package com.arsensim;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedCsv<T> implements Csv<T> {

    private final Csv<T> decoree;
    private final Comparator<T> comparator;


    public OrderedCsv(final Csv<T> decoree, Comparator<T> comparator) {
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
