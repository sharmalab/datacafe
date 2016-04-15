/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.mongo;

import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.core.DataSource;
import edu.emory.bmi.datacafe.core.DataSourceEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.HashMap;
import java.util.Map;

/**
 * Core initialization engine of DataCafe with Mongo datasource.
 */
public class MongoEngine extends DataSourceEngine {
    private static Logger logger = LogManager.getLogger(MongoEngine.class.getName());

    /**
     * Initializes the patient cursors
     *
     * @param database   database name
     * @param collection collection name
     * @param constraint constraint to be satisfied
     */
    public static MongoCursor<?> initializeEntry(String database, String collection, String constraint, Class clazz) {
        MongoCollection entries = JongoConnector.initCollection(database, collection);
        if (logger.isDebugEnabled()) {
            logger.debug("Successfully initialized entry.. " + constraint);
        }
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
        if (logger.isDebugEnabled()) {
            logger.debug("Successfully found entry.. " + constraint);
        }

        return patients.findOne(constraint).as(clazz);
    }

    public String addDataSource(DataSource dataSource) {
        Map<String, String> dsMap = new HashMap<>();
        dsMap.put(MongoConstants.DATABASE_KEY_ENTRY, dataSource.getDatabase());
        dsMap.put(MongoConstants.COLLECTION_KEY_ENTRY, dataSource.getCollection());
        return super.addDataSource(dsMap);
    }
}
