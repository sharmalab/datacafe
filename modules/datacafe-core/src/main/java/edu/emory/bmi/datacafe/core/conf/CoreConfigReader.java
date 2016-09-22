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
package edu.emory.bmi.datacafe.core.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Core Configuration Reader
 */
public class CoreConfigReader {
    protected static Properties prop;
    private static Logger logger = LogManager.getLogger(CoreConfigReader.class.getName());
    private static String fileExtension;
    private static String delimiter;


    private static boolean loadProperties() {
        prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(DatacafeConstants.DATACAFE_PROPERTIES_FILE);
        } catch (FileNotFoundException ex) {
            try {
                input = new FileInputStream(DatacafeConstants.DATACAFE_PROPERTIES_FILE_ALT);
            } catch (Exception e) {
                logger.error("Error in loading the properties file.. ", ex);
                return false;
            }
        }
        try {
            prop.load(input);
            return true;
        } catch (IOException e) {
            logger.error("IOException in opening the file", e);
            return false;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initiating Data Cafe from the configuration file.
     */
    protected static void readConfig() {
        boolean loaded = loadProperties();
        if (loaded) {
            logger.info("Initiating Data Cafe Core from the configurations file..");
            fileExtension = prop.getProperty("fileExtension");
            delimiter = prop.getProperty("delimiter");
        }
    }

    public static String getFileExtension() {
        return fileExtension;
    }

    public static String getDelimiter() {
        return delimiter;
    }
}
