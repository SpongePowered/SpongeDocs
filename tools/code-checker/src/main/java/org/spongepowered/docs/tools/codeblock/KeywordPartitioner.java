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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This iterator partitions the data from the underlying iterator based on a
 * {@link Predicate} and groups them to a {@link List}. The separator itself
 * will be removed. There are also helper methods for {@link Stream} usage.
 *
 * @param <E> The type of entries that should be split.
 */
public class KeywordPartitioner<E> implements Iterator<List<E>> {

    private final Iterator<E> iterator;
    private final Predicate<E> partitionSeparator;

    /**
     * Creates a new stream that will partition the data from the given stream
     * using the given predicate as separator.
     *
     * @param stream The stream that should be partitioned.
     * @param partitionSeparator The predicate that will be used to detect the
     *        separator. The matching separator will be removed from the output.
     * @return The newly created partitioned stream.
     */
    public static <T> Stream<List<T>> partition(final Stream<T> stream, final Predicate<T> partitionSeparator) {
        return new KeywordPartitioner<>(stream, partitionSeparator).asStream();
    }

    /**
     * Creates a new KeywordPartitioner using the given stream and the given
     * separator predicate.
     *
     * @param stream The stream that should be partitioned.
     * @param partitionSeparator The predicate that will be used to detect the
     *        separator. The matching separator will be removed from the output.
     */
    public KeywordPartitioner(final Stream<E> stream, final Predicate<E> partitionSeparator) {
        this(stream.iterator(), partitionSeparator);
    }

    /**
     * Creates a new KeywordPartitioner using the given iterator and the given
     * separator predicate.
     *
     * @param iterator The iterator that should be partitioned.
     * @param partitionSeparator The predicate that will be used to detect the
     *        separator. The matching separator will be removed from the output.
     */
    public KeywordPartitioner(final Iterator<E> iterator, final Predicate<E> partitionSeparator) {
        this.iterator = iterator;
        this.partitionSeparator = partitionSeparator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public List<E> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }
        final ArrayList<E> result = new ArrayList<>();
        E current;
        while (hasNext() && !this.partitionSeparator.test(current = this.iterator.next())) {
            result.add(current);
        }
        return result;
    }

    /**
     * Converts this iterator to a stream to ease the usage in fluent stream
     * calls.
     *
     * @return This iterator as a stream.
     */
    public Stream<List<E>> asStream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this, Spliterator.NONNULL), false);
    }

}
