package com.arsensim;

import org.apache.commons.csv.CSVRecord;

@FunctionalInterface
public interface CsvRecordMapper<T> {

    public T map(CSVRecord record);

}
