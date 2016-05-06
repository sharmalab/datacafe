package edu.emory.bmi.datacafe.server.test;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.hdfs.HdfsCleaner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

/**
 * Simple tests for the Data Cafe Server.
 */
public class TestDataCafeServer {
    private static Logger logger = LogManager.getLogger(TestDataCafeServer.class.getName());


    /**
     * Test the clean Hadoop directory method.
     */
    @Test
    public void testCleanHdfsDirectory() {
        ConfigReader.readConfig();
        try {
            HdfsCleaner.delete(ConfigReader.getInputBulkDir());
            logger.info(ConfigReader.getHdfsPath() + ConfigReader.getInputBulkDir() + " is deleted from HDFS.");
        } catch (IOException e) {
            logger.error("Exception in deleting the contents of the directory: " + ConfigReader.getInputBulkDir(), e);
        }
    }
}
