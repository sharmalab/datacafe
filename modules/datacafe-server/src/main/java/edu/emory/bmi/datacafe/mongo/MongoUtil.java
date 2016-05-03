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
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * Utility methods for Mongo Integration
 */
public final class MongoUtil {
    private static Logger logger = LogManager.getLogger(MongoUtil.class.getName());

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
}
