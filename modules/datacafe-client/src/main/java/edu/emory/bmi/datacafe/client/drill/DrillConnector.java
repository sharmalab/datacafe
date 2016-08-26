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
package edu.emory.bmi.datacafe.client.drill;

import edu.emory.bmi.datacafe.client.conf.ClientConfigReader;
import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.core.kernel.AbstractDataSourceConnector;
import org.apache.drill.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The connector implementation for Drill.
 */
public class DrillConnector extends AbstractDataSourceConnector {
    private static Logger logger = LogManager.getLogger(ClientExecutorEngine.class.getName());
    private static Connection connection;

    public static void initConnection() {
        try {
            String username = ClientConfigReader.getDrillUsername();
            String password = ClientConfigReader.getDrillPassword();

            Properties connectionProps = new Properties();
            connectionProps.put("user", username);
            connectionProps.put("password", password);

            connection = new Driver().connect(ClientConfigReader.getDrillJdbc(), connectionProps);
        } catch (SQLException e) {
            logger.error("SQL Exception in initiating a JDBC connection to Drill.", e);
        }
    }

    @Override
    public List getAllIDs(String database, String collection, String idAttribute) {
        return null;
    }

    @Override
    public List<String> getAttributeValues(String database, String table, List ids, String idAttribute,
                                           String[] preferredAttributes) {
        List<String> idList = new ArrayList<>();
        try {
            Statement st = connection.createStatement();

            for (Object id : ids) {
                String allAttributes;

                allAttributes = getChosenAttributeNames(preferredAttributes);
                String sql = ("SELECT " + allAttributes + " FROM " + table + " WHERE " + idAttribute + " = " +
                        "\"" + id + "\"");

                ResultSet rs = st.executeQuery(sql);
                String outcomeOfEachEntry = "";

                if (rs.next()) {
                    for (int key = 0; key < preferredAttributes.length; key++) {
                        if (key > 0) {
                            outcomeOfEachEntry += ",";
                        }
                        outcomeOfEachEntry += rs.getString(preferredAttributes[key]);
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(outcomeOfEachEntry);
                }
                idList.add(outcomeOfEachEntry);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception in initiating the connection", e);
        }
        return idList;
    }

    @Override
    public void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("SQL Exception in Drill Query Executor", e);
            }
        }
    }

    /**
     * Execute a query
     *
     * @param query the query to be executed
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Execute a query
     *
     * @param query the query to be executed
     */
    public static List<String> getQueryExecutionResponse(String query, int noOfAttributes) {
        ResultSet rs;
        List<String> responseList = new ArrayList<>();
        try {
            rs = executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= noOfAttributes; i++) {
                    responseList.add(rs.getString(i));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in executing the query.", e);
        }
        return responseList;
    }

    /**
     * Execute a query and print the output
     *
     * @param query the query to be executed
     */
    public static void executeQueryAndPrintOutput(String query, int noOfAttributes) {
        ResultSet rs;
        try {
            rs = executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= noOfAttributes; i++) {
                    logger.info(rs.getString(i));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in executing the query.", e);
        }
    }

    /**
     * Execute a query and return results
     *
     * @param query the query to be executed
     * @return the query output as a String.
     */
    public static String executeQueryAndReturn(String query, int noOfAttributes) {
        ResultSet rs;
        String out = "";
        try {
            rs = executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= noOfAttributes; i++) {
                    out += rs.getString(i) + " ";
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in executing the query.", e);
        }
        return out;
    }
}
