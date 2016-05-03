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
package edu.emory.bmi.datacafe.impl.mongo.parallel.main;

import edu.emory.bmi.datacafe.client.CoreExecutorEngine;
import edu.emory.bmi.datacafe.client.DataSourcesRegistry;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.impl.mongo.clinical.main.ExecutorRandomID;import edu.emory.bmi.datacafe.mongo.MongoConnector;
import edu.emory.bmi.datacafe.mongo.MongoIntegratedConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.lang.String;import java.util.List;

/**
 * A larger scale example with PhysioNet executed sequentially as a benchmark to the default parallel execution.
 */
public class PhysioNetSeqBenchmark {
    private static Logger logger = LogManager.getLogger(ExecutorRandomID.class.getName());

    public static void main(String[] args) {
        CoreExecutorEngine.init();

        String[] databases = {"physionet", "physionet", "physionet", "physionet", "physionet", "physionet"};
        String[] collections = {"caregivers", "dicddiagnosis", "dlabitems", "datetimeevents", "patients",
                "diagnosesicd"};

        DataSourcesRegistry.addDataSources(databases, collections);

        // Get the list of IDs from the first data source
        Document[] documents = new Document[collections.length];
        MongoConnector mongoConnector = new MongoConnector();

        documents[0] = new Document("DESCRIPTION", "Social Worker");
        documents[1] = new Document("ICD9_CODE", new Document("$gt", 70));
        documents[2] = new Document("CATEGORY", "CHEMISTRY");
        documents[3] = new Document("STOPPED", "NotStopd");
        documents[4] = new Document("GENDER", "M");
        documents[5] = new Document();

        long startT0 = System.currentTimeMillis();
        List[] idsArray = MongoIntegratedConnector.getListsOfIds(databases, collections, documents);
        long endT0 = System.currentTimeMillis();
        logger.info("Retrieved all the ids in, " + (endT0 - startT0)/1000.0 + " s.");

        long startT1 = System.currentTimeMillis();
        List[] chosenAttributes = MongoIntegratedConnector.getAllChosenAttributes(databases, collections,
                idsArray);
        long endT1 = System.currentTimeMillis();
        logger.info("Retrieved all the chosen attributes in, " + (endT1 - startT1)/1000.0 + " s.");

        long startT2 = System.currentTimeMillis();
        // Write to the Data Lake
        HdfsConnector.composeDataLakeSequential(chosenAttributes);
        long endT2 = System.currentTimeMillis();
        logger.info("Written to the data lakes in, " + (endT2 - startT2)/1000.0 + " s.");

        mongoConnector.closeConnections();
    }
}
