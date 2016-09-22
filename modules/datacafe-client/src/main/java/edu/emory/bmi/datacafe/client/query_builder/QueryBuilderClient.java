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
package edu.emory.bmi.datacafe.client.query_builder;

import edu.emory.bmi.datacafe.client.core.HzClient;
import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import edu.emory.bmi.datacafe.core.conf.QueryWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Builds an SQL query from the user provided information. Supporting schema-less queries.
 */
public class QueryBuilderClient {
    private static Logger logger = LogManager.getLogger(QueryBuilderClient.class.getName());

    private String datalakeID;
    private String[] attributes;
    private String[] collections;

    /**
     * Constructor, when the user does not offer the tables that each of the attribute belong to.
     *
     * @param datalakeID the datalake ID
     * @param attributes the array of attributes that the user interested in.
     */
    public QueryBuilderClient(String datalakeID, String[] attributes) {
        this.datalakeID = datalakeID;
        this.attributes = attributes;
        String collections[] = new String[attributes.length];
        String temp;

        for (int i = 0; i < attributes.length; i++) {
            temp = HzClient.getAnAttributeFromMultiMap(
                    datalakeID + DatacafeConstants.ATTRIBUTES_TABLES_MAP_SUFFIX, attributes[i]);

            collections[i] = QueryWrapper.getDestinationInDataLakeFromDrill(temp);
        }
        this.collections = collections;
    }

    /**
     * Constructor, when the user offers the collections that each of the attribute belongs to.
     *
     * @param datalakeID  the datalake ID
     * @param attributes  the array of attributes
     * @param collections the collections the attributes belong to - in a 1-1 mapping between the attributes and the
     *                    collections.
     */
    public QueryBuilderClient(String datalakeID, String[] attributes, String[] collections) {
        this.datalakeID = datalakeID;
        this.attributes = attributes;
        this.collections = collections;
    }

    public QueryBuilderClient(String datalakeID) {
        this.datalakeID = datalakeID;
    }

    public QueryBuilderClient() {
        this.datalakeID = DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP;
    }

    /**
     * Get all the data sources in the data lake.
     *
     * @return the data sources in the lake.
     */
    public Collection<String> getDataSources() {
        return HzClient.readValuesFromMultiMap(datalakeID + DatacafeConstants.META_INDICES_MULTI_MAP_SUFFIX,
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
     * Builds the Query statement after reformatting
     * queryBuilderClient.buildQueryStatement("SUBJECT_ID < 100 OR SUBJECT_ID = 120");
     *
     * @param whenQueryFromUser the user given when query
     * @return the Query statement
     */
    public String reformatAndBuildQueryStatement(String whenQueryFromUser) {
        String whenQueryReformatted = reformatQueryStatement(whenQueryFromUser);
        return buildQueryStatement("( " + whenQueryReformatted + " )");
    }

    /**
     * Builds the Query statement
     *
     * @param fullyFormattedWhenQueryFromUser the user given when query
     * @return the Query statement
     */
    public String buildQueryStatement(String fullyFormattedWhenQueryFromUser) {
        return buildQueryStatement() + " AND " + fullyFormattedWhenQueryFromUser;
    }

    /**
     * Reformat the query statement to include the table indices.
     *
     * @param whenQueryFromUser the user given when query
     * @return the reformatted when query.
     */
    public String reformatQueryStatement(String whenQueryFromUser) {
        String out = whenQueryFromUser;
        for (String attribute : attributes) {
            out = out.replace(attribute, getFullyQualifiedAttributeName(attribute));
        }
        return out;
    }


    private String getFullyQualifiedAttributeName(String attribute) {
        String tableIndex = getTableIndex(attribute);
        return getFullyQualifiedAttributeName(attribute, tableIndex);
    }

    private String getFullyQualifiedAttributeName(String attribute, String tableIndex) {
        return tableIndex + "." + attribute;
    }

    private String getTableIndex(String attribute) {
        String table = HzClient.getAnAttributeFromMultiMap(
                datalakeID + DatacafeConstants.ATTRIBUTES_TABLES_MAP_SUFFIX, attribute);

        String tableFullURI = QueryWrapper.getDestinationInDataLakeFromDrill(table);

        return HzClient.readValues(
                datalakeID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, tableFullURI);
    }

    /**
     * Builds the Query statement
     *
     * @return the Query statement
     */
    public String buildQueryStatement() {
        String from = HzClient.readValues(datalakeID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_FROM_ENTRY_KEY);
        String where = HzClient.readValues(datalakeID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_WHERE_ENTRY_KEY);

        return buildSelectStatement() + from + where;
    }

    private String buildSelectStatement() {
        String out = "SELECT ";
        for (int i = 0; i < collections.length; i++) {
            out += getFullyQualifiedAttributeName(attributes[i], HzClient.readValues(datalakeID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, collections[i]));
            if (i < collections.length - 1) {
                out += ", ";
            } else {
                out += "\n";
            }
        }
        return out;
    }
}
