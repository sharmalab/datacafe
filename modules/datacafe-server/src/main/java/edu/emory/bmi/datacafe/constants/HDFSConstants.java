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
package edu.emory.bmi.datacafe.constants;

/**
 * Constants for HDFS Integration.
 */
public final class HDFSConstants {

    /**
     * Suppress instantiation.
     */
    private HDFSConstants() {
    }

    public static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    public static final String HIVE_USER_NAME = "hadoop";
    public static final String HIVE_PORT = "10000";

    public static final String HIVE_SERVER = DatacafeConstants.IS_REMOTE_SERVER ?
        "ec2-54-158-108-220.compute-1.amazonaws.com" : "localhost";

    public static final String HIVE_CONNECTION_URI = "jdbc:hive2://" + HIVE_SERVER + ":" + HIVE_PORT + "/default";


    public static final String HIVE_TARGET_DIR = "../hadoop/datacafe/";

    public static final String HIVE_CSV_DIR = DatacafeConstants.IS_REMOTE_SERVER ? "/home/hadoop/datacafe/": "datacafe/";


    public static final String CLIENT_ORIGIN_DIR = DatacafeConstants.IS_REMOTE_SERVER ? "/home/hadoop/datacafe/": "./";
    public static final String CLIENT_CSV_DIR = CLIENT_ORIGIN_DIR + "conf/";

    public static final int HIVE_SFTP_PORT = 22;


    public static final String SFTP_USER = "ec2-user";

    public static String HIVE_PASSWORD = "";


    public static final String HADOOP_CONF = "/home/pradeeban/programs/hadoop-2.7.2/etc/hadoop";
    public static final String HDFS_PATH = "/user/hdfs/";
}
