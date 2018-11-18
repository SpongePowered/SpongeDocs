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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class OnOffKeywordPredicateTest {

    private static final Predicate<String> ON_PREDICATE = Predicate.isEqual("ON");
    private static final Predicate<String> OFF_PREDICATE = Predicate.isEqual("OFF");

    @Test
    void test() {
        // Normal
        assertEquals(Arrays.asList("ON", "Fire"),
                Stream.of("Foo", "Bar", "ON", "Fire", "OFF", "was", "destroyed")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE))
                        .collect(Collectors.toList()));

        // No ON
        assertEquals(Arrays.asList(),
                Stream.of("Foo", "Bar", "OFF", "Fire", "OFF", "was", "destroyed")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE))
                        .collect(Collectors.toList()));

        // No OFF
        assertEquals(Arrays.asList("ON", "Fire", "ON", "was", "destroyed"),
                Stream.of("Foo", "Bar", "ON", "Fire", "ON", "was", "destroyed")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE))
                        .collect(Collectors.toList()));

        // End ON
        assertEquals(Arrays.asList("ON"),
                Stream.of("TURN", "the", "computer", "OFF", "and", "the", "BRAIN", "ON")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE, false))
                        .collect(Collectors.toList()));

        // Start ON
        assertEquals(Arrays.asList("TURN", "the", "computer", "ON"),
                Stream.of("TURN", "the", "computer", "OFF", "and", "the", "BRAIN", "ON")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE, true))
                        .collect(Collectors.toList()));

        // Start ON, but OFF immediately
        assertEquals(Arrays.asList(),
                Stream.of("OFF", "line", "LIFE")
                        .filter(new OnOffKeywordPredicate<>(ON_PREDICATE, OFF_PREDICATE, true))
                        .collect(Collectors.toList()));
    }

}
