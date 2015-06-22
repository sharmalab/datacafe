/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Core class for Jongo Integration
 */
public class JongoConnector {

    /**
     * Initializes the MongoCollection using Jongo.
     * @param database the data base name
     * @param collection the collection name
     * @return Jongo object
     */
    public static MongoCollection initialize(String database, String collection) {
        DB db = new MongoClient().getDB(database);
        Jongo jongo = new Jongo(db);
        return jongo.getCollection(collection);
    }

    /**
     * Initializes the given collection in a data base
     * @param database the data base name
     * @param collection the collection name
     * @return the Jongo object.
     */
    public static MongoCollection initCollection(String database, String collection) {
        MongoConnector.printMongoCollection(database, collection);
        return initialize(database, collection);
    }
}
