package edu.emory.bmi.datacafe.client.test;

import edu.emory.bmi.datacafe.client.conf.ClientConfigReader;
import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.client.core.QueryWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Simple tests for the Data Cafe Client
 */
public class TestDataCafeClient {
    private static Logger logger = LogManager.getLogger(TestDataCafeClient.class.getName());


    /**
     * Test the get Destination in Data Lake from Drill when the original sources are known.
     */
    @Test
    public void testGetDestinationInDataLakeFromDrill() {
        ClientExecutorEngine.init();
        String out = QueryWrapper.getDestinationInDataLakeFromDrill("physionet", "patients");
        logger.info(out);
        Assert.assertEquals(out, ClientConfigReader.getDrillHdfsNameSpace() + ".`physionet_patients.csv`");
    }
}
