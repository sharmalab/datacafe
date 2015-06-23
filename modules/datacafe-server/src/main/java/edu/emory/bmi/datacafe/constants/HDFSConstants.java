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
    public static final String HADOOP_HOME = "/home/pradeeban/programs/hadoop-2.7.0";
    public static final String HADOOP_CONF = HADOOP_HOME + File.separator + "etc" + File.separator + "hadoop";
    public static final String HDFS_PATH = "/user/datacafe/";
}
