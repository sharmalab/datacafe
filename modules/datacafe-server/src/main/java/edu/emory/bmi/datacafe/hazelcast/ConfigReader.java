/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
