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

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class JavaFileWriter implements Closeable {

    private static final String INDEND = "    ";
    private static final String[] INDENDS = new String[] {
            "",
            INDEND,
            INDEND + INDEND,
            INDEND + INDEND + INDEND
    };

    private final BufferedWriter writer;

    private int indend = 0;

    public JavaFileWriter(OutputStream output) {
        this(output, StandardCharsets.UTF_8);
    }

    public JavaFileWriter(OutputStream output, Charset charset) {
        this(new OutputStreamWriter(output, charset));
    }

    public JavaFileWriter(Writer writer) {
        this(writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer));
    }

    public JavaFileWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public JavaFileWriter write(String content) throws IOException {
        this.writer.write(content);
        return this;
    }

    public JavaFileWriter writeIndended(String content) throws IOException {
        this.writer.write(INDENDS[this.indend]);
        return write(content);
    }

    public JavaFileWriter writeLine(String line) throws IOException {
        return writeIndended(line)
                .emptyLine();
    }

    public JavaFileWriter emptyLine() throws IOException {
        this.writer.newLine();
        return this;
    }

    public JavaFileWriter packageDef(String package_) throws IOException {
        return writeLine("package " + package_ + ";")
                .emptyLine();
    }

    public JavaFileWriter imported(String clazz) throws IOException {
        return writeLine("import " + clazz + ";");
    }

    public JavaFileWriter imports(Collection<String> clazzes) throws IOException {
        for (String clazz : clazzes) {
            imported(clazz);
        }
        if (!clazzes.isEmpty()) {
            emptyLine();
        }
        return this;
    }

    public JavaFileWriter classDef(String clazz) throws IOException {
        return classDef(clazz, false);
    }

    public JavaFileWriter classDef(String clazz, boolean static_) throws IOException {
        writeLine("public " + (static_ ? "static " : "") + "class " + clazz + " {");
        this.indend++;
        return emptyLine();
    }

    public JavaFileWriter methodDef(String method, String returnType) throws IOException {
        writeLine("public " + returnType + " " + method + "() throws Throwable {");
        this.indend++;
        return emptyLine();
    }

    public JavaFileWriter closingBracket() throws IOException {
        this.indend--;
        return writeLine("}").emptyLine();
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

}
