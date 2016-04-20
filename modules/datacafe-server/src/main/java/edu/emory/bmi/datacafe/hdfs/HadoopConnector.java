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
import edu.emory.bmi.datacafe.constants.HDFSConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;


/**
 * Connect to Hadoop
 */
public class HadoopConnector {
    private static Logger logger = LogManager.getLogger(HadoopConnector.class.getName());


    /**
     * Copies the local data warehouse file to HDFS.
     * @throws IOException
     */
    private static void copyToHDFS(String fileName, boolean isDirectory) throws IOException {
        String outputFileName;
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigReader.getHadoopConf()+ File.separator + HDFSConstants.CORE_SITE_XML));
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.HDFS_SITE_XML));

        FileSystem fs = FileSystem.get(config);

        if (!isDirectory) {
            outputFileName = fileName + ConfigReader.getFileExtension();
        } else {
            outputFileName = fileName;
        }

        fs.copyFromLocalFile(new Path(ConfigReader.getClientOriginDir() + fileName),
                new Path(ConfigReader.getHdfsPath() + outputFileName));
        logger.info("Successfully written " + outputFileName + " to the Hadoop HDFS");
    }

    private static FileSystem getFileSystem() throws IOException {
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigReader.getHadoopConf()+ File.separator + HDFSConstants.CORE_SITE_XML));
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.HDFS_SITE_XML));

        return FileSystem.get(config);
    }

    private static void delete(String folder) throws IOException {
        FileSystem fs = getFileSystem();
        fs.delete(new Path(ConfigReader.getHdfsPath() + folder), true);
        logger.info("Successfully deleted the contents of the HDFS folder: " + folder);
    }

    /**
     * Writes the file to HDFS
     */
    public static void writeToHDFS(String fileName) {
        try {
            HadoopConnector.copyToHDFS(fileName, false);
        } catch (IOException e) {
            logger.error("Failed to write to Hadoop HDFS", e);
        }
    }

    public static void main(String[] args) {
        ConfigReader.readConfig();
        try {
            delete(ConfigReader.getInputBulkDir());
            copyToHDFS(ConfigReader.getInputBulkDir(), true);
        } catch (IOException e) {
            logger.error("Exception in copying the directory: " + ConfigReader.getInputBulkDir(), e);
        }
    }
}