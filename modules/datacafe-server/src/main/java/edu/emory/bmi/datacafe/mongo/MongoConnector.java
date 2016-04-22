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

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import edu.emory.bmi.datacafe.conf.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

/**
 * Connects to the Mongo database
 */
public class MongoConnector {
    private static Logger logger = LogManager.getLogger(MongoConnector.class.getName());
    private static MongoConnector mongoConnector = new MongoConnector();


    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            ConfigReader.getDataServerHost(), ConfigReader.getDataServerPort()));

    /**
     * Iterates a collection in a given database.
     *
     * @param database   the database
     * @param collection the collection in the data base
     * @return an iterable document.
     */
    public static FindIterable<Document> getCollection(String database, String collection) {
        MongoDatabase db = mongoClient.getDatabase(database);
        return db.getCollection(collection).find();
    }

    /**
     * Prints an iterable collection
     *
     * @param iterable the collection iterable
     */
    public static void printMongoCollection(FindIterable<Document> iterable) {
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                logger.info(document);
            }
        });
    }

    /**
     * Get a collection with a selected set of attributes
     * @param database the data base
     * @param collection the collection in the data base
     * @param attributes the interested attributes
     * @return an iterable document.
     */
    public static FindIterable<Document> getCollection(String database, String collection, String... attributes) {
        MongoDatabase db = mongoClient.getDatabase(database);

//        AggregateIterable<Document> iterable = db.getCollection("restaurants").aggregate()
//        );
        return db.getCollection(collection).find(new Document("Gender", "MALE"));



    }



    /**
     * Gets cursor for a collection in a given database.
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @return a cursor that can be iterated.
     */
    @Deprecated
    public DBCursor getCursor(String database, String collection) {
        DB db = mongoClient.getDB(database);
        return db.getCollection(collection).find();
    }

    /**
     * Prints the collection to console
     *
     * @param database   the data base
     * @param collection the collection in the data base
     */
    @Deprecated
    public static void printMongoCollection(String database, String collection) {
        DBCursor clinicalCursor = mongoConnector.getCursor(database, collection);
        if (logger.isDebugEnabled()) {
            while (clinicalCursor.hasNext()) {
                logger.debug(clinicalCursor.next());
            }
        }
    }
}
