/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

import edu.emory.bmi.datacafe.mongo.JongoConnector;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

/**
 * Core initialization engine of DataCafe.
 */
public class DatacafeEngine {
    /**
     * Initializes the patient cursors
     *
     * @param database   database name
     * @param collection collection name
     * @param constraint constraint to be satisfied
     */
    public static MongoCursor<?> initializeEntry(String database, String collection, String constraint, Class clazz) {
        MongoCollection entries = JongoConnector.initCollection(database, collection);

         return entries.find(constraint).as(clazz);
    }

    /**
     * Finds the patient with the given id with more details.
     *
     * @param database   database name
     * @param collection collection name
     * @param constraint constraint to be satisfied
     * @param clazz,     the Class of entry to be found.
     * @return the Patient details
     */
    public static Object findEntry(String database, String collection, String constraint, Class clazz) {
        MongoCollection patients = JongoConnector.initCollection(database, collection);

        return patients.findOne(constraint).as(clazz);
    }
}
