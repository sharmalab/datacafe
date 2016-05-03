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
package edu.emory.bmi.datacafe.client.samples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.drill.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
* A sample drill query executor.
*/
public class DrillQueryExecutor {
    private static Logger logger = LogManager.getLogger(DrillQueryExecutor.class.getName());


    /* Drill JDBC Uri for local/cluster zookeeper */
    public static final String DRILL_JDBC_LOCAL_URI = "jdbc:drill:zk=local";

    /* Sample query used by Drill */
    public static final String DRILL_SAMPLE_QUERY = "SELECT * FROM cp.`employee.json` LIMIT 20";


    public static void main(String[] args) {

        Connection con = null;

        try {
            con = new Driver().connect(DRILL_JDBC_LOCAL_URI, new Properties());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DRILL_SAMPLE_QUERY);

            while (rs.next()) {
                logger.info(rs.getString(1));
                logger.info(rs.getString(2));
                logger.info(rs.getString(3));
            }
        } catch (Exception ex) {
            logger.error("Exception in connecting to Drill", ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error("SQL Exception in Drill Query Executor", e);
                }
            }
        }
    }
}