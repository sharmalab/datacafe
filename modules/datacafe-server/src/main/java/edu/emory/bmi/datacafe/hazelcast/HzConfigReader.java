/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
