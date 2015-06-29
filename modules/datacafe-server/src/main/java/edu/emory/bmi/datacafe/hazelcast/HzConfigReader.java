/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *               Toolkit for Modeling and Simulation
 *               of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datacafe.hazelcast;


/**
 * The class that reads the Hazelcast properties from the properties file.
 */
public class HzConfigReader extends ConfigReader {
    private static String hazelcastXml;
    private static String mainClusterName;
    private static String subClusterName;

    public static void readConfig() {
        readConfig("mainCluster");
    }

    public static void readConfig(String iMainCluster) {
        ConfigReader.readConfig();
        hazelcastXml = prop.getProperty("hazelcastXml");
        mainClusterName = prop.getProperty(iMainCluster);
        subClusterName = prop.getProperty("subCluster");
    }

    public static String getHazelcastXml() {
        return hazelcastXml;
    }

    public static String getMainClusterName() {
        return mainClusterName;
    }

    public static String getSubClusterName() {
        return subClusterName;
    }
}
