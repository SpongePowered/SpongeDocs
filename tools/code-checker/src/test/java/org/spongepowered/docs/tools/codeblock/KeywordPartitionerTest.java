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

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.spongepowered.docs.tools.codeblock.KeywordPartitioner.partition;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class KeywordPartitionerTest {

    @Test
    void test() {
        assertEquals(asList(asList("Foo", "Bar", "ON"), asList("OFF", "was")),
                partition(
                        Stream.of("Foo", "Bar", "ON", "Fire", "OFF", "was", "destroyed"),
                        s -> s.length() > 3)
                                .collect(Collectors.toList()));

        assertEquals(asList(asList("Foo", "Bar", "OFF", "Fire", "OFF", "was")),
                partition(
                        Stream.of("Foo", "Bar", "OFF", "Fire", "OFF", "was", "destroyed"),
                        s -> s.length() > 4)
                                .collect(Collectors.toList()));

        assertEquals(asList(asList(), asList(), asList("ON"), asList("ON"), asList()),
                partition(
                        Stream.of("Foo", "Bar", "ON", "Fire", "ON", "was", "destroyed"),
                        s -> s.length() > 2)
                                .collect(Collectors.toList()));
    }

}
