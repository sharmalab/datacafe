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
package edu.emory.bmi.datacafe.drill;

import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import edu.emory.bmi.datacafe.hazelcast.HzServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Attempts to build the from and where statements when there was no relations were explicitly asked by the user.
 * These methods need more intelligent implementations to construct the queries from all the data sources.
 * For now, assume the relations.json present, and use QueryBuilderServer instead.
 */
public class QueryBuilderServerNoJSON extends HzServer {

    private static Logger logger = LogManager.getLogger(QueryBuilderServerNoJSON.class.getName());
    private String executionID;

    public QueryBuilderServerNoJSON(String executionID) {
        this.executionID = executionID;
    }

    /**
     * Attempts to build the from and where statements. Use QueryBuilderServer.buildStatements() instead.
     */
    public void buildStatementsNoJSON() {
        buildTheFromStatement();
        buildTheWhereStatement();
    }

    /**
     * Builds the From statement when no relations.json was present
     *
     * @return the From statement
     */
    private String buildTheFromStatement() {
        Collection<String> datasources = readValuesFromMultiMap(
                executionID + DatacafeConstants.META_INDICES_MULTI_MAP_SUFFIX,
                DatacafeConstants.DATASOURCES_MAP_ENTRY_KEY);
        String from = "FROM ";
        int i = 1;
        String index;

        for (String datasource : datasources) {
            index = executionID + i++;
            addValueToMap(executionID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, datasource, index);
            from += datasource + " " + index;
            if (i <= datasources.size()) {
                from += ",\n";
            } else {
                from += "\n";
            }
        }
        HzServer.addValueToMap(executionID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_FROM_ENTRY_KEY, from);
        return from;
    }


    /**
     * Builds the Where statement when no relations.json was present.
     *
     * @return the Where statement
     */
    private String buildTheWhereStatement() {
        String where = "WHERE ";
        Collection<String> attributes = readValuesFromMultiMap(
                executionID + DatacafeConstants.META_INDICES_MULTI_MAP_SUFFIX,
                DatacafeConstants.ATTRIBUTES_MAP_ENTRY_KEY);

        boolean beginning = true;

        for (String attribute : attributes) {
            Collection<String> rawDataSources = readValuesFromMultiMap(executionID, attribute);
            String[] datasources = new String[rawDataSources.size()];
            String key;
            String value;
            int i = 0;
            for (String rawDataSource : rawDataSources) {
                datasources[i++] = readValuesFromMap(executionID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX,
                        rawDataSource);
            }
            key = datasources[0] + "." + attribute;

            for (int j = 1; j < rawDataSources.size(); j++) {
                if (beginning) {
                    beginning = false;
                } else {
                    where += " AND ";
                }
                value = datasources[j] + "." + attribute;
                HzServer.addValueToMap(executionID + DatacafeConstants.RELATIONS_MAP_SUFFIX, key, value);
                where += key + " = " + value;
            }
        }

        HzServer.addValueToMap(executionID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_WHERE_ENTRY_KEY, where);
        return where;
    }

}
