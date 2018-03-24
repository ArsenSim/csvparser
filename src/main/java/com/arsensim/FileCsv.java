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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is an implementation of {@link Csv} that parses a csv file as
 * resource. todo make generic type more flexible
 *
 * @param <E> the type to which each csv record will be mapped.
 * @author Arsen Simonean (arsensim08@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class FileCsv<E> implements Csv<Integer, E> {

    private final Path file;
    private final String delimiter;


    public FileCsv(final Path file, final String delimiter) {
        this.file = file;
        this.delimiter = delimiter;
    }

    @Override
    public List<E> map(final CsvRecordMapper<Integer, ? extends E> mapper) {
        this.assertFileExists();
        try (final BufferedReader reader = Files.newBufferedReader(this.file)) {
            final List<String[]> rows = reader.lines()
                    .map(line -> line.split(this.delimiter))
                    .collect(Collectors.toList());
            final ArrayList<CsvRecord<Integer>> records = new ArrayList<>();
            // todo validate same size
            for (final String[] row : rows) {
                final HashMap<Integer, String> data = new HashMap<>();
                for (int i = 0; i < row.length; i++) {
                    data.put(i, row[i]);
                }
                records.add(
                        new SimpleCsvRecord<>(data)
                );
            }
            return records.stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());
        } catch (final IOException e) {
            throw new CsvException(
                    String.format("Exception while reading file %s", this.file),
                    e
            );
        }
    }

    /**
     * Checks if the file exist.
     *
     * @throws CsvException when file does not exist.
     */
    private void assertFileExists() {
        if (!Files.exists(this.file)) {
            throw new CsvException(
                    String.format("File %s not found", this.file)
            );
        }
    }

}
