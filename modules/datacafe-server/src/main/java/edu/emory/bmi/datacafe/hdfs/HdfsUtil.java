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
import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
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
import java.util.List;

/**
 * Utilities for HDFS.
 */
public final class HdfsUtil {
    private static Logger logger = LogManager.getLogger(HdfsUtil.class.getName());
    private static FileSystem hdfs;

    public static void init() {
        try {
            hdfs = HdfsUtil.getFileSystem();
        } catch (IOException e) {
            logger.error("Exception while initializing HDFS file system");
        }
    }

    /**
     * Get the HDFS file system.
     * @return the hdfs file system
     * @throws java.io.IOException in getting the hdfs file system
     */
    public static FileSystem getFileSystem() throws IOException {
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.CORE_SITE_XML));
        config.addResource(new Path(ConfigReader.getHadoopConf() + File.separator + HDFSConstants.HDFS_SITE_XML));

        return FileSystem.get(config);
    }

    public static void write(List<String> chosenAttributes, String outputFile) {
        init();
        String temp = "";

        OutputStream os = null;
        try {
            os = hdfs.create(new Path(outputFile));
        } catch (IOException e) {
            logger.error("IOException in writing to hdfs.", e);
        }

        assert os != null;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        for (String chosenAttribute : chosenAttributes) {
            temp += chosenAttribute + "\n";
        }

        try {
            writer.write(temp);
        } catch (IOException e) {
            logger.error("IOException in writing to hdfs.", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("IOException in closing the writer", e);
            }
        }

        long endTime = System.currentTimeMillis();

        long timeConsumed = endTime - CoreExecutorEngine.getStartTime();
        logger.info("Successfully written to the data lake: " + outputFile + " in " + timeConsumed/1000.0 + " s.");
    }
}
