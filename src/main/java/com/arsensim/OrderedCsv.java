/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Arsen Simonean
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.arsensim;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an ordered implementation of {@link Csv}.
 *
 * @param <T> the type to which each csv record will be mapped.
 * @author Arsen Simonean (arsensim08@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class OrderedCsv<T> implements Csv<T> {

    private final Csv<T> decoree;
    private final Comparator<T> comparator;

    /**
     * A constructor that expects an instance of {@link Csv} to decorate it.
     *
     * @param decoree    An instance of {@link Csv} which behavior is to be
     *                   extended.
     * @param comparator An instance of comparator to use when ordering.
     */
    public OrderedCsv(final Csv<T> decoree, final Comparator<T> comparator) {
        this.decoree = decoree;
        this.comparator = comparator;
    }

    @Override
    public List<T> map() {
        return this.decoree.map().stream()
                .sorted(this.comparator)
                .collect(Collectors.toList());
    }

}
