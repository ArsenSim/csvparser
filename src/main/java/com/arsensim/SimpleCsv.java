package com.arsensim;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SimpleCsv<T> implements Csv<T> {

    private final Path file;
    private final CSVFormat format;
    private final CsvRecordMapper<T> mapper;

    public SimpleCsv(final Path file) {
        this(file, CSVFormat.DEFAULT, new SimpleCsvMapper<>());
    }

    // TODO remove {@link CSVFormat} from public API or think how to make it compatible irregarding the version the client may already be using
    public SimpleCsv(final Path file, final CSVFormat format, CsvRecordMapper<T> mapper) {
        this.file = file;
        this.format = format;
        this.mapper = mapper;
    }

    public List<T> map() {
        this.assertFileExists(this.file);
        try (final BufferedReader reader = Files.newBufferedReader(this.file)) {
            final ArrayList<T> mappedRecords = new ArrayList<>();
            for (final CSVRecord record : this.format.parse(reader)) {
                mappedRecords.add(mapper.map(record));
            }
            return mappedRecords;
        } catch (IOException e) {
            throw new CsvException(
                    String.format("Exception while reading file %s", this.file),
                    e
            );
        }
    }

    protected void assertFileExists(final Path file) {
        if (!Files.exists(file)) {
            throw new CsvException(
                    String.format("File %s not found", file)
            );
        }
    }

}
