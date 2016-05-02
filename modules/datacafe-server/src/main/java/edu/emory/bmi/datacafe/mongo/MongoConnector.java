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
import edu.emory.bmi.datacafe.core.AbstractDataSourceConnector;
import edu.emory.bmi.datacafe.core.DataCafeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Connects to the Mongo database
 */
public class MongoConnector extends AbstractDataSourceConnector {
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
    public List<Object> getAllIDs(FindIterable<Document> iterable) {
        return getAllIDs(iterable, MongoConstants.ID_ATTRIBUTE);
    }

    /**
     * Gets the list of IDs
     *
     * @param iterable    the collection iterable
     * @param idAttribute The attribute key that is used as the ID.
     * @return the list of IDs.
     */
    public List<Object> getAllIDs(FindIterable<Document> iterable, String idAttribute) {
        List<Object> idList = new ArrayList<Object>();
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

    @Override
    public List<String> getAttributesWithHeader(String database, String collection, List ids, String idAttribute,
                                                String[] preferredAttributes) {
        List<String> attributes = new ArrayList<>();
        attributes.add(getChosenAttributeNames(preferredAttributes));

        attributes.addAll(getAttributeValues(database, collection, ids, idAttribute, preferredAttributes,
                new String[]{idAttribute}).stream().map(str -> str).collect(Collectors.toList()));
        return attributes;
    }

    /**
     * Get all the values except the default MongoID attribute.
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param ids        the list of ids.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValuesExceptAutoGenMongoId(String database, String collection, List ids, String[] preferredAttributes) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes,
                new String[]{MongoConstants.ID_ATTRIBUTE}, true);
    }

    /**
     * Get all the values except a given attribute. Default MongoID, _id is assumed.
     *
     * @param database         the data base
     * @param collection       the collection in the data base
     * @param ids              the list of ids.
     * @param removedAttribute the attribute to be removed.
     * @return the list of DBCursor.
     */
    public List<String> getAllAttributeValuesExcept(String database, String collection, List ids,
                                                    String removedAttribute) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, null,
                new String[]{removedAttribute});
    }

    /**
     * Get all the values except the default MongoID attribute.
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param ids        the list of ids.
     * @return the list of DBCursor.
     */
    public List<String> getAllAttributeValuesExceptAutoGenMongoId(String database, String collection, List ids) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, null,
                new String[]{MongoConstants.ID_ATTRIBUTE}, true);
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
    public List<Object> getAllIDs(String database, String collection) {
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
    public List<Object> getIDs(String database, String collection, Document document) {
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
        return getAttributeValues(database, collection, ids, idAttribute,
                preferredAttributes, removedAttributes, false);
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
     * @param addHeader           should the headers be added.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes,
                                           String[] removedAttributes, boolean addHeader) {
        DBCollection collection1 = getCollection(database, collection);
        List<String> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(idAttribute, id),
                    getDBObjFromAttributes(preferredAttributes, removedAttributes));

            String cursorValue;
            if (addHeader) {
                cursorValue = getCursorValues(results, true);
                addHeader = false;
            } else {
                cursorValue = getCursorValues(results);
            }
            dbCursors.add(cursorValue.trim());
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
     * @param removedAttributes   the attributes to be removed.
     * @return the list of DBCursor.
     */
    public List<DBCursor> getAttributes(String database, String collection, List ids, String idAttribute,
                                        String[] preferredAttributes, String[] removedAttributes) {
        DBCollection collection1 = getCollection(database, collection);
        List<DBCursor> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(idAttribute, id),
                    getDBObjFromAttributes(preferredAttributes, removedAttributes));
            dbCursors.add(results);
            printCursor(results);
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
        return getAttributes(database, collection, ids, idAttribute, preferredAttributes, null);
    }


    /**
     * Gets all the attributes except a certain attribute
     *
     * @param database         the data base
     * @param collection       the collection in the data base
     * @param ids              the list of ids.
     * @param idAttribute      the id Attribute
     * @param removedAttribute the removed attribute
     * @return all the attribute values.
     */
    public List<DBCursor> getAllAttributesExcept(String database, String collection, List ids, String idAttribute,
                                                 String removedAttribute) {

        return getAttributes(database, collection, ids, idAttribute, null, new String[]{removedAttribute});
    }


    /**
     * Gets all the attributes except a certain attribute
     *
     * @param database    the data base
     * @param collection  the collection in the data base
     * @param ids         the list of ids.
     * @param idAttribute the id Attribute
     * @return all the attribute values.
     */
    public List<DBCursor> getAllAttributeValuesExceptMongoAutoGeneratedId(String database, String collection, List ids,
                                                                          String idAttribute) {

        return getAllAttributesExcept(database, collection, ids, idAttribute, MongoConstants.ID_ATTRIBUTE);
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
     * @param results   the DBCursor
     * @param addHeader Should a header with attributes be added.
     */
    public String getCursorValues(DBCursor results, boolean addHeader) {
        String outValue = "";

        while (results.hasNext()) {

            DBObject resultElement = results.next();
            Map resultElementMap = resultElement.toMap();
            Collection resultValues = resultElementMap.values();

            if (addHeader) {
                if (outValue.trim().equals("")) {
                    Collection resultNames = resultElementMap.keySet();
                    outValue += DataCafeUtil.constructStringFromCollection(resultNames);
                    outValue += "\n";
                }
            }

            String temp = DataCafeUtil.constructStringFromCollection(resultValues);

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
    public String getCursorValues(DBCursor results) {
        return getCursorValues(results, false);
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
