/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
