package edu.emory.bmi.datacafe.hdfs;

import edu.emory.bmi.datacafe.constants.HDFSConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * Connecting to Hive through Datacafe.
 */
public class HiveJdbcClient {
    private static Logger logger = LogManager.getLogger(HiveJdbcClient.class.getName());


    public static void main(String[] args) throws SQLException {
        writeToHive(HDFSConstants.HIVE_CSV_PATH, HDFSConstants.HIVE_FIRST_TABLE_NAME);

    }

    public static void writeToHive(String csvFilePath, String hiveTable)  throws SQLException {
        try {
            Class.forName(HDFSConstants.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("Exception in finding the driver", e);
        }

        Connection con = DriverManager.getConnection(HDFSConstants.HIVE_CONNECTION_URI, HDFSConstants.HIVE_USER_NAME,
                HDFSConstants.HIVE_PASSWORD);
        Statement stmt = con.createStatement();

        stmt.execute("drop table if exists " + hiveTable);

        stmt.execute("create table " + hiveTable+ " (SlideBarCodeID string, SlideBarCode string, PatientID string," +
                "Gender string, Laterality string) row format delimited fields terminated by ',' stored as textfile");

        // show tables
        String sql = "show tables '" + hiveTable + "'";
        logger.info("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            logger.info(res.getString(1));
        }
        // describe table
        sql = "describe " + hiveTable;
        logger.info("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            logger.info(res.getString(1) + "\t" + res.getString(2));
        }



        sql = "load data local inpath '" + csvFilePath + "' into table " + hiveTable;
        logger.info("Running: " + sql);
        stmt.execute(sql);

        // select * query
        sql = "select * from " + hiveTable;
        logger.info("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            logger.info(String.valueOf(res.getString(1)) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t" +
                    res.getString(4) + "\t" + res.getString(5));
        }

        // regular hive query
        sql = "select count(1) from " + hiveTable;
        logger.info("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            logger.info(res.getString(1));
        }

    }
}