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
 * A sample executor for MySQL.
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
        List ids1 = sqlConnector.getIDs(database1, table1, "_id", "WHERE Age_at_Initial_Diagnosis > 60 AND Laterality = 'Left'");
        List ids2 = sqlConnector.getIDs(database2, table2, "_id", "WHERE Tumor_Nuclei_Percentage > 65");

        if (logger.isDebugEnabled()) {
            logger.debug("list of IDs retrieved from the MySQL data server");
        }

        // Interested Attributes: "patientID", "gender", "laterality"
        List chosenAttributes1 = sqlConnector.getAttributeValues(database1, table1, ids1, "_id",
                new String[] {"_id", "Age_at_Initial_Diagnosis", "Gender", "Laterality"});

        // Interested Attributes: "sliceID", "patientID", "slideBarCode"
        List chosenAttributes2 = sqlConnector.getAttributeValues(database2, table2, ids2, "_id",
                new String[] {"_id", "BCR_Patient_UID_From_Pathology", "Slide_Barcode"});

        HdfsConnector.composeDataLake(chosenAttributes1, chosenAttributes2);
        sqlConnector.closeConnections();
    }
}

