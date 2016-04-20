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
package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.HDFSConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connects Data Cafe to Hive.
 */
public class HiveConnector {

    private static Logger logger = LogManager.getLogger(HiveConnector.class.getName());

    /**
     * Writes to Hive
     * @param csvFileName, file to be written to Hive
     * @param hiveTable, the table name in Hive
     * @param query, query to execute
     * @throws java.sql.SQLException, if execution failed.
     */
    public void writeToHive(String csvFileName, String hiveTable, String query)  throws SQLException {
        try {
            Class.forName(ConfigReader.getHiveDriver());
        } catch (ClassNotFoundException e) {
            logger.error("Exception in finding the driver", e);
        }

        Connection con = DriverManager.getConnection(HDFSConstants.HIVE_CONNECTION_URI, ConfigReader.getHiveUserName(),
                ConfigReader.getHivePassword());
        Statement stmt = con.createStatement();

        stmt.execute("drop table if exists " + hiveTable);

        stmt.execute("create table " + hiveTable+ query);

        String csvFilePath = ConfigReader.getHiveCSVDir() + csvFileName;

        String sql = "load data local inpath '" + csvFilePath + "' into table " + hiveTable;
        stmt.execute(sql);
    }
}