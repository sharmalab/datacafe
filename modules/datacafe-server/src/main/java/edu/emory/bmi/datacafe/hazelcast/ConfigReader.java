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

import edu.emory.bmi.datacafe.constants.DatacafeConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class that reads the simulation properties from the properties file.
 */
public class ConfigReader {

    private static boolean isVerbose; //true

    private static int simultaneousInstances = 1;
    private static int noOfExecutions = 1;
    private static int mapReduceSize;
    private static String loadFolder;
    private static int filesRead;

    protected static Properties prop;

    private static boolean loadProperties() {
        prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(DatacafeConstants.DATACAFE_PROPERTIES_FILE);
            prop.load(input);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readConfig() {
        boolean loaded = loadProperties();

        if (loaded) {
            String temp = prop.getProperty("mapReduceSize");
            if (temp!= null) {
                // map-reduce simulations
                mapReduceSize = Integer.parseInt(temp);
            }
            isVerbose = Boolean.parseBoolean(prop.getProperty("isVerbose"));
            loadFolder = prop.getProperty("loadFolder");
            temp = prop.getProperty("filesRead");
            filesRead = (temp == null) ? 0 : Integer.parseInt(temp);
            if (temp != null) {
                simultaneousInstances = Integer.parseInt(prop.getProperty("simultaneousInstances"));
                noOfExecutions = Integer.parseInt(prop.getProperty("noOfExecutions"));
            }
        }
    }

    public static int getSimultaneousInstances() {
        return simultaneousInstances;
    }

    public static int getNoOfExecutions() {
        return noOfExecutions;
    }

    public static boolean getIsVerbose() {
        return isVerbose;
    }

    public static int getMapReduceSize() {
        return mapReduceSize;
    }

    public static String getLoadFolder() {
        return loadFolder;
    }

    public static int getFilesRead() {
        return filesRead;
    }
}
