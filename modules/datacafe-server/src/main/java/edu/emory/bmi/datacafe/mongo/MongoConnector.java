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
import edu.emory.bmi.datacafe.core.SourceConnector;
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
public class MongoConnector implements SourceConnector {
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
    public FindIterable<Document> iterateCollection(String database, String collection) {
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
    public DBCollection getCollection(String database, String collection) {
        DB db = mongoClient.getDB(database);
        return db.getCollection(collection);
    }

    /**
     * Prints an iterable collection
     *
     * @param iterable the collection iterable
     */
    public void printMongoCollection(FindIterable<Document> iterable) {
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                logger.info(document);
            }
        });
    }


    /**
     * Gets the list of IDs. Default id, _id is used.
     *
     * @param iterable the collection iterable
     */
    public List getAllIDs(FindIterable<Document> iterable) {
        return getAllIDs(iterable, MongoConstants.ID_ATTRIBUTE);
    }

    /**
     * Gets the list of IDs
     *
     * @param iterable    the collection iterable
     * @param idAttribute The attribute key that is used as the ID.
     * @return the list of IDs.
     */
    public List getAllIDs(FindIterable<Document> iterable, String idAttribute) {
        List idList = new ArrayList();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                idList.add(document.get(idAttribute));
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
     * Gets all the attributes without removing or choosing a sub set of attributes. Default MongoID, _id is assumed.
     *
     * @param database   the data base
     * @param collection the collection in the database
     * @param ids        the list of ids.
     * @return the iterable document
     */
    public List<FindIterable<Document>> getAllAttributes(String database, String collection, List ids) {

        return getAllAttributes(database, collection, MongoConstants.ID_ATTRIBUTE, ids);
    }

    /**
     * Get only the values for a chosen sub set of attributes. Default MongoID, _id is assumed.
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids,
                                           String[] preferredAttributes) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes, null);
    }

    /**
     * Get only the values for a chosen sub set of attributes. Default MongoID, _id is assumed.
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids,
                                           String[] preferredAttributes, String[] removedAttributes) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes,
                removedAttributes);
    }

    /**
     * Get a chosen sub set of attributes.
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public List<DBCursor> getAttributes(String database, String collection, List ids,
                                        String[] preferredAttributes) {
        return getAttributes(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes);
    }

    /**
     * Get a collection with a selected set of attributes
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param document   the interested attributes
     * @return an iterable document.
     */
    public FindIterable<Document> getCollection(String database, String collection, Document document) {
        MongoDatabase db = mongoClient.getDatabase(database);

        return db.getCollection(collection).find(document);
    }

    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     */
    public List getAllIDs(String database, String collection) {
        return getAllIDs(iterateCollection(database, collection));
    }

    @Override
    public List getAllIDs(String database, String collection, String idAttribute) {
        return getAllIDs(iterateCollection(database, collection), idAttribute);
    }

    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param document   the Ids
     */
    public List getIDs(String database, String collection, Document document) {
        return getAllIDs(getCollection(database, collection, document));
    }


    /**
     * Gets all the attributes without removing or choosing a sub set of attributes
     *
     * @param database    the data base
     * @param collection  the collection in the database
     * @param idAttribute The attribute key that is used as the ID.
     * @param ids         the list of ids.
     * @return the iterable document
     */
    public List<FindIterable<Document>> getAllAttributes(String database, String collection,
                                                         String idAttribute, List ids) {

        List<FindIterable<Document>> iterableList = new ArrayList<>();
        for (Object id : ids) {
            Document tempDocument1 = new Document(idAttribute, id);

            FindIterable<Document> iterable = getCollection(database, collection, tempDocument1);
            iterableList.add(iterable);
        }
        return iterableList;
    }

    @Override
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes) {

        return getAttributeValues(database, collection, ids, idAttribute, preferredAttributes, null);
    }

    /**
     * Get only the values for a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param idAttribute         The attribute key that is used as the ID.
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes, String[] removedAttributes) {
        DBCollection collection1 = getCollection(database, collection);
        List<String> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(idAttribute, id),
                    getDBObjFromAttributes(preferredAttributes, removedAttributes));

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
     * @param idAttribute         The attribute key that is used as the ID.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public List<DBCursor> getAttributes(String database, String collection, List ids, String idAttribute,
                                        String[] preferredAttributes) {
        DBCollection collection1 = getCollection(database, collection);
        List<DBCursor> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(idAttribute, id),
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
    public BasicDBObject getDBObjFromAttributes(String[] preferredAttributes) {
        return getDBObjFromAttributes(preferredAttributes, null);
    }

    /**
     * Gets a BasicDBObject with a few attributes removed.
     *
     * @param removedAttributes the attributes to be removed.
     * @return the BasicDBObject.
     */
    public BasicDBObject getDBObjWithRemovedAttributes(String[] removedAttributes) {
        return getDBObjFromAttributes(null, removedAttributes);
    }

    /**
     * Gets a BasicDBObject with a few attributes added or removed.
     *
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @return the BasicDBObject.
     */
    public BasicDBObject getDBObjFromAttributes(String[] preferredAttributes, String[] removedAttributes) {
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
    public String getCursorValues(DBCursor results) {
        String outValue = "";

        while (results.hasNext()) {

            DBObject resultElement = results.next();
            Map resultElementMap = resultElement.toMap();
            Collection resultValues = resultElementMap.values();


            Object[] tempArray = resultValues.toArray();
            String temp = "";
            for (int i = 0; i < tempArray.length; i++) {
                if (i != 0) {
                    temp += ",";
                }
                temp += tempArray[i].toString(); //todo: How to avoid losing the types.
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
    public void printCursor(DBCursor results) {
        while (results.hasNext()) {
            logger.info(results.next());
        }
    }

    @Override
    public void closeConnections() {
        if (logger.isDebugEnabled()) {
            logger.debug("Successfully closed the Mongo connection.");
        }
        mongoClient.close();
    }
}
