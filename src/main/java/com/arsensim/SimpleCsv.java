package com.arsensim;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleCsv<I, E> implements Csv<I, E> {

    private final List<CsvRecord<I>> records;

    public SimpleCsv(List<CsvRecord<I>> records) {
        this.records = records;
    }

    @Override
    public List<E> map(final CsvRecordMapper<I, ? extends E> mapper) {
        return this.records.stream()
                .peek(this::validate)
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    private void validate(final CsvRecord<I> csvRecord) {
        // todo implement basic validations
    }

}
