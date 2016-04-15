/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.hdfs;

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
        config.addResource(new Path(HDFSConstants.HADOOP_CONF + File.separator + "core-site.xml"));
        config.addResource(new Path(HDFSConstants.HADOOP_CONF + File.separator + "hdfs-site.xml"));

        FileSystem fs = FileSystem.get(config);

        fs.copyFromLocalFile(new Path(HDFSConstants.CLIENT_ORIGIN_DIR + fileName),
                new Path(HDFSConstants.HDFS_PATH + fileName));
    }

    /**
     * Writes the file to HDFS
     */
    public static void writeToHDFS(String fileName) {
        try {
            HadoopConnector.copyToHDFS(fileName);
            logger.info("Successfully written to the Hadoop HDFS");
        } catch (IOException e) {
            logger.error("Failed to write to Hadoop HDFS", e);
        }
    }
}