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

import java.util.function.Predicate;

/**
 * Predicate that will filter the content based on the preceding on and off
 * keywords. Entries that match the on keyword will pass the filter. Entries
 * that match the off keyword will be removed.
 *
 * @param <T> The type of data this predicate deals this.
 */
public class OnOffKeywordPredicate<T> implements Predicate<T> {

    private final Predicate<T> onKeyword;
    private final Predicate<T> offKeyword;
    private boolean status = false;

    /**
     * Creates an new OnOffKeywordPredicate that will return all entries that
     * were tested while this instances was on. The initial status will be off.
     *
     * @param onKeyword The predicate used to decide to start letting entries
     *        throughout.
     * @param offKeyword The predicate used to decide to stop letting entries
     *        throughout.
     */
    public OnOffKeywordPredicate(Predicate<T> onKeyword, Predicate<T> offKeyword) {
        this(onKeyword, offKeyword, false);
    }

    /**
     * Creates an new OnOffKeywordPredicate that will return all entries that
     * were tested while this instances was on.
     *
     * @param onKeyword The predicate used to decide to start letting entries
     *        throughout.
     * @param offKeyword The predicate used to decide to stop letting entries
     *        throughout.
     * @param The initial status of this filter.
     */
    public OnOffKeywordPredicate(Predicate<T> onKeyword, Predicate<T> offKeyword, boolean initialStatus) {
        this.onKeyword = onKeyword;
        this.offKeyword = offKeyword;
        this.status = initialStatus;
    }

    @Override
    public boolean test(T t) {
        if (this.status) {
            if (this.offKeyword.test(t)) {
                this.status = false;
            }
        } else {
            if (this.onKeyword.test(t)) {
                this.status = true;
            }
        }
        return this.status;
    }

}
