/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.constants;

/**
 * Constants for Hadoop/Hive Integration.
 */
public class HDFSConstants {
    public static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    public static final String HIVE_USER_NAME = "hadoop";
    public static final String HIVE_PORT = "10000";

//    Hive Server - HDFS Master - Drill Instance
    public static final String HIVE_SERVER = DatacafeConstants.IS_REMOTE_SERVER ?
        "ec2-54-158-108-220.compute-1.amazonaws.com" : "localhost";

    public static final String HIVE_CONNECTION_URI = "jdbc:hive2://" + HIVE_SERVER + ":" + HIVE_PORT + "/default";


    public static final String HIVE_TARGET_DIR = "../hadoop/datacafe/";

    public static final String HIVE_CSV_DIR = DatacafeConstants.IS_REMOTE_SERVER ? "/home/hadoop/datacafe/": "datacafe/";


    public static final String CLIENT_ORIGIN_DIR = DatacafeConstants.IS_REMOTE_SERVER ? "/home/hadoop/datacafe/": "./";
    public static final String CLIENT_CSV_DIR = CLIENT_ORIGIN_DIR + "conf/";

    public static final int HIVE_SFTP_PORT = 22;


    public static final String SFTP_USER = "ec2-user";


    /*local*/
    //    public static String HIVE_USER_NAME = "pradeeban";
    //    public static final String HIVE_CSV_DIR = "gsoc2015/conf/";


    public static String HIVE_PASSWORD = "";

    /*local*/
//    public static final String HADOOP_HOME = "/home/pradeeban/programs/hadoop-2.7.0"; // /etc/hadoop/conf
//    public static final String HADOOP_CONF = HADOOP_HOME + File.separator + "etc" + File.separator + "hadoop";

    public static final String HADOOP_CONF = "/home/pradeeban/programs/hadoop-2.7.2/etc/hadoop";
    public static final String HDFS_PATH = "/user/hdfs/";
}
