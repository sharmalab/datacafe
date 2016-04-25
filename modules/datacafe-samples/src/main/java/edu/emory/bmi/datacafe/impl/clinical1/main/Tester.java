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
import edu.emory.bmi.datacafe.mongo.MongoConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.List;

public class Tester {
    private static Logger logger = LogManager.getLogger(Tester.class.getName());

    public static void main(String[] args) {

        CoreExecutorEngine.init();
        String database1 = "clinical";
        String collection1 = "clinicalData";
        String[] interestedAttributes1 = {"patientID", "gender", "laterality"};

        String database2 = "pathology";
        String collection2 = "pathologyData";
        String[] interestedAttributes2 = {"sliceID", "patientID", "slideBarCode"};

        Document document1 = new Document("Age_at_Initial_Diagnosis", new Document("$gt", 60)).append("Laterality", "Left");
        List ids1 = MongoConnector.getID(database1, collection1, document1);

        Document document2 = new Document("Tumor_Nuclei_Percentage", new Document("$gt", 65));
        List ids2 = MongoConnector.getID(database2, collection2, document2);

        List chosenAttributes1 = MongoConnector.getAttributes(database1, collection1, ids1, new String[]{"Gender", "Laterality"});
        List chosenAttributes2 = MongoConnector.getAttributes(database2, collection2, ids2, new String[]{"BCR_Patient_UID_From_Pathology", "Slide_Barcode"});
    }
}

