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
package edu.emory.bmi.datacafe.client.conf;

import edu.emory.bmi.datacafe.core.hazelcast.HzConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class that reads the Data Cafe properties from the properties file.
 */
public class ClientConfigReader extends HzConfigReader {

    private static Logger logger = LogManager.getLogger(ClientConfigReader.class.getName());

    /**
     * Drill properties
     */
    private static String drillJdbc;
    private static String drillUsername;
    private static String drillPassword;

    /**
     * Initiating Data Cafe from the configuration file.
     */
    public static void readConfig() {

        HzConfigReader.readConfig();

        drillJdbc = prop.getProperty("drillJdbc");
        drillUsername = prop.getProperty("drillUsername");
        drillPassword = prop.getProperty("drillPassword");
    }

    public static String getDrillJdbc() {
        return drillJdbc;
    }

    public static String getDrillUsername() {
        return drillUsername;
    }

    public static String getDrillPassword() {
        return drillPassword;
    }
}
