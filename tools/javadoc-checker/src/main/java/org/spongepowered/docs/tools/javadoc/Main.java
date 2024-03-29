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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws Exception {
        LOGGER.info("Javadoc-Checker-{}", Objects.toString(Main.class.getPackage().getImplementationVersion(), "DEV"));
        File docsSourceRoot;
        if (args.length >= 1) {
            docsSourceRoot = new File(args[0]);
        } else {
            // Executed from the tool project?
            docsSourceRoot = new File("../../source");
            if (!docsSourceRoot.exists()) {
                // Executed from the docs project?
                docsSourceRoot = new File("source");
                if (!docsSourceRoot.exists()) {
                    // Executed from the docs/source?
                    docsSourceRoot = new File(".");
                }
            }
        }
        if (!docsSourceRoot.exists()) {
            LOGGER.error("Docs Source not found at: ", docsSourceRoot.getAbsolutePath());
            throw new IllegalArgumentException("Docs Source not found at: " + docsSourceRoot.getAbsolutePath());
        }
        final boolean checkDeprecated;
        if (args.length >= 2) {
            checkDeprecated = Boolean.parseBoolean(args[1]);
        } else {
            checkDeprecated = false;
        }

        final File processingRoot = docsSourceRoot.getCanonicalFile();
        final int pathPrefixLength = processingRoot.getPath().length() + 1;
        dummyInitSponge();
        LOGGER.info("Started {} on {}", Instant.now(), processingRoot.getAbsolutePath());

        final AtomicInteger fileCount = new AtomicInteger();
        final AtomicInteger warningCount = new AtomicInteger();
        final AtomicInteger errorCount = new AtomicInteger();

        recursiveFileStream(processingRoot)
                .map(file -> new FileChecker(file, pathPrefixLength, checkDeprecated))
                .forEachOrdered(checker -> {
                    fileCount.incrementAndGet();
                    try {
                        checker.process();
                    } catch (final Exception e) {
                        errorCount.incrementAndGet();
                        LOGGER.error("Failed to process file: {}", checker.getFileName(), e);
                    }
                    warningCount.addAndGet(checker.getFindingCount());
                });

        LOGGER.info("Completed {} - Files: {} - Warnings: {} - Errors: {}",
                Instant.now(), fileCount, warningCount, errorCount);

        if (errorCount.get() > 0) {
            System.exit(1);
        }
    }

    private static void dummyInitSponge() throws Exception {
        final Field gameField = Sponge.class.getDeclaredField("game");
        final InvocationHandler handler = new InvocationHandler() {

            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                final Class<?> returnType = method.getReturnType();
                if (returnType.isInterface()) {
                    return Proxy.newProxyInstance(Sponge.class.getClassLoader(), new Class[] {returnType}, this);
                } else {
                    return null;
                }
            }

        };
        gameField.setAccessible(true);
        gameField.set(null, Proxy.newProxyInstance(Sponge.class.getClassLoader(), new Class[] {Game.class}, handler));
    }

    /**
     * Searches for all files with the <code>.rst</code> extension.
     *
     * @param file The file or directory to start the search.
     * @return The recursive stream that searches for all files with the
     *         <code>.rst</code> extension.
     */
    private static Stream<File> recursiveFileStream(final File file) {
        if (file.isDirectory()) {
            return Arrays.stream(file.listFiles()).flatMap(Main::recursiveFileStream);
        } else if (file.isFile() && file.getName().endsWith(".rst")) {
            return Stream.of(file);
        } else {
            return Stream.empty();
        }
    }

}
