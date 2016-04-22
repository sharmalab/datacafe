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
}
