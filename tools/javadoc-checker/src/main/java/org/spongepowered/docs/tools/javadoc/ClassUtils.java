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

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Helps with some class related tasks.
 */
public final class ClassUtils {

    private static final Pattern PATTERN_DOT = Pattern.compile(".", Pattern.LITERAL);
    private static final Pattern PATTERN_UPPER_SINGLE = Pattern.compile("[A-Z]");

    public static final ImmutableMap<String, Class<?>> PRIMITIVE_IMPORTS = ImmutableMap.<String, Class<?>>builder()
            .put("boolean", boolean.class)
            .put("byte", byte.class)
            .put("short", short.class)
            .put("char", char.class)
            .put("int", int.class)
            .put("long", long.class)
            .put("float", float.class)
            .put("double", double.class)
            .build();

    /**
     * Converts the given class to its corresponding array class.
     *
     * @param clazz The class that should be converted. May be null.
     * @return The array class of the given class or null if the given class was
     *         null.
     */
    @Nullable
    public static Class<?> toArrayClass(@Nullable final Class<?> clazz) {
        if (clazz == null) {
            return null;
        } else {
            return Array.newInstance(clazz, 0).getClass();
        }
    }

    /**
     * Extract the class names from the given potentially generic class
     * reference.
     *
     * <p>
     * <b>Examples:</b>
     * </p>
     *
     * <ul>
     * <li>String -&gt; String</li>
     * <li>Collection&lt;String&gt; -&gt; Collection, String</li>
     * </ul>
     *
     * @param genericDefinition The generic class definition.
     * @return A list with all classes contained in the genericDefinition in the
     *         order of their first occurrence.
     */
    public static List<String> extractClassNamesFromGenericDefinition(final String genericDefinition) {
        if (genericDefinition.isEmpty()) {
            throw new IllegalArgumentException("Empty generic definition!");
        }
        final List<String> result = new ArrayList<>();
        int index = 0;
        final int length = genericDefinition.length();
        while (true) {
            final int gStart = genericDefinition.indexOf('<', index);
            final int gSep = genericDefinition.indexOf(',', index);
            final int gEnd = genericDefinition.indexOf('>', index);
            int min = length + 1;
            if (gStart != -1) {
                min = Math.min(min, gStart);
            }
            if (gSep != -1) {
                min = Math.min(min, gSep);
            }
            if (gEnd != -1) {
                min = Math.min(min, gEnd);
            }
            if (gStart == min) {
                // <
                result.add(genericDefinition.substring(index, min));
            } else if (gSep == min) {
                // ,
                result.add(genericDefinition.substring(index, min));
            } else if (gEnd == min) {
                // >
                result.add(genericDefinition.substring(index, min));
            } else if (length + 1 == min) {
                if (index != length) {
                    final String substring = genericDefinition.substring(index, length);
                    result.add(substring);
                }
                return result;
            }
            index = min + 1;
        }
    }

    /**
     * Gets the class based on its import name.
     *
     * @param fqcn The fully qualified class name of the class.
     * @return The class represented by the given class name.
     * @throws IllegalArgumentException If the given name does not represent a
     *         class or the class is not on the classpath.
     */
    public static Class<?> getClassForName(final String fqcn) throws IllegalArgumentException {
        try {
            final String className = extractClassName(fqcn);
            return Class.forName(fqcn.replace('.' + className, '.' + className.replace('.', '$')));
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown class: " + fqcn, e);
        }
    }

    /**
     * Extracts the class name from the given class that can be used by the
     * javadoc tags. This method assumes that only trailing parts with an
     * uppercase character belong to the class name.
     *
     * @param fqcn The fully qualified class name of the class.
     * @return The class name from the given class
     */
    public static String extractClassName(final String fqcn) {
        final String[] parts = PATTERN_DOT.split(fqcn, -1);
        String className = parts[parts.length - 1];

        for (int i = parts.length - 2; i > 0; i--) {
            final String part = parts[i];
            if (PATTERN_UPPER_SINGLE.matcher(part).find()) {
                className = part + '.' + className;
            } else {
                break;
            }
        }

        return className;
    }

    /**
     * Extract the base classes of the parameter definition.
     *
     * @param parameters The parameters to search in.
     * @return A list of the base classes that represent the names of the
     *         argument types.
     */
    public static List<String> extractBaseClassNamesFromParameterDef(final String parameters) {
        final List<String> result = new ArrayList<>();
        if (parameters.isEmpty()) {
            return result;
        }
        int index = 0;
        int depth = 0;
        final int length = parameters.length();
        while (true) {
            final int gStart = parameters.indexOf('<', index);
            final int gSep = parameters.indexOf(',', index);
            final int gEnd = parameters.indexOf('>', index);
            int min = length + 1;
            if (gStart != -1) {
                min = Math.min(min, gStart);
            }
            if (gSep != -1) {
                min = Math.min(min, gSep);
            }
            if (gEnd != -1) {
                min = Math.min(min, gEnd);
            }
            if (gStart == min) {
                // <
                if (depth == 0) {
                    result.add(parameters.substring(index, min));
                }
                depth++;
            } else if (gSep == min) {
                // ,
                if (depth == 0 && index != min) {
                    result.add(parameters.substring(index, min));
                }
            } else if (gEnd == min) {
                // >
                depth--;
            } else if (length + 1 == min) {
                if (index != length) {
                    final String substring = parameters.substring(index, length);
                    result.add(substring);
                }
                return result;
            }
            index = min + 1;
        }
    }

    private ClassUtils() {
    }

}
