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
