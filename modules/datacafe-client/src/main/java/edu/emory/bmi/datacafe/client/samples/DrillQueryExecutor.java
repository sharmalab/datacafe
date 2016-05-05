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
import edu.emory.bmi.datacafe.client.drill.DrillConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
* A sample drill query executor.
*/
public class DrillQueryExecutor {
    private static Logger logger = LogManager.getLogger(DrillQueryExecutor.class.getName());

    public static final String DRILL_SAMPLE_QUERY = "SELECT t1.SUBJECT_ID, t1.DOB " +
            "FROM hdfs.root.`physionet_patients.csv` t1";

    public static void main(String[] args) {
        ClientExecutorEngine.init();
        DrillConnector.executeQuery(DRILL_SAMPLE_QUERY, 2);
    }
}