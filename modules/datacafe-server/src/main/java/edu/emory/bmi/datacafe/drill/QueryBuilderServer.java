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
import edu.emory.bmi.datacafe.core.conf.QueryWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collection;
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
    private String datalakeID;
    private boolean firstInTheWhere = true;

    public QueryBuilderServer(String datalakeID) {
        this.datalakeID = datalakeID;
    }


    /**
     * Build the from and where statements. Execution order matters. Do not change it. As of now, the construction of
     * From statement has a dependency on the construction of the where statement.
     */
    public void buildStatements() {
        populateDataSourceIndicesMap();
        buildTheWhereStatement();
        buildTheFromStatement();
    }


    /**
     * Builds the From statement
     *
     */
    private void populateDataSourceIndicesMap() {
        Collection<String> datasources = readValuesFromMultiMap(
                datalakeID + DatacafeConstants.META_INDICES_MULTI_MAP_SUFFIX,
                DatacafeConstants.DATASOURCES_MAP_ENTRY_KEY);
        int i = 1;
        String index;

        for (String datasource : datasources) {
            index = datalakeID + i++;
            addValueToMap(datalakeID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, datasource, index);
        }
    }


    /**
     * Builds the Where statement.
     *
     * @return the Where statement
     */
    private String buildTheWhereStatement() {
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
            String collection = readValuesFromMap(datalakeID + DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX,
                    rawDataSource);

            addValueToMap(datalakeID + DatacafeConstants.CHOSEN_COLLECTIONS_MAP_SUFFIX, rawDataSource, collection);

            JsonObject secondaryCollectionObject = jsonObject.getJsonObject(rawCollection);

            Set<String> secondaryCollections = secondaryCollectionObject.keySet();
            for (String secondaryRawCollection : secondaryCollections) {
                String rawSecondaryDataSource = QueryWrapper.getDestinationInDataLakeFromDrill(secondaryRawCollection);
                String secondaryCollection = readValuesFromMap(datalakeID +
                                DatacafeConstants.COLLECTION_INDICES_MAP_SUFFIX, rawSecondaryDataSource);

                addValueToMap(datalakeID + DatacafeConstants.CHOSEN_COLLECTIONS_MAP_SUFFIX, rawSecondaryDataSource,
                        secondaryCollection);

                String attribute = secondaryCollectionObject.getString(secondaryRawCollection);

                HzServer.addValueToMultiMap(datalakeID + DatacafeConstants.LINKS_TO_MAP_SUFFIX + collection,
                        secondaryCollection, attribute);

                if (firstInTheWhere) {
                    firstInTheWhere = false;
                } else {
                    where += " AND ";
                }
                where += collection + "." + attribute + " = "  + secondaryCollection + "." + attribute;
            }
        }
        HzServer.addValueToMap(datalakeID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_WHERE_ENTRY_KEY, where);
        return where;
    }

    /**
     * Builds the From statement
     *
     * @return the From statement
     */
    private String buildTheFromStatement() {
        Collection<String> rawDataSources = getKeysFromMap(datalakeID + DatacafeConstants.CHOSEN_COLLECTIONS_MAP_SUFFIX);
        String from = "FROM ";
        int i = 1;
        String index;

        for (String rawDataSource : rawDataSources) {
            i++;
            index = readValuesFromMap(datalakeID + DatacafeConstants.CHOSEN_COLLECTIONS_MAP_SUFFIX, rawDataSource);
            from += rawDataSource + " " + index;
            if (i <= rawDataSources.size()) {
                from += ",\n";
            } else {
                from += "\n";
            }
        }
        HzServer.addValueToMap(datalakeID + DatacafeConstants.META_INDICES_SINGLE_MAP_SUFFIX,
                DatacafeConstants.SQL_FROM_ENTRY_KEY, from);
        return from;
    }
}
