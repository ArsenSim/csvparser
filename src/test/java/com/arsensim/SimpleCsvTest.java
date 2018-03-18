package com.arsensim;

import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class SimpleCsvTest {

    @Test(expected = CsvException.class)
    public void testParseNoFile() {
        final SimpleCsv parser = new SimpleCsv(
                Paths.get("inexisting/path")
        );
        parser.map();
    }

    @Test
    public void testParse() {
        final SimpleCsv<ExampleOutput> parser = new SimpleCsv<>(
                Paths.get("src/test/resources/CommaDelimitedTwoRows.csv")
        );
        final List<ExampleOutput> mappedLines = parser.map();
        assertThat(mappedLines, hasSize(2));
    }

    private static final class ExampleOutput {
    }

}
