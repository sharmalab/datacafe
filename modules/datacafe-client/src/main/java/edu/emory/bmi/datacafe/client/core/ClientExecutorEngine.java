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
package edu.emory.bmi.datacafe.client.core;


import edu.emory.bmi.datacafe.client.conf.ClientConfigReader;
import edu.emory.bmi.datacafe.client.drill.DrillConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The core Data Cafe executor engine singleton
 */
public final class ClientExecutorEngine {
    private static Logger logger = LogManager.getLogger(ClientExecutorEngine.class.getName());

    private static ClientExecutorEngine clientExecutorEngine;
    private static long startTime;

    /**
     * Initialize the singleton object
     */
    public static void init() {
        startTime = System.currentTimeMillis();
        if (clientExecutorEngine == null) {
            clientExecutorEngine = new ClientExecutorEngine();
        }
    }

    /**
     * Executes the initialization workflow of Data Cafe once, and only once.
     */
    private ClientExecutorEngine() {
        ClientConfigReader.readConfig();
        DrillConnector.initConnection();
    }

    /**
     * Get the execution start time.
     *
     * @return the execution start time.
     */
    public static long getStartTime() {
        return startTime;
    }
}

