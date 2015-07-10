/*
* Title:        S²DN
* Description:  Orchestration Middleware for Incremental
*               Development of Software-Defined Cloud Networks.
* Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
*
* Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
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
    public static void copyToHDFS() throws IOException {
        Configuration config = new Configuration();
        config.addResource(new Path(HDFSConstants.HADOOP_CONF + File.separator + "core-site.xml"));
        config.addResource(new Path(HDFSConstants.HADOOP_CONF + File.separator + "hdfs-site.xml"));

        FileSystem fs = FileSystem.get(config);

//        LOAD DATA LOCAL INPATH '/your/local/filesystem/file.csv' INTO TABLE `sandbox.test` PARTITION (day='20130221')

        fs.copyFromLocalFile(new Path(DatacafeConstants.CONF_FOLDER + File.separator +
                        DatacafeConstants.DATAWAREHOUSE_CSV), new Path(HDFSConstants.HDFS_PATH));
    }

    /**
     * Writes the file to HDFS
     */
    public static void writeToHDFS() {
        try {
            HadoopConnector.copyToHDFS();
            logger.info("Successfully written to the Hadoop HDFS");
        } catch (IOException e) {
            logger.error("Failed to write to Hadoop HDFS", e);
        }
    }
}
