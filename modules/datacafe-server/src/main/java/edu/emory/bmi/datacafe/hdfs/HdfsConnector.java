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
package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.core.DataSourcesRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Connecting to HDFS through Datacafe.
 */
public class HdfsConnector {
    private static Logger logger = LogManager.getLogger(HdfsConnector.class.getName());

    /**
     * Composes the data lake
     * @param chosenAttributes list of chosen attributes
     */
    public static void composeDataLake(List... chosenAttributes) {
        String[] dataSourcesNames = DataSourcesRegistry.getFullNamesAsArray();

        // Write to the Data Lake
        HdfsConnector.writeToWarehouse(dataSourcesNames, chosenAttributes);
    }

    /**
     * Writes the data sources to HDFS
     *
     * @param datasourcesNames names of the data sources
     * @param chosenAttributes       array of lists for each data sources to be written to the data warehouse.
     */
    private static void writeToWarehouse(String[] datasourcesNames, List<String>[] chosenAttributes) {
        HdfsWriterThread[] hdfsWriterThread = new HdfsWriterThread[datasourcesNames.length];
        try {
            for (int i = 0; i < datasourcesNames.length; i++) {
                hdfsWriterThread[i] = new HdfsWriterThread(datasourcesNames[i], chosenAttributes[i]);
                hdfsWriterThread[i].start();
            }
        } catch (IOException e) {
            logger.error("Error while attempting to get the file system", e);
        }
    }
}