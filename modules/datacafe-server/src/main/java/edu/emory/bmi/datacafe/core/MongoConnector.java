/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.core;

import com.mongodb.*;

/**
 * Connects to the Mongo database
 */
public class MongoConnector implements Connector {

    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            DatacafeConstants.MONGO_CLIENT_HOST, DatacafeConstants.MONGO_CLIENT_PORT));

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
}
