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

import org.apache.commons.csv.CSVFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of {@link Csv} that parses a csv file as
 * resource. todo make generic type more flexible
 *
 * @param <T> the type to which each csv record will be mapped.
 * @author Arsen Simonean (arsensim08@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class FileCsv<T> implements Csv<T> {

    private final Path file;
    private final CsvMapper<T> mapper;
    private final CSVFormat format;

    /**
     * A convenience constructor that uses {@link ReflectiveCsvMapper} class
     * to map output.
     *
     * @param file   The CSV file.
     * @param output The class of the output desired.
     */
    public FileCsv(final Path file, final Class<T> output) {
        this(
                file,
                new ReflectiveCsvMapper<>(output),
                CSVFormat.DEFAULT.withFirstRecordAsHeader()
        );
    }

    /**
     * Primary constructor.
     *
     * @param file   The CSV file.
     * @param mapper The mapperto map the output to instances of {@code T}.
     * @param format The {@link CSVFormat} of the resource.
     */
    public FileCsv(final Path file, final CsvMapper<T> mapper, final CSVFormat format) {
        this.file = file;
        this.mapper = mapper;
        this.format = format;
    }

    @Override
    public List<T> map() {
        this.assertFileExists();
        try (final BufferedReader reader = Files.newBufferedReader(this.file)) {
            return this.format.parse(reader).getRecords().stream()
                    .map(this.mapper::map)
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
