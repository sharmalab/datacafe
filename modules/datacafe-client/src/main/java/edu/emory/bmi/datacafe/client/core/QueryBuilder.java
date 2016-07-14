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
package edu.emory.bmi.datacafe.client.core;

import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds an SQL query from the user provided information. Supporting schema-less queries.
 */
public class QueryBuilder {
    private static Logger logger = LogManager.getLogger(QueryBuilder.class.getName());

    private String executionID;
    private Map<String, String> collectionIndexMap = new HashMap<>();

    public QueryBuilder(String executionID) {
        this.executionID = executionID;
    }

    public QueryBuilder() {
        this.executionID = DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP;
    }

    /**
     * Prints the tables that has the any given attribute.
     * @param attribute the attribute to be probed.
     */
    public void displayTablesWithAttribute(String attribute) {
        HzClient.printValuesFromMultiMap(executionID, attribute);
    }

    /**
     * Prints the tables that has the any given attribute.
     * @param attributes the attributes to be probed as an array.
     */
    public void displayTablesWithAttribute(String[] attributes) {
        for (String attribute: attributes) {
            HzClient.printValuesFromMultiMap(executionID, attribute);
        }
    }

    /**
     * Get all the data sources in the data lake.
     * @return the data sources in the lake.
     */
    public Collection<String> getDataSources() {
        return HzClient.readValuesFromMultiMap(executionID, DatacafeConstants.DATASOURCES_MAP_ENTRY_KEY);
    }

    /**
     * Display all the data sources in the data lake.
     */
    public void displayAllDataSources() {
        Collection<String> datasources = getDataSources();
        datasources.forEach(logger::info);
    }

    /**
     * Builds the FROM statement
     * @return the FROM statement
     */
    public String buildFromStatement() {
        Collection<String> datasources = getDataSources();
        String out = "FROM ";
        int i = 1;
        String index;

        for (String datasource: datasources) {
            index = "t"+i++;
            collectionIndexMap.put(datasource, index);
            out += datasource + " " + index;
            if (i < datasources.size()) {
                out += ",\n";
            } else {
                out += "\n";
            }
        }
        return out;
    }
}
