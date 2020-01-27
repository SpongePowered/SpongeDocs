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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

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
            .add(double.class)
            .add(String.class)
            .add(UUID.class)
            .add(IpSet.class)
            .build();

    private static final char[] HEADLINE_LEVELS = { '#', '*', '=', '-', '^', '\"' };
    private static final char TYPE_HEADLINE_LEVEL = HEADLINE_LEVELS[2];

    public static void main(final String... args) {

        final Set<Class<?>> configClasses = scanForNestedTypes(GlobalConfig.class);
        final StringBuilder sb = new StringBuilder();
        for (final Class<?> clazz : configClasses) {
            writeDocumentation(clazz, sb);
        }
        System.out.println(sb);
    }

    public static Set<Class<?>> scanForNestedTypes(final Class<?> clazz) {
        final Set<Class<?>> result = new LinkedHashSet<>();
        result.add(clazz);
        final Queue<Class<?>> queue = new ConcurrentLinkedQueue<>();
        queue.add(clazz);
        for (final Class<?> current : queue) {
            for (final Field field : getAllFieldsFrom(current)) {
                final Class<?> fieldClass = extractSingularType(field.getGenericType());
                if (!DATA_CLASSES.contains(fieldClass) && result.add(fieldClass)) {
                    queue.add(current);
                }
            }
        }
        return result;
    }

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

    public static Class<?> extractSingularType(final Type type) {
        return extractSingularType(TypeToken.of(type));
    }

    public static Class<?> extractSingularType(final TypeToken<?> type) {
        if (type.isSubtypeOf(Collection.class)) {
            return type.resolveType(Collection.class.getTypeParameters()[0]).getRawType();
        }
        if (type.isSubtypeOf(Map.class)) {
            return extractSingularType(type.resolveType(Map.class.getTypeParameters()[1]));
        }
        return type.getRawType();
    }

    public static String extractName(final Field field) {
        final Setting setting = field.getAnnotation(Setting.class);
        if (setting.value().isEmpty()) {
            return field.getName();
        }
        return setting.value();
    }

    public static String extractComment(final Field field) {
        final Setting setting = field.getAnnotation(Setting.class);
        if (setting.comment().isEmpty()) {
            return null;
        }
        return setting.comment().trim().replace("\n", "\n| ");
    }

    public static void writeDocumentation(final Class<?> clazz, final StringBuilder sb) {
        final Object defaultInstance = createDefaultInstance(clazz);
        final String simpleName = clazz.getSimpleName();
        writeHeadline(simpleName, TYPE_HEADLINE_LEVEL, sb);
        sb.append('\n');
        for (final Field field : getAllFieldsFrom(clazz)) {
            final Type fieldType = field.getGenericType();
            final boolean simpleDataClass = DATA_CLASSES.contains(field.getType());
            final String comment = extractComment(field);

            sb.append("**").append(extractName(field)).append("**\n");
            sb.append('\n');
            if (comment != null) {
                sb.append("| ").append(comment).append("\n\n");
            }
            sb.append("* **Type:** ``").append(toText(fieldType)).append("``\n");
            if (simpleDataClass) {
                final Object defaultValue = getDefaultValue(defaultInstance, field);
                if (defaultValue != null) {
                    sb.append("* **Default:** ``").append(defaultValue).append("``\n");
                }
            }
            sb.append('\n');
        }
    }

    private static void writeHeadline(final String simpleName, final char headlineLevel, final StringBuilder sb) {
        sb.append(simpleName).append('\n');
        sb.append(StringUtils.repeat(headlineLevel, simpleName.length())).append('\n');
    }

    private static Object createDefaultInstance(final Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toText(final Type type) {
        return TypeToken.of(type).toString()
                .replaceAll("[A-Za-z]+\\.", "");
    }

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
