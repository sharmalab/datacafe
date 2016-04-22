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
import edu.emory.bmi.datacafe.constants.HDFSConstants;
import edu.emory.bmi.datacafe.core.CoreDataObject;
import edu.emory.bmi.datacafe.interfaces.WarehouseConnector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
        try {
            FileSystem hdfs = getFileSystem();
            OutputStream os[] = new OutputStream[datasourcesNames.length];
            BufferedWriter writer[] = new BufferedWriter[datasourcesNames.length];


            for (int i = 0; i < datasourcesNames.length; i++) {
                String outputFile = ConfigReader.getHdfsPath() + datasourcesNames[i] +
                        ConfigReader.getFileExtension();
                os[i] = hdfs.create(new Path(outputFile));
                writer[i] = new BufferedWriter(new OutputStreamWriter(os[i]));
                String temp = "";
                for (int j = 0; j < texts[i].size(); j++) {
                    temp += texts[i].get(j) + "\n";
                }
//                logger.info(temp);
                writer[i].write(temp);
//                writer[i].write("TEST\n");
                logger.info("Successfully written:" + temp + " ... to the data source: " + outputFile);


//            createFile(datasourcesNames[i], texts[i]);
//            HadoopConnector.writeToHDFS(datasourcesNames[i]);
            }
        } catch (IOException e) {
            logger.error("Error while attempting to get the file system", e);
        }
    }

    private static FileSystem getFileSystem() throws IOException {
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.CORE_SITE_XML));
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.HDFS_SITE_XML));

        return FileSystem.get(config);
    }

    /**
     * Just to delete the entire content of an hdfs folder.
     * @param folder the folder to be deleted
     * @throws IOException if the deletion failed.
     */
    private static void delete(String folder) throws IOException {
        FileSystem fs = getFileSystem();
        fs.delete(new Path(ConfigReader.getHdfsPath() + folder), true);
        logger.info("Successfully deleted the contents of the HDFS folder: " + folder);
    }

    public static void main(String[] args) {
        ConfigReader.readConfig();
        try {
            delete(ConfigReader.getInputBulkDir());
        } catch (IOException e) {
            logger.error("Exception in deleting the contents of the directory: " + ConfigReader.getInputBulkDir(), e);
        }
    }
}