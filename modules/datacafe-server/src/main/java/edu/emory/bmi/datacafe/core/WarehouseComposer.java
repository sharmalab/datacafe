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
    private static Logger logger = LogManager.getLogger(WarehouseComposer.class.getName());
    /**
     * Makes a file from the results.
     */
    public static void createFile(List<String> lines) {
        createFile(DatacafeConstants.DATAWAREHOUSE_CSV, lines);
    }

    public static void createFile(String fileName, List<String> lines) {
        Charset utf8 = StandardCharsets.UTF_8;

        try {
            if (DatacafeConstants.IS_APPEND) {
                Files.write(Paths.get(DatacafeConstants.CONF_FOLDER + File.separator + fileName +
                                DatacafeConstants.FILE_EXTENSION), lines, utf8, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
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
