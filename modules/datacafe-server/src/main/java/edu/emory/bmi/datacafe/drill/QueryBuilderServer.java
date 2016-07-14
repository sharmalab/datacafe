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

import com.hazelcast.core.MultiMap;
import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import edu.emory.bmi.datacafe.hazelcast.HzServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Builds and stores relationships in Hazelcast server cluster for the data sources.
 */
public class QueryBuilderServer extends HzServer {
    private static Logger logger = LogManager.getLogger(QueryBuilderServer.class.getName());
    private String executionID;

    public QueryBuilderServer(String executionID) {
        this.executionID = executionID;
    }
    /**
     * Builds the From statement
     *
     * @return the From statement
     */
    public String buildTheFromStatement() {
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
        logger.info("From statement at Server: " + from);

        return from;
    }

    /**
     * Reads an entry from the multi-map
     * invoke: HzClient.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the values of the entry.
     */
    public static Collection<String> readValuesFromMultiMap(String mapName, String key) {
        MultiMap<String, String> map = firstInstance.getMultiMap(mapName);
        return map.get(key);
    }
}
