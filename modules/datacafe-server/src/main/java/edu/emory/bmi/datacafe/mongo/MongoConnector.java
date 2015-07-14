/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.mongo;

import com.mongodb.*;
import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.core.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connects to the Mongo database
 */
public class MongoConnector implements Connector {
    private static Logger logger = LogManager.getLogger(MongoConnector.class.getName());
    private static MongoConnector mongoConnector = new MongoConnector();


    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            MongoConstants.CLIENT_HOST, MongoConstants.CLIENT_PORT));

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
    public String constructQuery(String... queryEntries) {
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
