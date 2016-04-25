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
package edu.emory.bmi.datacafe.impl.clinical1.main;

import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.bson.Document;

import java.util.List;

/**
 * The _id is Mongo-generated Random string. Ignore it.
 */
public class ExecutorRandomID {
    public static void main(String[] args) {
        CoreExecutorEngine.init();

        String database1 = "patients";
        String collection1 = "patientsData";

        String database2 = "admissions";
        String collection2 = "admissionsData";

        DataSourcesRegistry.addDataSource(database1, collection1);
        DataSourcesRegistry.addDataSource(database2, collection2);

        // Get the list of IDs from the first data source
        Document document1 = new Document("GENDER", "M");
        List ids1 = MongoConnector.getID(database1, collection1, document1);

        // Get the list of IDs from the second data source
        Document document2 = new Document("ADMISSION_TYPE", "EMERGENCY");
        List ids2 = MongoConnector.getID(database2, collection2, document2);

        // ID: "SUBJECT_ID". Other Interested Attributes: "ROW_ID", "GENDER"
        List chosenAttributes1 = MongoConnector.getAttributeValues(database1, collection1, ids1, "SUBJECT_ID",
                new String[]{"ROW_ID", "GENDER"});

        // ID: "HADM_ID". Other Interested Attributes: "SUBJECT_ID", "DISCHARGE_LOCATION", "MARITAL_STATUS"
        List chosenAttributes2 = MongoConnector.getAttributeValues(database2, collection2, ids2, "HADM_ID",
                new String[]{"SUBJECT_ID", "DISCHARGE_LOCATION", "MARITAL_STATUS"});

        List<String>[] attributes = new List[]{chosenAttributes1, chosenAttributes2};
        String[] dataSourcesNames = DataSourcesRegistry.getFullNamesAsArray();

        // Write to the Data Lake
        HdfsConnector.writeToWarehouse(dataSourcesNames, attributes);

    }
}
