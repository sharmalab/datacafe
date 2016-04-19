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
package edu.emory.bmi.datacafe.conf;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class that reads the Data Cafe properties from the properties file.
 */
public class ConfigReader {

    private static Logger logger = LogManager.getLogger(ConfigReader.class.getName());
    private static String dataServerHost;
    private static int dataServerPort;
    private static String fileWriteMode;
    private static boolean isRemoteDSServer;
    private static String hadoopConf;
    private static String hdfsPath;
    private static String hiveServer = "";
    private static int hivePort;
    private static int sftpPort;
    private static String sftpUser;

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

    /**
     * Initiating Data Cafe from the configuration file.
     */
    public static void readConfig() {
        logger.info("Initiating Data Cafe from the configurations file..");

        boolean loaded = loadProperties();

        if (loaded) {
            dataServerHost = prop.getProperty("dataServerHost");
            String temp = prop.getProperty("dataServerPort");
            if (temp != null) {
                dataServerPort = Integer.parseInt(temp);
            }
            fileWriteMode = prop.getProperty("fileWriteMode");

            String remoteStr = prop.getProperty("isRemote");
            if (remoteStr != null) {
                isRemoteDSServer = Boolean.parseBoolean(remoteStr);
            }

            if (isRemoteDSServer) {
                String sftpPortStr = prop.getProperty("sftpPort");
                if (sftpPortStr != null) {
                    sftpPort = Integer.parseInt(sftpPortStr);
                }
                sftpUser = prop.getProperty("sftpUser");
            }

            hadoopConf = prop.getProperty("hadoopConf");
            hdfsPath = prop.getProperty("hdfsPath");

            hiveServer = prop.getProperty("hiveServer");

            if (!(hiveServer.equals("") || (hiveServer.equals(null)))) {
                String hivePortStr = prop.getProperty("hivePort");
                hivePort = Integer.parseInt(hivePortStr);
            }
        }
    }

    public static int getDataServerPort() {
        return dataServerPort;
    }

    public static String getDataServerHost() {
        return dataServerHost;
    }

    public static String getFileWriteMode() {
        return fileWriteMode;
    }

    public static boolean isRemoteDSServer() {
        return isRemoteDSServer;
    }

    public static String getHadoopConf() {
        return hadoopConf;
    }

    public static String getHdfsPath() {
        return hdfsPath;
    }

    public static String getHiveServer() {
        return hiveServer;
    }

    public static int getHivePort() {
        return hivePort;
    }

    public static int getSftpPort() {
        return sftpPort;
    }

    public static String getSftpUser() {
        return sftpUser;
    }
}
