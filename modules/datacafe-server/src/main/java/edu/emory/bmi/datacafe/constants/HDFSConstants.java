/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.constants;

import java.io.File;

/**
 * Constants for Hadoop/Hive Integration.
 */
public class HDFSConstants {
    public static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    public static String HIVE_CONNECTION_URI = "jdbc:hive2://localhost:10000/default";
    public static String HIVE_USER_NAME = "pradeeban";
    public static String HIVE_PASSWORD = "";

    public static final String HIVE_CSV_DIR = "gsoc2015/conf/";
}
