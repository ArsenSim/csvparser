package com.arsensim;

import org.apache.commons.csv.CSVRecord;

public class SimpleCsvMapper<T> implements CsvRecordMapper<T> {

    @Override
    public T map(CSVRecord record) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
