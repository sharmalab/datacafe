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
package edu.emory.bmi.datacafe.client.samples;

import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.client.core.HzClient;
import edu.emory.bmi.datacafe.client.core.QueryBuilderClient;
import edu.emory.bmi.datacafe.client.drill.DrillConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;


/**
 * A sample drill query executor client with queries partially auto-generated.
 */
public class ExecutorClient {
    private static Logger logger = LogManager.getLogger(ExecutorClient.class.getName());
    private static final String datalakeID = "PhysioNetExecutor";

    private static final String attributes[] = {"SUBJECT_ID", "DOB", "HADM_ID", "ICD9_CODE", "SHORT_TITLE", "DESCRIPTION"};

    public static String derivedQueryFromHazelcast;

    public static void main(String[] args) {
        ClientExecutorEngine.init();
        QueryBuilderClient queryBuilderClient = new QueryBuilderClient(datalakeID, attributes);

        derivedQueryFromHazelcast = queryBuilderClient.buildQueryStatement("SUBJECT_ID", " < 100");

        DrillConnector.executeQuery(derivedQueryFromHazelcast, 6);
        ClientExecutorEngine.printExecutionTime();

    }
}