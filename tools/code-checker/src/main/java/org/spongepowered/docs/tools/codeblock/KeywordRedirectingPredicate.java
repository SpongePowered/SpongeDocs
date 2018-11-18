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

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Filters all matching entries from the test and redirects them to a
 * {@link Consumer}. This predicate will always return the opposite of the
 * underlying {@link Predicate}.
 *
 * @param <T> The type of data the predicate deals with.
 */
public class KeywordRedirectingPredicate<T> implements Predicate<T> {

    private final Predicate<T> keywordPredicate;
    private final Consumer<T> redirectionTarget;

    /**
     * Creates a new KeywordRedirectingPredicate using the given predicate as a
     * filter. The filtered entries will be redirected to the given consumer.
     *
     * @param keywordPredicate The predicate used to filter the input.
     * @param redirectionTarget The consumer that should receive the filtered
     *        entries.
     */
    public KeywordRedirectingPredicate(Predicate<T> keywordPredicate, Consumer<T> redirectionTarget) {
        this.keywordPredicate = keywordPredicate;
        this.redirectionTarget = redirectionTarget;
    };

    @Override
    public boolean test(T t) {
        if (this.keywordPredicate.test(t)) {
            this.redirectionTarget.accept(t);
            return false;
        } else {
            return true;
        }
    }

}
