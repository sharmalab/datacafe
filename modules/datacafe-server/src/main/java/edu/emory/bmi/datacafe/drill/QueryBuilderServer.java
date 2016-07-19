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
import edu.emory.bmi.datacafe.core.conf.QueryWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


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
        return from;
    }


    /**
     * Reads an entry from the multi-map
     * invoke: QueryBuilderServer.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the values of the entry.
     */
    public Collection<String> readValuesFromMultiMap(String mapName, String key) {
        MultiMap<String, String> map = firstInstance.getMultiMap(mapName);
        return map.get(key);
    }


    /**
     * Reads an entry from the map
     * invoke: QueryBuilderServer.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the value of the entry.
     */
    public String readValuesFromMap(String mapName, String key) {
        ConcurrentMap<String, String> map = firstInstance.getMap(mapName);
        return map.get(key);
    }


    /**
     * Builds the Where statement when no relations.json was present.
     *
     * @return the Where statement
     */
    public String buildTheWhereStatementNoJSON() {
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

    /**
     * Builds the Where statement.
     *
     * @return the Where statement
     */
    public String buildTheWhereStatement() {
        parse();

        //todo: remove.

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

    /**
     * Parses the JSON file to the Hazelcast maps.
     *
     * @return true if successfully parses the JSON into the in-memory data grid.
     */
    public void parse() {
        String where = "WHERE ";

        InputStream input = null;
        try {
            input = new FileInputStream(DatacafeConstants.RELATIONS_DATA_FILE);
        } catch (FileNotFoundException ex) {
            try {
                input = new FileInputStream(DatacafeConstants.RELATIONS_DATA_FILE_ALT);
            } catch (Exception e) {
                logger.error("Error in loading the relations file.. ", ex);
            }
        }
        JsonReader reader = Json.createReader(input);
        JsonObject jsonObject = reader.readObject();
        reader.close();

        Set<String> primaryCollections = jsonObject.keySet();

        for (String rawCollection : primaryCollections) {
            String rawDataSource = QueryWrapper.getDestinationInDataLakeFromDrill(rawCollection);

            // shorter form as the map name.
            String collection = readValuesFromMap(executionID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX,
                    rawDataSource);

            JsonObject secondaryCollectionObject = jsonObject.getJsonObject(rawCollection);

            Set<String> secondaryCollections = secondaryCollectionObject.keySet();
            for (String secondaryRawCollection : secondaryCollections) {
                String rawSecondaryDataSource = QueryWrapper.getDestinationInDataLakeFromDrill(secondaryRawCollection);
                String secondaryCollection = readValuesFromMap(executionID +
                                DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, rawSecondaryDataSource);

                HzServer.addValueToMultiMap(executionID + DatacafeConstants.LINKS_TO_MAP_SUFFIX + collection,
                        secondaryCollection, String.valueOf(secondaryCollectionObject.get(secondaryRawCollection)));

                logger.info("Collection: " + collection + " . Secondary Collection: " + secondaryCollection +
                        " . Attribute: " + String.valueOf(secondaryCollectionObject.get(secondaryRawCollection)));
            }
        }
    }

}
