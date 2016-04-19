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

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import edu.emory.bmi.datacafe.core.CoreDataObject;
import edu.emory.bmi.datacafe.interfaces.WarehouseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Connecting to HDFS through Datacafe.
 */
public class HdfsConnector implements WarehouseConnector {
    private static Logger logger = LogManager.getLogger(HdfsConnector.class.getName());

    /**
     * Writes the data sources to Hive
     *
     * @param datasourceNames names of the data sources
     * @param params          parameters of the data sources as a 2-d array - an array for each of the data source
     * @param queries         queries for each of the data source.
     * @param writables       array of lists for each data sources to be written to the data warehouse.
     */
    public static void writeDataSourcesToWarehouse(String[] datasourceNames, String[][] params,
                                                   String[] queries, List<?>[] writables) {
        List<String>[] texts = new ArrayList[writables.length];

        for (int i = 0; i < writables.length; i++) {
            texts[i] = CoreDataObject.getWritableString(params[i], writables[i]);
        }

        WarehouseConnector warehouseConnector = new HdfsConnector();
        warehouseConnector.writeToWarehouse(datasourceNames, texts, queries);
    }


    @Override
    public void writeToWarehouse(String[] datasourcesNames, List<String>[] texts, String[] queries) {
        for (int i = 0; i < datasourcesNames.length; i++) {
            createFile(datasourcesNames[i], texts[i]);
            HadoopConnector.writeToHDFS(datasourcesNames[i]);
        }
    }

    @Override
    public void createFile(String fileName, List<String> lines) {

        Charset utf8 = StandardCharsets.UTF_8;
        try {
            if (ConfigReader.getFileWriteMode().equalsIgnoreCase(DatacafeConstants.APPEND)) {
                Files.write(Paths.get(fileName), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            } else if (ConfigReader.getFileWriteMode().equalsIgnoreCase(DatacafeConstants.REPLACE)) {
                Files.write(Paths.get(fileName), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            } else if (ConfigReader.getFileWriteMode().equalsIgnoreCase(DatacafeConstants.CREATE)) {
                Files.write(Paths.get(fileName), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.CREATE_NEW);
            } else {
                Files.write(Paths.get(fileName), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.CREATE_NEW);
                logger.info("No valid file write mode found. Default action: create new chosen");
            }
            logger.info("Successfully written the output to the file, " + fileName);
        } catch (IOException e) {
            logger.error("Error in creating the warehouse file: " + fileName, e);
        }
        if (ConfigReader.isRemoteDSServer()) {
            FileRemoteManager.copyFile(fileName);
        }
    }
}