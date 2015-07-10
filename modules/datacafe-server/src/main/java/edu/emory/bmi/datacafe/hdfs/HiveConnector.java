package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.constants.HDFSConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Connecting to Hive through Datacafe.
 */
public class HiveConnector {
    private static Logger logger = LogManager.getLogger(HiveConnector.class.getName());


    /**
     * Writes to Hive
     * @param csvFileName, file to be written to Hive
     * @param hiveTable, the table name in Hive
     * @param query, query to execute
     * @throws SQLException, if execution failed.
     */
    public static void writeToHive(String csvFileName, String hiveTable, String query)  throws SQLException {
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
}