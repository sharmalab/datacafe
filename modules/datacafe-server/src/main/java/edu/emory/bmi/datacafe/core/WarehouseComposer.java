/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.core;

import edu.emory.bmi.datacafe.constants.DatacafeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Makes a warehouse from the merger.
 */
public class WarehouseComposer {
    private static List<String> lines = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(WarehouseComposer.class.getName());


    /**
     * Writes the mergerList into a file
     *
     * @param mergerList the value of the merged data.
     */
    public static void writeToFile(List<Merger> mergerList) {
        boolean initial = true;
        for (Merger merger : mergerList) {
            if (initial) {
                writeHeader(merger.getJoinedMap());
                initial = false;
            }
            writeBody(merger.getJoinedMap());
        }
        createFile(false);
    }

    /**
     * Retrieves and writes the mergerList into a file.
     */
    public static void writeToFile() {
        Composer composer = Composer.getComposer();
        writeToFile(composer.getMergerList());
    }

    /**
     * Writes the headers
     *
     * @param map the map of data
     */
    public static void writeHeader(Map<String, String> map) {
        writeText(map.keySet());
    }

    /**
     * Write the set of data into a file.
     *
     * @param dataSet set of data
     */
    public static void writeText(Set<String> dataSet) {
        String temp = "";
        boolean first = true;
        for (String key : dataSet) {
            if (!first) {
                temp += DatacafeConstants.DELIMITER;
            } else {
                first = false;
            }
            temp += key;
        }
        lines.add(temp);
    }

    /**
     * Writes the body / data entries
     *
     * @param map the map of data
     */
    public static void writeBody(Map<String, String> map) {
        String temp = "";
        boolean first = true;
        for (String key : map.keySet()) {
            if (!first) {
                temp += DatacafeConstants.DELIMITER;
            } else {
                first = false;
            }
            temp += map.get(key);
        }
        lines.add(temp);
    }

    /**
     * Makes a file from the results.
     * @param isAppend should the content be appended.
     */
    public static void createFile(boolean isAppend) {

        Charset utf8 = StandardCharsets.UTF_8;

        try {
            if (isAppend) {
                Files.write(Paths.get(DatacafeConstants.CONF_FOLDER + File.separator +
                                DatacafeConstants.DATAWAREHOUSE_CSV), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } else {
                Files.write(Paths.get(DatacafeConstants.CONF_FOLDER + File.separator +
                                DatacafeConstants.DATAWAREHOUSE_CSV), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            }
            logger.info("Successfully written the output to the file, " + DatacafeConstants.DATAWAREHOUSE_CSV);
        } catch (IOException e) {
            logger.error("Error in creating the warehouse file", e);
        }
    }
}
