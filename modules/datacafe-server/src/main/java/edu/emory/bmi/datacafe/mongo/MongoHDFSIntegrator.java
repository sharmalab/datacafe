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

import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * A simple integrator class to write from Mongo to HDFS in a parallel manner for each of the Mongo collection.
 */
public class MongoHDFSIntegrator {
    private static Logger logger = LogManager.getLogger(MongoHDFSIntegrator.class.getName());

    /**
     * Write to HDFS. Parallel Execution.
     *
     * @param databases   the databases as an array
     * @param collections the collections in the respective databases.
     * @param documents   the Document objects as an array
     */
    public static void writeToHDFSInParallel(String[] databases, String[] collections, Document[] documents) {
        MongoHDFSIntegratorThread[] mongoHDFSIntegratorThreads = new MongoHDFSIntegratorThread[collections.length];
        String[] dataSourcesNames = DataSourcesRegistry.getFullNamesAsArray();

        for (int i = 0; i < collections.length; i++) {
            mongoHDFSIntegratorThreads[i] = new MongoHDFSIntegratorThread(databases[i], collections[i],
                    documents[i], dataSourcesNames[i]);
            mongoHDFSIntegratorThreads[i].start();
        }
    }
}
