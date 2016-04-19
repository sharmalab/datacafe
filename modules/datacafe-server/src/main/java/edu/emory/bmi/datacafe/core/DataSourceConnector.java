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

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * The connector interface for each of the data sources
 */
public interface DataSourceConnector {
    /**
     * Execute a query on db1.dbCollection1 and db2.dbCollection2 and returns the final result as a string
     * @param db1 database1
     * @param dbCollection1 collection1 in database1
     * @param db2 database2
     * @param dbCollection2 collection2 in database2
     * @param joinQuery the query to be executed
     * @return the results as a String
     */
    public String getJoinedResult(DB db1, DBCollection dbCollection1, DB db2, DBCollection dbCollection2, String
            joinQuery);
}
