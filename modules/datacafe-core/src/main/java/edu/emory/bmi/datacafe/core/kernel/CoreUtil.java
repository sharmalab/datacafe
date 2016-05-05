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
package edu.emory.bmi.datacafe.core.kernel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods of Data Cafe Core
 */
public final class CoreUtil {
    /**
     * Construct a string from a collection, with each item in a new line
     *
     * @param collection the collection
     * @return the collection - each item as a line.
     */
    public static String constructPageFromCollection(Collection collection) {
        return (String) collection.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Construct a string from an array of objects
     *
     * @param tempArray the object array
     * @return the array as a comm separated line
     */
    public static String constructStringFromObjectArray(Object[] tempArray) {
        return constructPageFromList(Arrays.asList(tempArray));
    }

    /**
     * Construct a string from a list
     *
     * @param tempList the list
     * @return the list as a comma separated line.
     */
    public static String constructPageFromList(List tempList) {
        return constructPageFromCollection(tempList);
    }

}
