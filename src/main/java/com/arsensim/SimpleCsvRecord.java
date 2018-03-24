package com.arsensim;

import java.util.HashMap;
import java.util.Map;

public class SimpleCsvRecord<I> implements CsvRecord<I> {

    private final Map<I, String> values;

    public SimpleCsvRecord(final Map<I, String> values) {
        this.values = values;
    }

    @Override
    public String get(final I column) {
        if (!this.values.containsKey(column)) {
            throw new CsvException(
                    String.format("Column %s is not present in csv record %s", column, this)
            );
        }
        return this.values.get(column);
    }

    @Override
    public Map<I, String> toMap() {
        return new HashMap<>(this.toMap());
    }

}
