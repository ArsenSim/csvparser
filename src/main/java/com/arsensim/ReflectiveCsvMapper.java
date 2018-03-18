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

import org.apache.commons.csv.CSVRecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is an implementation of {@link CsvMapper} that creates a
 * new object of {@code T} and populates it with data using reflective
 * operations. Setter methods are used.
 *
 * @param <T> the type to which each csv record will be mapped.
 * @author Arsen Simonean (arsensim08@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class ReflectiveCsvMapper<T> implements CsvMapper<T> {

    private final Class<T> clazz;

    /**
     * A constructor that expects as a parameter the target class of mapped
     * object.
     *
     * @param clazz The class to which the output will be mapped.
     */
    public ReflectiveCsvMapper(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(final CSVRecord record) {
        final T resultObject = this.newEmptyResultObject();
        this.formatRecord(record).forEach((columnName, value) -> {
            final Method setterMethod = this.findSetterMethod(columnName);
            this.setValue(setterMethod, resultObject, value);
        });
        return resultObject;
    }

    /**
     * Maps the {@code CSVRecord} to a {@code Map} with keys being lower case.
     *
     * @param record A {@code CSVRecord} to map.
     * @return The mapped record.
     */
    private Map<String, String> formatRecord(final CSVRecord record) {
        final Map<String, String> initialMap = record.toMap();
        if (initialMap.isEmpty()) {
            throw new CsvException(
                    String.format("Cannot use %s with a headless csv", this.getClass())
            );
        }
        return initialMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toLowerCase().replace(" ", ""),
                        Map.Entry::getValue
                ));
    }

    /**
     * Finds an appropriate setter {@code Method} in the {@code T} class that
     * maps to the csv record that is lowercase and has no spaces.
     *
     * @param csvColumnName The name of the setter method to search without
     *                      "set" substring.
     * @return The appropriate setter {@code Method} found.
     */
    private Method findSetterMethod(final String csvColumnName) {
        return Stream.of(this.clazz.getDeclaredMethods())
                .filter(method ->
                        method.getName()
                                .toLowerCase()
                                .replace("set", "")
                                .equals(csvColumnName)
                ).findAny()
                .orElseThrow(() -> new CsvException(
                        String.format(
                                "No appropriate setter method found for csv column %s in class %s",
                                csvColumnName,
                                this.clazz
                        )
                ));
    }

    /**
     * Creates an empty instance of {@code T} using default constructor.
     *
     * @return The newly created instance of {@code T}.
     */
    private T newEmptyResultObject() {
        try {
            return this.clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CsvException(
                    String.format(
                            "Could not construct an empty object of %s using no-arg constructor",
                            this.clazz
                    )
            );
        }
    }

    /**
     * Sets a value on an object using the setter method.
     *
     * @param setter The setter {@code Method} to invoke.
     * @param object The object on which to invoce the setter {@code Method}.
     * @param value  The value which to set on the object.
     */
    private void setValue(final Method setter, final T object, final String value) {
        try {
            setter.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CsvException(
                    String.format(
                            "Could not set value for object of class %s using method %s",
                            this.clazz,
                            setter.getName()
                    )
            );
        }
    }

}
