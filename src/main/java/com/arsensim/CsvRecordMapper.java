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

/**
 * A mapper of the {@code CsvRecord}.
 * <p>
 * todo make E more flexible
 *
 * @param <E> The type to which the csv record will be mapped.
 * @author Arsen Simonean (arsensim08@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@FunctionalInterface
public interface CsvRecordMapper<I, E> {

    /**
     * Maps the provided csv record to a desired output object.
     *
     * @param record CSV record to map.
     * @return An instance of {@code E}.
     */
    E map(final CsvRecord<I> record);

}
