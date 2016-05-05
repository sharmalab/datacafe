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

import edu.emory.bmi.datacafe.core.conf.CoreConfigReader;
import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A minimalistic representation of a data source
 */
public class DataSourcesRegistry {
    private static List<String> fullNames;

    public static void init() {
        fullNames = new ArrayList<>();
    }

    /**
     * Populate the datasources full names list. A one-to-one mapping is assumed across the databases and collections.
     * @param databases the databases.
     * @param collections the collections.
     */
    public static void addDataSources(String[] databases, String[] collections) {
        for (int i = 0; i < collections.length; i++) {
            fullNames.add(constructFullDataSourceName(databases[i], collections[i]));
        }
    }

    /**
     * Add a single data source to the list.
     * @param database the name of the data base
     * @param collection the name of the collection
     */
    public static void addDataSource(String database, String collection) {
        fullNames.add(constructFullDataSourceName(database, collection));
    }

    /**
     * Gets the fullnames of the datasources as an array
     * @return the full name of the data sources array
     */
    public static String[] getFullNamesAsArray() {
        String[] fullNameArray = new String[fullNames.size()];

        for (int i = 0; i < fullNames.size(); i++) {
            fullNameArray[i] = fullNames.get(i);
        }
        return fullNameArray;
    }

    /**
     * Constructs full name for the data stores in data lake
     *
     * @param data data elements
     * @return fullName
     */
    protected static String constructFullDataSourceName(String... data) {
        String fullName = "";
        if (data.length > 0) {
            for (String element : data) {
                if (!fullName.equals("")) {
                    fullName += "_";
                }
                fullName += element;
            }
        } else {
            fullName = UUID.randomUUID().toString();
        }
        return fullName;
    }

    /**
     * Gets the full data source name along with its extension, as ds<extension>.
     * For example, data_source -> data_source.csv
     * @param dsName the full data sourceName.
     * @return the full name with extension
     */
    public static String getFullDSNameWithExtension(String dsName) {
        return dsName + CoreConfigReader.getFileExtension();
    }

    /**
     * Gets the full data source name along with its extension, as ds<extension>.
     * For example, database,collection -> database_collection.csv
     * @param data the data source attributes
     * @return the full name with extension
     */
    public static String getFullDSNameWithExtension(String... data) {
        return getFullDSNameWithExtension(constructFullDataSourceName(data));
    }

    /**
     * Wrap the datasource by DatacafeConstants.SQL_WRAP_CHARACTER.
     * @param datasource the data source name
     * @return the name, wrapped.
     */
    public static String sqlWrapTheDataSource(String datasource) {
        return DatacafeConstants.SQL_WRAP_CHARACTER + datasource + DatacafeConstants.SQL_WRAP_CHARACTER;
    }
}
