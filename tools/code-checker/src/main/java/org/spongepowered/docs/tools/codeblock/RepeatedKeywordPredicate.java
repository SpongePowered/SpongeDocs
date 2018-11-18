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

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Predicate that will only match if the input consecutively matches the
 * underlying filter at least a certain number of times.
 *
 * @param <T> The type of data this predicate deals this.
 */
public class RepeatedKeywordPredicate<T> implements Predicate<T> {

    private final Predicate<T> predicate;
    private final int requiredRepetitions;
    private int currentRepetitions = 0;

    /**
     * Creates a new RepeatedKeywordPredicate using the given predicate that
     * must match at least the given number of repetitions.
     *
     * <p>
     * <b>Note:</b> The match counter is increased for the first match, so this
     * predicate will return true for all matching elements if
     * requiredRepetitions is set to 1.
     * </p>
     *
     * @param predicate The filter used to check the input.
     * @param requiredRepetitions The required number of consecutive matches
     *        before this predicate will pass any tests. Must be &gt; 1.
     */
    public RepeatedKeywordPredicate(Predicate<T> predicate, int requiredRepetitions) {
        this.predicate = requireNonNull(predicate, "predicate");
        if (requiredRepetitions <= 0) {
            throw new IllegalArgumentException("requiredRepetitions cannot be zero or less");
        }
        this.requiredRepetitions = requiredRepetitions;
    }

    @Override
    public boolean test(T t) {
        if (this.predicate.test(t)) {
            this.currentRepetitions++;
        } else {
            this.currentRepetitions = 0;
        }
        return this.currentRepetitions >= this.requiredRepetitions;
    }

}
