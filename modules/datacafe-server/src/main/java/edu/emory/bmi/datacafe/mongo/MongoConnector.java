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

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.MongoConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Connects to the Mongo database
 */
public class MongoConnector {
    private static Logger logger = LogManager.getLogger(MongoConnector.class.getName());

    private static final MongoClient mongoClient = new MongoClient(new ServerAddress(
            ConfigReader.getDataServerHost(), ConfigReader.getDataServerPort()));

    /**
     * Iterates a collection in a given database.
     *
     * @param database   the database
     * @param collection the collection in the data base
     * @return an iterable document.
     */
    public static FindIterable<Document> iterateCollection(String database, String collection) {
        MongoDatabase db = mongoClient.getDatabase(database);
        return db.getCollection(collection).find();
    }


    /**
     * Gets the Mongo Collection
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @return the DBCollection object.
     */
    public static DBCollection getCollection(String database, String collection) {
        DB db = mongoClient.getDB(database);
        return db.getCollection(collection);
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
     * Gets the list of IDs
     *
     * @param iterable the collection iterable
     */
    public static List getID(FindIterable<Document> iterable) {
        List idList = new ArrayList();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                idList.add(document.get(MongoConstants.ID_ATTRIBUTE));
            }
        });
        if (logger.isDebugEnabled()) {
            for (Object anIdList : idList) {
                logger.debug(anIdList);
            }
        }
        return idList;
    }

    /**
     * Get a collection with a selected set of attributes
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param document   the interested attributes
     * @return an iterable document.
     */
    public static FindIterable<Document> getCollection(String database, String collection, Document document) {
        MongoDatabase db = mongoClient.getDatabase(database);

        return db.getCollection(collection).find(document);
    }

    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     */
    public static List getID(String database, String collection) {
        return getID(iterateCollection(database, collection));
    }

    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param document   the Ids
     */
    public static List getID(String database, String collection, Document document) {
        return getID(getCollection(database, collection, document));
    }


    /**
     * Gets all the attributes without removing or choosing a sub set of attributes
     *
     * @param database   the data base
     * @param collection the collection in the database
     * @param ids        the list of ids.
     * @return the iterable document
     */
    public static List<FindIterable<Document>> getAllAttributes(String database, String collection, List ids) {

        List<FindIterable<Document>> iterableList = new ArrayList<>();
        for (Object id : ids) {
            Document tempDocument1 = new Document(MongoConstants.ID_ATTRIBUTE, id);

            FindIterable<Document> iterable = getCollection(database, collection, tempDocument1);
            iterableList.add(iterable);
        }
        return iterableList;
    }

    /**
     * Get only the values for a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public static List<String> getAttributeValues(String database, String collection, List ids,
                                                  String[] preferredAttributes) {
        DBCollection collection1 = getCollection(database, collection);
        List<String> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(MongoConstants.ID_ATTRIBUTE, id),
                    getDBObjFromAttributes(preferredAttributes));

            String cursorValue = getCursorValues(results);
            dbCursors.add(cursorValue);
        }
        return dbCursors;
    }


    /**
     * Get a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public static List<DBCursor> getAttributes(String database, String collection, List ids,
                                               String[] preferredAttributes) {
        DBCollection collection1 = getCollection(database, collection);
        List<DBCursor> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(MongoConstants.ID_ATTRIBUTE, id),
                    getDBObjFromAttributes(preferredAttributes));
            dbCursors.add(results);
            printCursor(results);
        }
        return dbCursors;
    }


    /**
     * Gets a BasicDBObject with a few attributes preferred.
     *
     * @param preferredAttributes the attributes to be added.
     * @return the BasicDBObject
     */
    public static BasicDBObject getDBObjFromAttributes(String[] preferredAttributes) {
        return getDBObjFromAttributes(preferredAttributes, null);
    }

    /**
     * Gets a BasicDBObject with a few attributes removed.
     *
     * @param removedAttributes the attributes to be removed.
     * @return the BasicDBObject.
     */
    public static BasicDBObject getDBObjWithRemovedAttributes(String[] removedAttributes) {
        return getDBObjFromAttributes(null, removedAttributes);
    }

    /**
     * Gets a BasicDBObject with a few attributes added or removed.
     *
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @return the BasicDBObject.
     */
    public static BasicDBObject getDBObjFromAttributes(String[] preferredAttributes, String[] removedAttributes) {
        BasicDBObject basicDBObject = new BasicDBObject();
        if (preferredAttributes != null) {
            for (String preferredAttribute : preferredAttributes) {
                basicDBObject.append(preferredAttribute, 1);
            }
        }
        if (removedAttributes != null) {
            for (String removedAttribute : removedAttributes) {
                basicDBObject.append(removedAttribute, 0);
            }
        }
        return basicDBObject;
    }

    /**
     * Prints the cursor
     *
     * @param results the DBCursor
     */
    public static String getCursorValues(DBCursor results) {
        String outValue = "";

        while (results.hasNext()) {

            DBObject resultElement = results.next();
            Map resultElementMap = resultElement.toMap();
            Collection resultValues = resultElementMap.values();


            Object[] tempArray = resultValues.toArray();
            String temp = "";
            for (int i = 0; i < tempArray.length; i++) {
                if (i!=0) {
                    temp += ",";
                }
                temp += (String) tempArray[i];
            }

            outValue += temp;
            if (logger.isDebugEnabled()) {
                logger.debug(outValue);
            }
        }
        return outValue;
    }

    /**
     * Prints the cursor
     *
     * @param results the DBCursor
     */
    public static void printCursor(DBCursor results) {
        while (results.hasNext()) {
            logger.info(results.next());
        }
    }
}
