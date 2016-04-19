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
    private static void copyToHDFS(String fileName) throws IOException {
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigReader.getHadoopConf()+ File.separator + HDFSConstants.CORE_SITE_XML));
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.HDFS_SITE_XML));

        FileSystem fs = FileSystem.get(config);
        String outputFileName = fileName +   ConfigReader.getFileExtension();

        fs.copyFromLocalFile(new Path(HDFSConstants.CLIENT_ORIGIN_DIR + fileName),
                new Path(ConfigReader.getHdfsPath() + outputFileName));
        logger.info("Successfully written " + outputFileName + " to the Hadoop HDFS");
    }

    /**
     * Writes the file to HDFS
     */
    public static void writeToHDFS(String fileName) {
        try {
            HadoopConnector.copyToHDFS(fileName);
        } catch (IOException e) {
            logger.error("Failed to write to Hadoop HDFS", e);
        }
    }
}