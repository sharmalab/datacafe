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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import edu.emory.bmi.datacafe.conf.ConfigReader;
import org.bson.Document;

/**
 * Utility methods for Mongo Collections.
 */
public class MongoCollection {
    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            ConfigReader.getDataServerHost(), ConfigReader.getDataServerPort()));
    private String database;
    private String collection;

    public MongoCollection(String database, String collection) {
        this.database = database;
        this.collection = collection;
    }

    /**
     * Get a collection with a selected set of attributes
     *
     * @param document   the interested attributes
     * @return an iterable document.
     */
    public FindIterable<Document> getCollection(Document document) {
        MongoDatabase db = mongoClient.getDatabase(database);

        return db.getCollection(collection).find(document);
    }

    /**
     * Iterates a collection in a given database.
     *
     * @return an iterable document.
     */
    public FindIterable<Document> iterateCollection() {
        MongoDatabase db = mongoClient.getDatabase(database);
        return db.getCollection(collection).find();
    }


    /**
     * Gets the Mongo Collection
     *
     * @return the DBCollection object.
     */
    public DBCollection getCollection() {
        DB db = mongoClient.getDB(database);
        return db.getCollection(collection);
    }

    public static void close() {
        mongoClient.close();
    }
}
