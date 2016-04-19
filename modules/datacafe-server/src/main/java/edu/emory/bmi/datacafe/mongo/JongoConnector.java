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
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import edu.emory.bmi.datacafe.constants.MongoConstants;
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
        DB db = new MongoClient(new ServerAddress(
                MongoConstants.CLIENT_HOST, MongoConstants.CLIENT_PORT)).getDB(database);
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
