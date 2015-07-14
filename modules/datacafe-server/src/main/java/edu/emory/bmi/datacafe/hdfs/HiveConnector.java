/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import edu.emory.bmi.datacafe.constants.HDFSConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.List;

/**
 * Connecting to Hive through Datacafe.
 */
public class HiveConnector extends WarehouseConnector{
    private static Logger logger = LogManager.getLogger(HiveConnector.class.getName());


    /**
     * Writes to Hive
     * @param csvFileName, file to be written to Hive
     * @param hiveTable, the table name in Hive
     * @param query, query to execute
     * @throws SQLException, if execution failed.
     */
    public void writeToHive(String csvFileName, String hiveTable, String query)  throws SQLException {
        try {
            Class.forName(HDFSConstants.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("Exception in finding the driver", e);
        }

        Connection con = DriverManager.getConnection(HDFSConstants.HIVE_CONNECTION_URI, HDFSConstants.HIVE_USER_NAME,
                HDFSConstants.HIVE_PASSWORD);
        Statement stmt = con.createStatement();

        stmt.execute("drop table if exists " + hiveTable);

        stmt.execute("create table " + hiveTable+ query);

        String csvFilePath = HDFSConstants.HIVE_CSV_DIR + csvFileName;

        String sql = "load data local inpath '" + csvFilePath + "' into table " + hiveTable;
        stmt.execute(sql);
    }

    @Override
    public void writeToWarehouse(String[] datasourcesNames, List<String>[] texts, String[] queries) {
        for (int i = 0; i < DatacafeConstants.NUMBER_OF_COMPOSING_DATA_SOURCES; i++) {
            createFile(datasourcesNames[i], texts[i]);

            try {
                writeToHive(datasourcesNames[i] + DatacafeConstants.FILE_EXTENSION,
                        datasourcesNames[i], queries[i]);
                logger.info("Successfully written the data to the warehouse: " + datasourcesNames[i]);
            } catch (SQLException e) {
                logger.error("SQL Exception in writing to Hive Table: " + datasourcesNames[i], e);
            }
        }
    }

    @Override
    public void createFile(String fileName, List<String> lines) {

        Charset utf8 = StandardCharsets.UTF_8;

        try {
            if (DatacafeConstants.IS_APPEND) {
                Files.write(Paths.get(DatacafeConstants.CONF_FOLDER + File.separator + fileName +
                                DatacafeConstants.FILE_EXTENSION), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } else {
                Files.write(Paths.get(DatacafeConstants.CONF_FOLDER + File.separator + fileName +
                                DatacafeConstants.FILE_EXTENSION), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            }
            logger.info("Successfully written the output to the file, " + fileName);
        } catch (IOException e) {
            logger.error("Error in creating the warehouse file", e);
        }
    }

}