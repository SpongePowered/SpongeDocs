/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.docs.tools.codeblock;

import static java.nio.file.Files.lines;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileChecker.class);

    private static final Predicate<Object> IS_NOT_JAVADOC_IMPORT_START = Predicate.isEqual(".. javadoc-import::").negate();
    private static final Predicate<String> IS_CODE_BLOCK_START = s -> s.trim().equals(".. code-block:: java");
    private static final Predicate<String> IS_CODE_BLOCK_END = s -> !s.isEmpty() && !s.startsWith("    ");
    private static final Predicate<String> IS_IMPORT = s -> s.startsWith("import ");

    private static final Predicate<Collection<?>> IS_EMPTY_COLLECTION = Collection::isEmpty;
    private static final Predicate<Collection<?>> IS_NOT_EMPTY_COLLECTION = IS_EMPTY_COLLECTION.negate();
    private static final Predicate<String> IS_EMPTY = String::isEmpty;
    private static final Predicate<String> IS_NOT_EMPTY = IS_EMPTY.negate();

    private final String fileName;
    private final File file;
    private final String className;
    private final File outputFile;
    private final Set<String> imports = new TreeSet<>();

    /**
     * Creates a new File checker for the given path.
     *
     * @param file The file to handle in this instance.
     * @param pathPrefixLength The pathPrefix length that can be ignored for the
     *        file name.
     * @param checkDeprecated Whether this checker should check for deprecated
     *        references.
     */
    public FileChecker(final File file, final int pathPrefixLength, File outputDirectory) {
        this.fileName = file.getPath().substring(pathPrefixLength).replace('\\', '/');
        this.file = file;
        this.className = "Code_" + this.fileName.replace('/', '_').replace('-', '_').replace(".rst", "");
        this.outputFile = new File(outputDirectory, this.className + ".java");
    }

    /**
     * Gets the name of this file.
     *
     * @return The name of this file.
     */
    public String getFileName() {
        return this.fileName;
    }

    public int process() throws IOException {
        LOGGER.info("Processing: " + this.fileName);
        collectImports();

        final List<List<String>> codeBlocks = collectCodeBlocks();
        if (codeBlocks.isEmpty()) {
            return 0;
        }

        LOGGER.debug("Writing Class: " + this.className);
        try (FileOutputStream fos = new FileOutputStream(this.outputFile);
                JavaFileWriter writer = new JavaFileWriter(fos)) {
            writer.packageDef("org.spongepowered.docs.generated.codeblocks")
                    .imports(this.imports)
                    .imported("static org.spongepowered.docs.generated.Utils.*")
                    .classDef(this.className);

            int i = 0;
            for (List<String> codeBlock : codeBlocks) {

                boolean indended = true;

                // Detect code block context
                String firstLine = codeBlock.get(0);
                int firstLineIndex = 0;
                while (firstLine.startsWith("@") || firstLine.startsWith("//")) {
                    firstLine = codeBlock.get(++firstLineIndex);
                }
                if (firstLine.startsWith("private ")) {
                    writer.writeLine("// " + i + " - Modifier");
                    writer.classDef("Inner" + i++, true);
                } else if (firstLine.contains(" = ")) {
                    writer.writeLine("// " + i + " - Assignment");
                    writer.methodDef("inner" + i++, "void");
                } else if (firstLine.startsWith("if ") || firstLine.startsWith("try ")) {
                    writer.writeLine("// " + i + " - Keyword");
                    writer.methodDef("inner" + i++, "void");
                } else if (firstLine.matches("^[a-zA-Z]+(\\(\\))?\\.[a-z].*")) {
                    writer.writeLine("// " + i + " - Operation on reference");
                    writer.methodDef("inner" + i++, "void");
                } else if (firstLine.startsWith("return ")) {
                    writer.writeLine("// " + i + " - Return");
                    writer.methodDef("inner" + i++, "Object");
                } else if (firstLine.contains(" class ") || firstLine.contains(" interface ")) {
                    writer.writeLine("// " + i + " - Class");
                    indended = false;
                } else {
                    writer.writeLine("// " + i + " - Unknown");
                    writer.classDef("Inner" + i++, true);
                }

                // Write actual code block
                for (String line : codeBlock) {
                    if (line.isEmpty()) {
                        writer.emptyLine();
                        continue;
                    }
                    // Replace templates + adapt to changed context
                    if (line.startsWith("package ")) {
                        line = "// " + line;
                    } else if (line.contains(" class ")) {
                        if (!line.contains(" static ")) {
                            line = line.replace(" class ", " static class ");
                        }
                    } else if (line.contains(" interface ")) {
                        if (!line.contains(" static ")) {
                            line = line.replace(" interface ", " static interface ");
                        }
                    } else if (line.trim().equals("[...]")) {
                        line = "// [...]";
                    } else if (line.trim().endsWith(" = ...;")) {
                        line = line.replace(" = ...;", " = magic();");
                    } else if (line.trim().contains("(...)")) {
                        line = line.replace("(...)", "(magic())");
                    }
                    writer.writeLine(line);
                }

                writer.emptyLine();
                if (indended) {
                    writer.closingBracket();
                }
            }
            writer.closingBracket();
        }
        return codeBlocks.size();
    }

    /**
     * Collects the imports for this file.
     *
     * @throws IOException If the file could not be read.
     */
    private void collectImports() throws IOException {
        lines(this.file.toPath())
                .dropWhile(IS_NOT_JAVADOC_IMPORT_START)
                .skip(1) // Skip the import tag
                .dropWhile(IS_EMPTY) // first line might be empty
                .takeWhile(IS_NOT_EMPTY) // until the end of the block
                .map(String::trim) // remove indenting
                // .filter(IS_NOT_EMPTY) // ignore import groups separator
                .forEachOrdered(this.imports::add);
    }

    private List<List<String>> collectCodeBlocks() throws IOException {
        return KeywordPartitioner.partition(
                lines(this.file.toPath())
                        .filter(new OnOffKeywordPredicate<>(IS_CODE_BLOCK_START, IS_CODE_BLOCK_END))
                        .map(new KeywordBasedDeIndenter(IS_CODE_BLOCK_START))
                        .skip(1) // skip first start keyword
                        // remove leading indent from the code block
                        .map(s -> s.startsWith("    ") ? s.substring(4) : s)
                        // Extract inlined imports
                        .filter(new KeywordRedirectingPredicate<>(IS_IMPORT,
                                s -> this.imports.add(s.substring(7).trim().replace(";", ""))))
                        // Empty lines should be really empty
                        .map(s -> s.trim().isEmpty() ? "" : s)
                        // only one empty line allowed
                        .filter(new OnOffKeywordPredicate<>(IS_NOT_EMPTY, new RepeatedKeywordPredicate<>(IS_EMPTY, 2)))

                , IS_CODE_BLOCK_START) // split individual code blocks.
                // Remote leading and trailing empty lines
                .map(b -> {
                    if (!b.isEmpty() && b.get(0).isEmpty()) {
                        b.remove(0);
                    }
                    if (!b.isEmpty() && b.get(b.size() - 1).isEmpty()) {
                        b.remove(b.size() - 1);
                    }
                    return b;
                })
                .filter(IS_NOT_EMPTY_COLLECTION)
                // .peek(l -> {
                // LOGGER.warn("------------------------------------------------");
                // l.forEach(LOGGER::debug);
                // })
                .collect(Collectors.toList());
    }

}
