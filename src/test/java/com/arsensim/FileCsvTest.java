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

import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This class contains tests for {@link FileCsv}.
 */
public class FileCsvTest {

    /**
     * Tests that {@link CsvException} is thrown when the file doesn't exist.s
     */
    @Test(expected = CsvException.class)
    public void testParseNoFile() {
        final FileCsv<ExampleOutput> parser = new FileCsv<>(
                Paths.get("inexisting/path"),
                ExampleOutput.class
        );
        parser.map();
    }

    /**
     * Tests the happy flow of the {@link FileCsv}.
     */
    @Test
    public void testParse() {
        final FileCsv<ExampleOutput> parser = new FileCsv<>(
                Paths.get("src/test/resources/HeadedCommaDelimitedTwoRows.csv"),
                ExampleOutput.class
        );
        final List<ExampleOutput> mappedLines = parser.map();
        assertThat(mappedLines, hasSize(2));
        final ExampleOutput fooBar = mappedLines.get(0);
        assertThat(fooBar.getX(), is("foo"));
        assertThat(fooBar.getY(), is("bar"));
        final ExampleOutput bazBuf = mappedLines.get(1);
        assertThat(bazBuf.getX(), is("baz"));
        assertThat(bazBuf.getY(), is("buf"));
    }

}
