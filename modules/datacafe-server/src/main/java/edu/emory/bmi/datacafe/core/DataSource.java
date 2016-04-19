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
package edu.emory.bmi.datacafe.core;

import java.util.UUID;

/**
 * A minimalistic representation of a data source
 */
public class DataSource {
    private String database;
    private String collection;
    private String fullName;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public DataSource(String database, String collection) {
        this.database = database;
        this.collection = collection;
        this.fullName = constructFullDataSourceName(database, collection);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Constructs full name for the data stores in data lake
     *
     * @param data data elements
     * @return fullName
     */
    protected String constructFullDataSourceName(String... data) {
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
}
