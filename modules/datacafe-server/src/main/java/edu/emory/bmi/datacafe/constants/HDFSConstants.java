/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.constants;

import java.io.File;

/**
 * Constants for Hadoop/Hive Integration.
 */
public class HDFSConstants {
    public static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    public static final String HIVE_USER_NAME = "hadoop";
    public static final String HIVE_PORT = "10000";

//    Master
//    public static final String HIVE_SERVER = "ec2-54-82-17-142.compute-1.amazonaws.com";

    // Unified - HDFS/Drill/DataCafe
    public static final String HIVE_SERVER = "localhost";
//    public static final String HIVE_SERVER = "ec2-23-22-140-139.compute-1.amazonaws.com";

    // Unified - HDFS
//    public static final String HIVE_SERVER = "ec2-23-22-140-139.compute-1.amazonaws.com";

//    Core-1
//    public static final String HIVE_SERVER_CORE_1 = "ec2-54-90-78-166.compute-1.amazonaws.com";

//    Core-2
//    public static final String HIVE_SERVER_CORE_2 = "ec2-54-83-77-50.compute-1.amazonaws.com";

    public static final String HIVE_CONNECTION_URI = "jdbc:hive2://" + HIVE_SERVER + ":" + HIVE_PORT + "/default";


    public static final String HIVE_CSV_DIR = DatacafeConstants.IS_REMOTE_HIVE_SERVER ? "/home/ec2-user/datacafe/conf/": "datacafe/";


    public static final String CLIENT_ORIGIN_DIR = "/home/ubuntu/datacafe/";
    public static final String CLIENT_CSV_DIR = CLIENT_ORIGIN_DIR + "conf/";

    public static final int HIVE_SFTP_PORT = 22;


    public static final String SFTP_USER = "ec2-user";
    public static final String SFTP_PASS = "";


    /*local*/
    //    public static String HIVE_CONNECTION_URI = "jdbc:hive2://localhost:10000/default";
    //    public static String HIVE_USER_NAME = "pradeeban";
    //    public static final String HIVE_CSV_DIR = "gsoc2015/conf/";


    public static String HIVE_PASSWORD = "";

    /*local*/
//    public static final String HADOOP_HOME = "/home/pradeeban/programs/hadoop-2.7.0"; // /etc/hadoop/conf
//    public static final String HADOOP_CONF = HADOOP_HOME + File.separator + "etc" + File.separator + "hadoop";

    public static final String HADOOP_CONF = "/etc/hadoop/conf";
    public static final String HDFS_PATH = "/user/hive/warehouse/";
}
