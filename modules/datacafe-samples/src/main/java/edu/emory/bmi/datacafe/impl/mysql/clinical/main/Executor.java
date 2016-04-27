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
package edu.emory.bmi.datacafe.impl.mysql.clinical.main;

import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import edu.emory.bmi.datacafe.hdfs.HdfsConnector;
import edu.emory.bmi.datacafe.mysql.MySQLConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A sample executor for MySQL, where the _id is considered as part of the original data.
 */
public class Executor {
    private static Logger logger = LogManager.getLogger(MySQLConnector.class.getName());

    public static void main(String[] args) {

        CoreExecutorEngine.init();

        String database1 = "clinical";
        String table1 = "clinical";

        String database2 = "pathology";
        String table2 = "pathology";

        DataSourcesRegistry.addDataSource(database1, table1);
        DataSourcesRegistry.addDataSource(database2, table2);

        MySQLConnector sqlConnector = new MySQLConnector();

        // Get the list of IDs from the first data source
        List ids1 = sqlConnector.getAllIDs(database1, table1, "_id", "WHERE Age_at_Initial_Diagnosis > 60 AND Laterality = 'Left'");
        List ids2 = sqlConnector.getAllIDs(database2, table2, "_id", "WHERE Tumor_Nuclei_Percentage > 65");

        if (logger.isDebugEnabled()) {
            logger.debug("list of IDs retrieved from the MySQL data server");
        }

        // Interested Attributes: "patientID", "gender", "laterality"
        List chosenAttributes1 = new ArrayList<>(); // = MongoConnector.getAttributeValues(database1, collection1, ids1, new String[]{"Gender", "Laterality"});

        // Interested Attributes: "sliceID", "patientID", "slideBarCode"
        List chosenAttributes2 = new ArrayList<>(); // = MongoConnector.getAttributeValues(database2, collection2, ids2, new String[]{"BCR_Patient_UID_From_Pathology", "Slide_Barcode"});

        HdfsConnector.composeDataLake(chosenAttributes1, chosenAttributes2);
    }


}

