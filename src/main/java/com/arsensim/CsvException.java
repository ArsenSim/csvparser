package com.arsensim;

import java.io.IOException;

public final class CsvException extends RuntimeException {

    public CsvException(final String message) {
        super(message);
    }

    public CsvException(final String format, final IOException e) {
        super(format, e);
    }
}
