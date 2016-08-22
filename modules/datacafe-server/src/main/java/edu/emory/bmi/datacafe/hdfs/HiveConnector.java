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
import edu.emory.bmi.datacafe.core.ServerExecutorEngine;
import edu.emory.bmi.datacafe.core.conf.CoreConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Connects Data Cafe to Hive. An optional data flow to store the meta data of the structured data.
 */
public class HiveConnector {

    private static Logger logger = LogManager.getLogger(HiveConnector.class.getName());

    /**
     * Writes to Hive. Only when the hive server is defined in the properties.
     *
     * @param hiveTable, the table name in Hive
     * @param query,     query to execute
     */
    public static void writeToHive(String hiveTable, String query) {
        if (!(ConfigReader.getHiveServer().equals("") || (ConfigReader.getHiveServer() == null))) {
            getDriver();

            Connection con = null;
            try {
                Statement stmt = getStatement();

                stmt.execute("drop table if exists " + hiveTable);

                stmt.execute("create table " + hiveTable + " " + query);

                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Executed the query [%s] metadata for " +
                            "Hive Table: %s", query, hiveTable));
                }

            } catch (SQLException e) {
                logger.error("SQLException in executing the Hive query for the data source, " + hiveTable, e);
            }
        }
    }

    private static Statement getStatement() throws SQLException {
        Connection con;
        con = DriverManager.getConnection(HDFSConstants.HIVE_CONNECTION_URI,
                ConfigReader.getHiveUserName(), ConfigReader.getHivePassword());

        return con.createStatement();
    }


    /**
     * Read from the Hive meta store.
     *
     * @return the list of tables.
     */
    @SuppressWarnings("Ignored")
    public static List<String> readFromHive() {

        List<String> tablesInDataLake = new ArrayList<>();

        if (!(ConfigReader.getHiveServer().equals("") || (ConfigReader.getHiveServer() == null))) {
            getDriver();

            Connection con = null;
            try {
                Statement stmt = getStatement();

                ResultSet resultSet = stmt.executeQuery("show tables");

                while (resultSet.next()) {
                    String table = resultSet.getString(1);
                    String tableFull = table + CoreConfigReader.getFileExtension(); //get Full Name
                    tablesInDataLake.add(tableFull);

                    ResultSet resultSet2 = stmt.executeQuery("SHOW COLUMNS FROM " + table);
                    while (resultSet2.next()) {
                        logger.info(tableFull + ": " + resultSet2.getString(1));
                    }
                }

            } catch (SQLException ignored) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SQL Exception ignored", ignored);
                }
            }
        }
        return tablesInDataLake;
    }

    private static void getDriver() {
        try {
            Class.forName(ConfigReader.getHiveDriver());
        } catch (ClassNotFoundException e) {
            logger.error("Exception in finding the Hive driver: " + ConfigReader.getHiveDriver(), e);
        }
    }

    public static void main(String[] args) {
        ServerExecutorEngine.init();

        List<String> temp = readFromHive();
    }
}
