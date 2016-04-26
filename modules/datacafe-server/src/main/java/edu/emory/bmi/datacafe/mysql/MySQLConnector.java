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
package edu.emory.bmi.datacafe.mysql;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.SqlConstants;
import edu.emory.bmi.datacafe.core.CoreExecutorEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connects to the MySQL data server.
 */
public class MySQLConnector {
    private static Logger logger = LogManager.getLogger(MySQLConnector.class.getName());

    /**
     * Gets data from the given table
     * @param database the database name
     * @param table the table in the given database.
     */
    public void getData(String database, String table) {
        Connection con = null;
        try {
            try {
                Class.forName(SqlConstants.MYSQL_DRIVER).newInstance();
            } catch (InstantiationException e) {
                logger.error("Exception initiating the instance", e);
            } catch (IllegalAccessException e) {
                logger.error("Illegal Access Exception", e);
            } catch (ClassNotFoundException e) {
                logger.error("Class not found Exception", e);
            }
            con = DriverManager.getConnection(SqlConstants.MYSQL_URL_PREFIX + ConfigReader.getCompleteMySQLUrl() + "/" +
                    database + ConfigReader.getAdditionalMySQLConf(),
                    ConfigReader.getMySQLUserName(), ConfigReader.getMySQLPassword());

            Statement st = con.createStatement();
            String sql = ("SELECT * FROM " + table);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int ageAtInitialDiagnosis = rs.getInt("Age_at_Initial_Diagnosis");
                String laterality = rs.getString("Laterality");
                logger.info(ageAtInitialDiagnosis + " : " + laterality);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception in initiating the connection", e);

        } finally {
            try {
                assert con != null;
                con.close();
            } catch (SQLException e) {
                logger.error("Exception in closing the connection");
            }
        }
    }

    public static void main(String[] args) {
        CoreExecutorEngine.init();
        MySQLConnector sqlConnector = new MySQLConnector();
        sqlConnector.getData("clinical", "clinical");
    }
}
