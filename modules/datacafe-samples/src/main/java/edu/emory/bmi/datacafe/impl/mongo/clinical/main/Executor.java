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
package edu.emory.bmi.datacafe.impl.mongo.clinical.main;

import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.bson.Document;

import java.util.List;
/**
 * A sample executor for Mongo, where the _id is considered as part of the original data.
 */
public class Executor {
    public static void main(String[] args) {

        CoreExecutorEngine.init();

        String database1 = "clinical";
        String collection1 = "clinicalData";

        String database2 = "pathology";
        String collection2 = "pathologyData";

        DataSourcesRegistry.addDataSource(database1, collection1);
        DataSourcesRegistry.addDataSource(database2, collection2);

        MongoConnector mongoConnector = new MongoConnector();

        // Get the list of IDs from the first data source
        Document document1 = new Document("Age_at_Initial_Diagnosis", new Document("$gt", 60)).append("Laterality", "Left");
        List ids1 = mongoConnector.getID(database1, collection1, document1);

        // Get the list of IDs from the second data source
        Document document2 = new Document("Tumor_Nuclei_Percentage", new Document("$gt", 65));
        List ids2 = mongoConnector.getID(database2, collection2, document2);

        // Interested Attributes: "patientID", "gender", "laterality"
        List chosenAttributes1 = mongoConnector.getAttributeValues(database1, collection1, ids1, new String[]{"Gender", "Laterality"});

        // Interested Attributes: "sliceID", "patientID", "slideBarCode"
        List chosenAttributes2 = mongoConnector.getAttributeValues(database2, collection2, ids2, new String[]{"BCR_Patient_UID_From_Pathology", "Slide_Barcode"});

        // Write to the Data Lake
        HdfsConnector.composeDataLake(chosenAttributes1, chosenAttributes2);
    }
}

