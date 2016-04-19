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
package edu.emory.bmi.datacafe.interfaces;

import java.util.*;

/**
 * Makes a warehouse from the merger.
 */
public interface WarehouseConnector {

    /**
     * Makes a file from the results.
     *
     * @param fileName Name of the file
     * @param lines    lines to be written.
     */
    public abstract void createFile(String fileName, List<String> lines);

    /**
     * Write the data to the data warehouse
     *
     * @param datasourcesNames the identifiers of the data sources
     * @param texts            the data to be written, in text format.
     * @param queries,         queries for the data.
     */
    public abstract void writeToWarehouse(String[] datasourcesNames, List<String>[] texts, String[] queries);
}
