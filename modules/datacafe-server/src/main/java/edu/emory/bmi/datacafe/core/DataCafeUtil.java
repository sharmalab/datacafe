/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.core;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods of Data Cafe
 */
public final class DataCafeUtil {

    /**
     * Double quotes mess up with the CSV parsing. First double the double quote before wrapping the entire thing in
     * a pair of double quotes.
     * @return double quote it double
     */
    public static String doubleTheDoubleQuote(String text) {
        return text.replaceAll("\"", "\"\"");
    }

    /**
     * Wraps the tex with double quotes.
     * @param text the text
     * @return the wrapped text.
     */
    public static String doublequote(String text) {
        if (StringUtils.isNumeric(text)) { //checking only for numeric now. todo: all types.
            return text;
        } else {
            return "\"" + text + "\"";
        }
    }

    /**
     * Construct a string from a collection
     *
     * @param collection the collection
     * @return the collection as a comma separated line
     */
    public static String constructStringFromCollection(Collection collection) {
        // This line of code is genius (despite looking ugly).
        // Future maintainer: Be careful if you are trying to refactor it.
        return (String) collection.stream()
                .map(i -> doublequote(doubleTheDoubleQuote(i.toString())))
                .collect(Collectors.joining(","));
    }

    /**
     * Construct a string from an array of objects
     *
     * @param tempArray the object array
     * @return the array as a comm separated line
     */
    public static String constructStringFromObjectArray(Object[] tempArray) {
        return constructStringFromList(Arrays.asList(tempArray));
    }

    /**
     * Construct a string from a list
     *
     * @param tempList the list
     * @return the list as a comma separated line.
     */
    public static String constructStringFromList(List tempList) {
        return constructStringFromCollection(tempList);
    }
}
