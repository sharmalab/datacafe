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
 * The constants of Datacafe
 */
public class DatacafeConstants {
    public static final String MONGO_CLIENT_HOST = "localhost";
    public static final int MONGO_CLIENT_PORT = 27017;

    public static final String DATAWAREHOUSE_CSV = "datawarehouse.csv";
    public static final String OUTPUT_FILE = "output.csv";
    public static final String CONF_FOLDER = "conf";
    public static final String DELIMITER = ",";

    public static final String HADOOP_HOME = "/home/pradeeban/programs/hadoop-2.7.0";
    public static final String HADOOP_CONF = HADOOP_HOME + File.separator + "etc" + File.separator + "hadoop";
    public static final String HDFS_PATH = "/user/datacafe/";

}
