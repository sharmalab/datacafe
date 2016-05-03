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

import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import edu.emory.bmi.datacafe.impl.mongo.clinical.main.ExecutorRandomID;
import edu.emory.bmi.datacafe.mongo.MongoHDFSIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * A larger scale example with PhysioNet.
 */
public class PhysioNetSampleExecutor2 {
    private static Logger logger = LogManager.getLogger(ExecutorRandomID.class.getName());

    public static void main(String[] args) {
        CoreExecutorEngine.init();

        String[] databases = {"physionet", "physionet", "physionet", "physionet", "physionet", "physionet"};
        String[] collections = {"caregivers", "dicddiagnosis", "dlabitems", "datetimeevents", "patients",
                "diagnosesicd"};
        String[][] attributes = {{},{},{},{"ITEMID","SUBJECT_ID","CGID", "ICUSTAY_ID"},{},{}};

        DataSourcesRegistry.addDataSources(databases, collections);

        // Get the list of IDs from the first data source
        Document[] documents = new Document[collections.length];

        documents[0] = new Document();
        documents[1] = new Document("ICD9_CODE", new Document("$gt", 70));
        documents[2] = new Document();
        documents[3] = new Document("STOPPED", "NotStopd");
        documents[4] = new Document("GENDER", "M");
        documents[5] = new Document();

        MongoHDFSIntegrator.writeToHDFSInParallel(databases, collections, documents, attributes);
    }
}
