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
package org.spongepowered.docs.tools.javadoc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.readAllBytes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileChecker.class);

    private static final Pattern PATTERN_PACKAGE_DEF = Pattern.compile("[a-z][a-z\\.]+");
    private static final Pattern PATTERN_HASH = Pattern.compile("#");
    private static final Pattern PATTERN_JAVADOC = Pattern.compile(":javadoc:`([^`]*)`");

    private static final Predicate<String> IS_EMPTY = String::isEmpty;
    private static final Predicate<String> IS_NOT_EMPTY = IS_EMPTY.negate();

    private final String fileName;
    private final File file;
    private final boolean checkDeprecated;
    private final Map<String, Class<?>> imports = new LinkedHashMap<>(ClassUtils.PRIMITIVE_IMPORTS);
    private final Map<String, Class<?>> unusedImports = new TreeMap<>();
    private int findings = 0;

    /**
     * Creates a new File checker for the given path.
     *
     * @param file The file to handle in this instance.
     * @param pathPrefixLength The pathPrefix length that can be ignored for the
     *        file name.
     * @param checkDeprecated Whether this checker should check for deprecated
     *        references.
     */
    public FileChecker(final File file, final int pathPrefixLength, boolean checkDeprecated) {
        this.fileName = file.getPath().substring(pathPrefixLength).replace('\\', '/');
        this.file = file;
        this.checkDeprecated = checkDeprecated;
    }

    /**
     * Gets the name of this file.
     *
     * @return The name of this file.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets the number of findings for this file.
     *
     * @return The number of findings.
     */
    public int getFindingCount() {
        return this.findings;
    }

    public void process() throws IOException {
        collectImports();

        checkJavadocReferences();
        for (final Class<?> unusedImport : this.unusedImports.values()) {
            report("Unused import", unusedImport.getSimpleName(), unusedImport.getName());
        }
        // If we didn't find anything, the we should still print the processed
        // file name.
        if (this.findings == 0) {
            LOGGER.info("Processing: " + this.fileName);
        }
    }

    private void report(final String type, final String value, final String reference) {
        if (this.findings == 0) {
            LOGGER.warn("Processing: " + this.fileName);
        }
        this.findings++;
        LOGGER.warn(" - " + type + " \"" + value + "\" in " + reference);
    }

    /**
     * Collects the imports for this file.
     *
     * @throws IOException If the file could not be read.
     */
    private void collectImports() throws IOException {
        lines(this.file.toPath())
                .dropWhile(Predicate.isEqual(".. javadoc-import::").negate())
                .skip(1) // Skip the import tag
                .dropWhile(IS_EMPTY) // first line might be empty
                .takeWhile(IS_NOT_EMPTY) // until the end of the block
                .map(String::trim) // remove indenting
                .filter(IS_NOT_EMPTY) // ignore import groups separator
                .forEachOrdered(fqcn -> {
                    final String key = ClassUtils.extractClassName(fqcn);
                    Class<?> clazz = null;
                    try {
                        clazz = ClassUtils.getClassForName(fqcn);
                    } catch (final IllegalArgumentException e) {
                        report("Unknown import", key, fqcn);
                    }
                    if (clazz != null) {
                        this.imports.put(key, clazz);
                        this.unusedImports.merge(key, clazz, (a, b) -> {
                            report("Duplicate import", key, fqcn);
                            return a;
                        });
                    }
                });
        // TODO: Check import order?
    }

    private String readFileContents() throws IOException {
        return new String(readAllBytes(this.file.toPath()), UTF_8);
    }

    private List<String> findJavadocReferences() throws IOException {
        return PATTERN_JAVADOC.matcher(readFileContents()).results()
                // remove line-breaks, spaces and indenting
                .map(r -> r.group(1).replaceAll("[ \\t\\n\\r]+", ""))
                // remove display value
                .map(s -> s.replaceAll("\\{.*\\}", ""))
                .collect(Collectors.toList());
    }

    @Nullable
    private Class<?> resolveClass(final String clazzName) {
        this.unusedImports.remove(clazzName);
        if (clazzName.endsWith("...")) {
            final String rawClassName = clazzName.substring(0, clazzName.length() - 3);
            return ClassUtils.toArrayClass(resolveClass(rawClassName));
        }
        Class<?> clazz = this.imports.get(clazzName);
        if (clazz == null) {
            try {
                clazz = ClassUtils.getClassForName(clazzName);
            } catch (final IllegalArgumentException e) {
            }
        }
        return clazz;
    }

    private void checkJavadocReferences() throws IOException {
        for (final String reference : findJavadocReferences()) {
            // Is it a package?
            if (PATTERN_PACKAGE_DEF.matcher(reference).matches()) {
                checkPackageDef(reference);
                continue; // Nothing else to do.
            }
            final String[] parts = PATTERN_HASH.split(reference, 2);

            // Check the class part
            final @Nullable Class<?> baseClass = checkClassDefAndReturnBaseClass(parts[0], reference);

            if (parts.length == 1 || baseClass == null) {
                // Its a class references nothing else to do.
                // or missing import -> Cannot continue with this entry
                continue;
            }

            final String memberDef = parts[1];
            if (memberDef.contains("(")) {
                // Its a method
                checkMethodDef(baseClass, memberDef, reference);
            } else {
                // Its a field
                checkFieldDef(baseClass, memberDef, reference);
            }
        }
    }

    private void checkPackageDef(final String reference) {
        // Then we surely use it at least one import from it
        if (this.imports.values().stream()
                .map(Class::getName)
                .noneMatch(s -> s.startsWith(reference + '.'))) {
            report("Unknown package reference", reference, reference);
        }
    }

    @Nullable
    private Class<?> checkClassDefAndReturnBaseClass(final String classDef, final String reference) {
        final List<String> classReferences = ClassUtils.extractClassNamesFromGenericDefinition(classDef);
        for (final String classReference : classReferences) {
            final Class<?> clazz = resolveClass(classReference);
            if (clazz == null) {
                report("Missing import", classReference, reference);
            }
        }
        Class<?> clazz = resolveClass(classReferences.get(0));
        if (this.checkDeprecated && clazz.isAnnotationPresent(Deprecated.class)) {
            report("Deprecated class", clazz.getSimpleName(), reference);
        }
        return clazz;
    }

    private void checkMethodDef(final Class<?> ownerClass, final String memberDef, final String reference) {
        final int startIndex = memberDef.indexOf('(');
        final String methodName = memberDef.substring(0, startIndex);
        final String parameters = memberDef.substring(startIndex + 1, memberDef.length() - 1);
        final List<Class<?>> clazzes = new ArrayList<>();
        for (final String parameterClazzName : ClassUtils.extractBaseClassNamesFromParameterDef(parameters)) {
            final Class<?> clazz = resolveClass(parameterClazzName);
            if (clazz == null) {
                report("Unknown import", parameterClazzName, reference);

            }
            clazzes.add(clazz);
        }
        if (clazzes.contains(null)) {
            // If we can't find all the parameters, we won't find the method
            return;
        }
        final Class<?>[] clazzesArray = clazzes.toArray(new Class<?>[clazzes.size()]);
        try {
            Method method = ownerClass.getDeclaredMethod(methodName, clazzesArray);
            if (this.checkDeprecated && method.isAnnotationPresent(Deprecated.class)) {
                report("Deprecated method", memberDef, reference);
            }
        } catch (NoSuchMethodException | SecurityException e) {
            report("Unknown method", memberDef, reference);
        }
    }

    private void checkFieldDef(final Class<?> primaryClass, final String memberDef, final String reference) {
        try {
            Field field = primaryClass.getField(memberDef);
            if (this.checkDeprecated && field.isAnnotationPresent(Deprecated.class)) {
                report("Deprecated field", memberDef, reference);
            }
        } catch (NoSuchFieldException | SecurityException e) {
            report("Unknown field", memberDef, reference);
        }
    }

}
