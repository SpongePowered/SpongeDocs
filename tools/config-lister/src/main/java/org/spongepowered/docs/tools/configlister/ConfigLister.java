/**
 * This file is part of ConfigLister, licensed under the MIT License (MIT).
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.spongepowered.docs.tools.configlister;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.common.config.type.GlobalConfig;
import org.spongepowered.common.util.IpSet;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.objectmapping.Setting;

public class ConfigLister {

    private static final Set<Class<?>> DATA_CLASSES = ImmutableSet.<Class<?>>builder()
            .add(boolean.class)
            .add(Boolean.class)
            .add(int.class)
            .add(Integer.class)
            .add(double.class)
            .add(String.class)
            .add(UUID.class)
            .add(IpSet.class)
            .build();

    private static final char[] HEADLINE_LEVELS = { '#', '*', '=', '-', '^', '\"' };
    private static final char TYPE_HEADLINE_LEVEL = HEADLINE_LEVELS[2];

    /**
     * Writes the documentation to sysout.
     *
     * @param args No args.
     */
    public static void main(final String... args) {

        final Map<Class<?>, String> configClasses = scanForNestedTypes(GlobalConfig.class);
        final StringBuilder sb = new StringBuilder();
        writeDocumentation(GlobalConfig.class, "The main configuration for Sponge: ``global.conf``", sb);
        for (final Entry<Class<?>, String> entry : configClasses.entrySet()) {
            writeDocumentation(entry.getKey(), entry.getValue(), sb);
        }
        System.out.println(sb);
    }

    /**
     * Scans for nested configuration types.
     *
     * @param clazz The class to scan for nested types.
     * @return A sorted map containing all nested configuration classes, with their
     *         primary description.
     */
    public static Map<Class<?>, String> scanForNestedTypes(final Class<?> clazz) {
        final Map<Class<?>, String> result = new TreeMap<>(Comparator.comparing(Class::getSimpleName));
        final Queue<Class<?>> queue = new LinkedList<>();
        Class<?> current = clazz;
        do {
            for (final Field field : getAllFieldsFrom(current)) {
                final String comment = extractComment(field);
                final Class<?> fieldClass = extractSingularType(field.getGenericType());
                if (!DATA_CLASSES.contains(fieldClass) && !fieldClass.isEnum() && !result.containsKey(fieldClass)) {
                    result.put(fieldClass, comment);
                    queue.add(fieldClass);
                }
            }
        } while ((current = queue.poll()) != null);
        return result;
    }

    /**
     * Gets all fields for the given class.
     *
     * @param clazz The class to get the fields from.
     * @return A sorted set containing the fields of the given class.
     */
    public static Set<Field> getAllFieldsFrom(final Class<?> clazz) {
        if (Object.class.equals(clazz)) {
            return Collections.emptySet();
        }
        final Set<Field> fields = new TreeSet<>(Comparator.comparing(ConfigLister::extractName));
        for (final Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fields.add(field);
            }
        }
        fields.addAll(getAllFieldsFrom(clazz.getSuperclass()));
        return fields;
    }

    /**
     * Extracts the singular type of the given type. If the given type represents a
     * {@link Collection} then it will return the type of the collection. If the
     * given type represents a {@link Map} then it will return the type of the
     * value. Otherwise it will return the rawtype of the given type.
     *
     * @param type The type to extract the singular type from.
     * @return The singular type of the given type.
     */
    public static Class<?> extractSingularType(final Type type) {
        return extractSingularType(TypeToken.of(type));
    }

    /**
     * Extracts the singular type of the given type. If the given type represents a
     * {@link Collection} then it will return the type of the collection. If the
     * given type represents a {@link Map} then it will return the type of the
     * value. Otherwise it will return the rawtype of the given type.
     *
     * @param type The type to extract the singular type from.
     * @return The singular type of the given type.
     */
    public static Class<?> extractSingularType(final TypeToken<?> type) {
        if (type.isSubtypeOf(Collection.class)) {
            return type.resolveType(Collection.class.getTypeParameters()[0]).getRawType();
        }
        if (type.isSubtypeOf(Map.class)) {
            return extractSingularType(type.resolveType(Map.class.getTypeParameters()[1]));
        }
        return type.getRawType();
    }

    /**
     * Extracts the name of the field.
     *
     * @param field The field to extract the name from.
     * @return The defined name or the derived name of the field.
     */
    public static String extractName(final Field field) {
        final Setting setting = field.getAnnotation(Setting.class);
        if (setting.value().isEmpty()) {
            return field.getName();
        }
        return setting.value();
    }

    /**
     * Extracts the comment from the given field.
     *
     * @param field The field to extract the comment from.
     * @return The processed comment for the given field or null.
     */
    public static String extractComment(final Field field) {
        final Setting setting = field.getAnnotation(Setting.class);
        if (setting.comment().isEmpty()) {
            return null;
        }
        return setting.comment().strip()
                .replaceAll(" ?\\(Default: [^\\)]+\\)", "") // Remove default value, we already have that
                .replaceAll("\n\n+", "\n") // Remove empty lines
                .replace("\"", "``") // " -> ``
                .replaceAll("(\\W|^)'(.*)'(\\W|$)", "$1``$2``$3") // 'highlighted' -> ``highlighted``, ignore "it's"
                .replaceAll("([^a-zA-Z0-9/]|^)-?([0-9]*\\.?[0-9]+)([^a-zA-Z0-9/]|$)", "$1``$2``$3") // 1 -> ``1``, ignore 1x1
                .replaceAll("\n?(Note|Warning|WARNING):", "\n**$1**:"); // Highlight keywords
    }

    /**
     * Writes the documentation for the given class to the given string builder.
     *
     * @param clazz The class to write the documentation for.
     * @param classComment The comment for the class. Can be null.
     * @param sb The string builder to write the documentation to.
     */
    public static void writeDocumentation(final Class<?> clazz, final String classComment, final StringBuilder sb) {
        final Object defaultInstance = createDefaultInstance(clazz);
        // Class header
        writeTypeHeadline(toSimpleName(clazz), TYPE_HEADLINE_LEVEL, sb);
        sb.append('\n');
        if (classComment != null) {
            sb.append("| ").append(classComment.replace("\n", "\n| ")).append('\n');
            sb.append("|\n"); // Extra line to force some space between the end of this section and the next
            sb.append('\n');
        }

        // Fields
        for (final Field field : getAllFieldsFrom(clazz)) {
            final Type fieldType = field.getGenericType();
            final Class<?> singularType = extractSingularType(fieldType);

            final String comment = extractComment(field);

            // Field-Name
            sb.append("* **").append(extractName(field)).append("**\n");
            sb.append('\n');
            // Field-Comment
            if (comment != null) {
                sb.append("  | ").append(comment
                        .replace("\n", "\n  | ")).append('\n');
            }
            // Field-Type
            if (singularType.isEnum()) {
                // Enum type -> Just text + possible values
                sb.append("  | **Type:** ``").append(toText(fieldType)).append("``\n");
                sb.append("  | **Possible values:** ").append("\n");
                for (final Object constant : singularType.getEnumConstants()) {
                    sb.append("  | - ``").append(((Enum<?>) constant).name()).append("``\n");
                }
                // Field-Default
                if (field.getType().isEnum()) {
                    final Object defaultValue = getDefaultValue(defaultInstance, field);
                    if (defaultValue != null) {
                        sb.append("  | **Default:** ``").append(defaultValue).append("``\n");
                    }
                }

            } else if (DATA_CLASSES.contains(singularType)) {
                // Simple type -> Just text
                sb.append("  | **Type:** ``").append(toText(fieldType)).append("``\n");
                // Field-Default
                if (DATA_CLASSES.contains(field.getType())) {
                    final Object defaultValue = getDefaultValue(defaultInstance, field);
                    if (defaultValue != null) {
                        sb.append("  | **Default:** ``").append(defaultValue).append("``\n");
                    }
                }
            } else {
                // Nested type -> Generate link
                sb.append("  | **Type:** :ref:`").append(toText(fieldType).replace("<", "\\<"))
                        .append("<ConfigType_").append(toSimpleName(singularType)).append(">`\n");
            }
            sb.append("  |\n"); // Extra line to force some space between the end of this section and the next
            sb.append('\n');
        }
    }

    /**
     * Writes a headline for the given type.
     *
     * @param simpleName The simple name of the type to write.
     * @param headlineLevel The headline level char to use.
     * @param sb The string builder to write the headline to.
     */
    private static void writeTypeHeadline(final String simpleName, final char headlineLevel, final StringBuilder sb) {
        sb.append(".. _ConfigType_").append(simpleName).append(":\n\n");
        sb.append(simpleName).append('\n');
        sb.append(StringUtils.repeat(headlineLevel, simpleName.length())).append('\n');
    }

    /**
     * Converts the given type to its simple name, but retains generic parameters.
     *
     * @param type The type to convert.
     * @return The simple name of the given type.
     */
    private static String toText(final Type type) {
        return TypeToken.of(type).toString()
                .replaceAll("[A-Za-z]+\\.", "") // Remove package names
                .replaceAll("Category(\\W|$)", "$1"); // Remove confusing Category suffix
    }

    private static String toSimpleName(final Class<?> clazz) {
        return clazz.getSimpleName()
                .replaceAll("Category$", ""); // Remove confusing Category suffix
    }

    /**
     * Create the default instance of the given class.
     *
     * @param clazz The class to create the default instance for.
     * @return The newly created default instance.
     */
    private static Object createDefaultInstance(final Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets the default value for the given field.
     *
     * @param defaultInstance The default instance to extract the default value
     *            from. Can be null.
     * @param field The field to extract the default value for.
     * @return The default value for the given field or null.
     */
    private static Object getDefaultValue(final Object defaultInstance, final Field field) {
        if (defaultInstance == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(defaultInstance);
        } catch (final Exception e) {
            return null;
        }
    }

}
