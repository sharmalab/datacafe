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
    public static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    public static String HIVE_USER_NAME = "hadoop";

    public static String HIVE_CONNECTION_URI = "jdbc:hive2://ec2-54-82-17-142.compute-1.amazonaws.com:10000/default";
    public static final String HIVE_CSV_DIR = "datacafe/conf/";

    /*local*/
    //    public static String HIVE_CONNECTION_URI = "jdbc:hive2://localhost:10000/default";
    //    public static String HIVE_USER_NAME = "pradeeban";
    //    public static final String HIVE_CSV_DIR = "gsoc2015/conf/";


    public static String HIVE_PASSWORD = "";

}
