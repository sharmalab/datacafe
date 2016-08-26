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
package edu.emory.bmi.datacafe.rest;

import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.client.core.HzClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;

import static spark.Spark.*;

/**
 * Offers a public API to the data lake creation and access.
 */
public class DataLakeManager {

    private static Logger logger = LogManager.getLogger(DataLakeManager.class.getName());

    public static void initialize() {
        ClientExecutorEngine.init();



        /**
         Retrieve the list of datalakes.
         /GET
         http://localhost:9090/datalakes/

         Response:
         [-7466653342708752832, -7059417815353339196, -6908825180316283930, -6365519002970140943]

         or

         No datalakes found
         */
        get("/datalakes/", (request, response) -> {

            logger.info("The data lakes are retrieved");

            Collection<String> datalakes = HzClient.getDataLakeNames();

            String out = Arrays.toString(datalakes.toArray());

            if (datalakes != null) {
                return out;
            } else {
                response.status(404); // 404 Not found
                return "No datalakes found";
            }
        });

        /**
         * R Replica Set:
         /POST
         http://localhost:9090/datalakes?datalakeID=PhysioNetExecutor&attributes=SUBJECT_ID,DOB,HADM_ID,ICD9_CODE,SHORT_TITLE,DESCRIPTION

         TCGA-GBM&iPatientID=TCGA-06-6701%2CTCGA-08-0831&iStudyInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895&iSeriesInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679

         Response:
         (replicaSetID).
         -4764762120292626164
         */
        post("/datalake", (request, response) -> {
            String datalakeID = request.queryParams("datalakeID");
            String[] attributes = (request.queryParams("attributes") != null) ? request.queryParams("attributes").split(",") : new String[0];

//            String[] patientId = (request.queryParams("iPatientID") != null) ? request.queryParams("iPatientID").split(",") : new String[0];
//            String[] studyInstanceUID = (request.queryParams("iStudyInstanceUID") != null) ? request.queryParams("iStudyInstanceUID").split(",") : new String[0];
//            String[] seriesInstanceUID = (request.queryParams("iSeriesInstanceUID") != null) ? request.queryParams("iSeriesInstanceUID").split(",") : new String[0];
//            long replicaSetID = tciaReplicaSetHandler.createNewReplicaSet(userId, collection, patientId, studyInstanceUID, seriesInstanceUID);
//            RsIntegratorCore.updateExistenceInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION, true);
//            response.status(201); // 201 Created
            return datalakeID;
        });


    }
}
