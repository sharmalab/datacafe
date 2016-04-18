/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.mongo;

import edu.emory.bmi.datacafe.core.AbstractQueryConstructor;

/**
 * Representation of a Mongo Data Cafe Query
 */
public class QueryConstructor extends AbstractQueryConstructor {

    private String database;

    private String constraints;

    public QueryConstructor(String database, String collection, String constraints, String... interestedAttributes) {
        super(collection, interestedAttributes);
        this.database = database;
        this.constraints = constraints;
    }

    public QueryConstructor(String database, String collection, String... interestedAttributes) {
        super(collection, interestedAttributes);
        this.database = database;
        this.constraints = "";
    }

    public String getDatabase() {
        return database;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
}
