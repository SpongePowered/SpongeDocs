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
import java.util.function.UnaryOperator;

public class KeywordBasedDeIndenter implements UnaryOperator<String> {

    private final Predicate<String> keywordPredicate;
    private int indentionLevel = 0;

    public KeywordBasedDeIndenter(Predicate<String> keywordPredicate) {
        this.keywordPredicate = requireNonNull(keywordPredicate, "keywordPredicate");
    }

    @Override
    public String apply(String text) {
        if (this.keywordPredicate.test(text)) {
            this.indentionLevel = 0;
            while (text.charAt(this.indentionLevel) == ' ') {
                this.indentionLevel++;
            }
        }
        if (text.isEmpty()) {
            return text;
        }
        for (int i = 0; i < this.indentionLevel && !text.isEmpty() && text.charAt(0) == ' '; i++) {
            text = text.substring(1);
        }
        return text;
    }

}
