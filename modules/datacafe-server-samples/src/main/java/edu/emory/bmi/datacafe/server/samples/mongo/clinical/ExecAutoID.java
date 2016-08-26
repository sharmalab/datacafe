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
package edu.emory.bmi.datacafe.server.samples.mongo.clinical;

import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.core.ServerExecutorEngine;
import edu.emory.bmi.datacafe.core.kernel.DataSourcesRegistry;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.List;

/**
 * The _id is Mongo-generated Random string. Ignore it.
 */
public class ExecAutoID {
    private static Logger logger = LogManager.getLogger(ExecAutoID.class.getName());

    public static void main(String[] args) {
        ServerExecutorEngine.init();

        String database1 = "patients";
        String collection1 = "patientsData";

        String database2 = "admissions";
        String collection2 = "admissionsData";

        DataSourcesRegistry.addDataSource(database1, collection1);
        DataSourcesRegistry.addDataSource(database2, collection2);

        MongoConnector mongoConnector = new MongoConnector();

        // Get the list of IDs from the first data source
        Document document1 = new Document("GENDER", "M");
        List ids1 = mongoConnector.getIDs(database1, collection1, document1);

        if(logger.isDebugEnabled()) {
            logger.debug("First list of IDs retrieved with ids, " + ids1.size());
        }

        // Get the list of IDs from the second data source
        Document document2 = new Document("ADMISSION_TYPE", "EMERGENCY");
        List ids2 = mongoConnector.getIDs(database2, collection2, document2);

        if (logger.isDebugEnabled()) {
            logger.debug("Second list of IDs retrieved with ids, " + ids2.size());
        }

        // ID: "SUBJECT_ID". Other Interested Attributes: "ROW_ID", "GENDER"
        List chosenAttributes1 = mongoConnector.getAttributesWithHeader(database1, collection1, ids1,
                MongoConstants.ID_ATTRIBUTE,
                new String[]{"SUBJECT_ID", "ROW_ID", "GENDER"});

        // ID: "HADM_ID". Other Interested Attributes: "SUBJECT_ID", "DISCHARGE_LOCATION", "MARITAL_STATUS"
        List chosenAttributes2 = mongoConnector.getAttributesWithHeader(database2, collection2, ids2,
                MongoConstants.ID_ATTRIBUTE,
                new String[]{"HADM_ID", "SUBJECT_ID", "DISCHARGE_LOCATION", "MARITAL_STATUS"});

        // Write to the Data Lake
        HdfsConnector.composeDataLake(chosenAttributes1, chosenAttributes2);
        mongoConnector.closeConnections();
    }
}
