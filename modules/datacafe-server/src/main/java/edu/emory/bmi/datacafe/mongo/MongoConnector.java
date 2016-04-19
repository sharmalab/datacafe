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

import com.mongodb.*;
import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.interfaces.DataSourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connects to the Mongo database
 */
public class MongoConnector implements DataSourceConnector {
    private static Logger logger = LogManager.getLogger(MongoConnector.class.getName());
    private static MongoConnector mongoConnector = new MongoConnector();


    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            MongoConstants.CLIENT_HOST, ConfigReader.getDataServerPort()));

    public static String[] constructQueries(String[][] params) {
        String[] queries = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            queries[i] = constructQuery(params[i]);
        }
        return queries;
    }

    public static String constructQuery(String[] params) {
        String out = "";
        for (int i = 0; i < params.length; i++) {
            out += params[i] + " " + "string";
            if (i < params.length - 1) {
                out += DatacafeConstants.DELIMITER;
            }
        }
        return " (" + out + ") row format delimited fields " +
                "terminated by '" + DatacafeConstants.DELIMITER + "' stored as textfile";
    }

    /**
     * Gets cursor for a collection in a given database.
     * @param database the data base
     * @param collection the collection in the data base
     * @return a cursor that can be iterated.
     */
    public DBCursor getCursor(String database, String collection) {
        DB db = mongoClient.getDB(database);
        DBCollection dbCollection = db.getCollection(collection);
        return dbCollection.find();
    }

    /**
     * Return a long query by combining parts of the query through a string concatenation.
     * @param queryEntries parts of the query
     * @return the query
     */
    public String constructQueryFromParts(String... queryEntries) {
        String query = "";
        for (String queryEntry : queryEntries) {
            query += queryEntry;
        }
        return query;
    }

    @Override
    public String getJoinedResult(DB db1, DBCollection dbCollection1, DB db2, DBCollection dbCollection2,
                                  String joinQuery) {
        return null;
    }

    /**
     * Prints the collection to console
     * @param database the data base
     * @param collection the collection in the data base
     */
    public static void printMongoCollection(String database, String collection) {
        DBCursor clinicalCursor = mongoConnector.getCursor(database, collection);
        if (logger.isDebugEnabled()) {
            while (clinicalCursor.hasNext()) {
                logger.debug(clinicalCursor.next());
            }
        }
    }
}
