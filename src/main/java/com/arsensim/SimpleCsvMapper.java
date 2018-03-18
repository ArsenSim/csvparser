package com.arsensim;

import org.apache.commons.csv.CSVRecord;

// todo implement simple reflective set operations
public class SimpleCsvMapper<T> implements CsvRecordMapper<T> {

    @Override
    public T map(CSVRecord record) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
