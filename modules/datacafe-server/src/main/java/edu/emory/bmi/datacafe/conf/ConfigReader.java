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

import edu.emory.bmi.datacafe.core.hazelcast.HzConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class that reads the Data Cafe properties from the properties file.
 */
public class ConfigReader extends HzConfigReader {

    private static Logger logger = LogManager.getLogger(ConfigReader.class.getName());
    private static String dataServerHost;
    private static int dataServerPort;
    private static boolean isRemoteDSServer;

    /**
     * Hadoop HDFS Configurations.
     */
    private static String hadoopConf;
    private static String hdfsPath;


    private static String remoteTargetDir;

    private static String fileExtension;
    private static String delimiter;
    private static String privateKey;


    /**
     * Origin Directories.
     */
    private static String clientOriginDir;
    private static String clientCSVDir;

    private static String inputBulkDir;

    /**
     * MySQL Configurations
     */
    private static String completeMySQLUrl;
    private static String mySQLUserName;
    private static String mySQLPassword;
    private static String additionalMySQLConf = "";


    /**
     * Initiating Data Cafe from the configuration file.
     */
    public static void readConfig() {

        HzConfigReader.readConfig();

        dataServerHost = prop.getProperty("dataServerHost");
        String temp = prop.getProperty("dataServerPort");
        if (temp != null) {
            dataServerPort = Integer.parseInt(temp);
        }

        String remoteStr = prop.getProperty("isRemote");
        if (remoteStr != null) {
            isRemoteDSServer = Boolean.parseBoolean(remoteStr);
        }

        clientOriginDir = prop.getProperty("clientOriginDir");

        if (isRemoteDSServer) {
            remoteTargetDir = prop.getProperty("remoteTargetDir");
            clientCSVDir = prop.getProperty("clientCSVDir");
            if (clientCSVDir == null) {
                clientCSVDir = clientOriginDir + "conf/";
            }
        }

        hadoopConf = prop.getProperty("hadoopConf");
        hdfsPath = prop.getProperty("hdfsPath");

        fileExtension = prop.getProperty("fileExtension");
        delimiter = prop.getProperty("delimiter");
        privateKey = prop.getProperty("privateKey");
        inputBulkDir = prop.getProperty("inputBulkDir");

        completeMySQLUrl = prop.getProperty("completeMySQLUrl");
        if (completeMySQLUrl != null) {
            mySQLUserName = prop.getProperty("mySQLUserName");
            mySQLPassword = prop.getProperty("mySQLPassword");
            additionalMySQLConf = prop.getProperty("additionalMySQLConf");

        }
    }

    public static int getDataServerPort() {
        return dataServerPort;
    }

    public static String getDataServerHost() {
        return dataServerHost;
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

    public static String getFileExtension() {
        return fileExtension;
    }

    public static String getDelimiter() {
        return delimiter;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static String getRemoteTargetDir() {
        return remoteTargetDir;
    }

    public static String getClientOriginDir() {
        return clientOriginDir;
    }

    public static String getClientCSVDir() {
        return clientCSVDir;
    }

    public static String getInputBulkDir() {
        return inputBulkDir;
    }

    public static String getCompleteMySQLUrl() {
        return completeMySQLUrl;
    }

    public static String getMySQLUserName() {
        return mySQLUserName;
    }

    public static String getMySQLPassword() {
        return mySQLPassword;
    }

    public static String getAdditionalMySQLConf() {
        return additionalMySQLConf;
    }
}
