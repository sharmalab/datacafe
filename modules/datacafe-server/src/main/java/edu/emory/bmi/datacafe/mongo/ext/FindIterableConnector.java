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
package edu.emory.bmi.datacafe.mongo.ext;

import com.mongodb.client.FindIterable;
import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.mongo.MongoCollection;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
* Methods for Mongo FindIterable.
*/
@Deprecated
public class FindIterableConnector extends MongoConnector {
    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     */
    public List<Object> getAllIDs(String database, String collection) {
        MongoCollection mongoCollection = new MongoCollection(database, collection);

        return getAllIDs(mongoCollection.iterateCollection());
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

            MongoCollection mongoCollection = new MongoCollection(database, collection);

            FindIterable<Document> iterable = mongoCollection.getCollection(tempDocument1);
            iterableList.add(iterable);
        }
        return iterableList;
    }
}
