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

/**
 * Builds an SQL query from the user provided information. Supporting schema-less queries.
 */
public class QueryBuilderClient {
    private static Logger logger = LogManager.getLogger(QueryBuilderClient.class.getName());

    private String executionID;
    private String[] attributes;
    private String[] collections;

    public QueryBuilderClient(String executionID, String[] attributes, String[] collections) {
        this.executionID = executionID;
        this.attributes = attributes;
        this.collections = collections;
    }

    public QueryBuilderClient(String executionID) {
        this.executionID = executionID;
    }

    public QueryBuilderClient() {
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
        return HzClient.readValuesFromMultiMap(executionID + DatacafeConstants.META_INDICES_MULTI_MAP_SUFFIX,
                DatacafeConstants.DATASOURCES_MAP_ENTRY_KEY);
    }

    /**
     * Display all the data sources in the data lake.
     */
    public void displayAllDataSources() {
        Collection<String> datasources = getDataSources();
        datasources.forEach(logger::info);
    }

    /**
     * Builds the Query statement
     * @return the Query statement
     */
    public String buildQueryStatement() {
        String from = HzClient.readValues(executionID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_FROM_ENTRY_KEY);
        return buildSelectStatement() + from;
    }

    private String buildSelectStatement() {
        String out = "SELECT ";
        for (int i = 0; i < collections.length; i++) {
            out += HzClient.readValues(executionID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, collections[i]) +
                    "." + attributes[i];
            if (i < collections.length - 1) {
                out += ", ";
            } else {
                out += "\n";
            }
        }
        return out;
    }
}
