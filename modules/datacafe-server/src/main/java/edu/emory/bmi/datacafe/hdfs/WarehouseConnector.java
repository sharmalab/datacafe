/*
* Title:        S²DN
* Description:  Orchestration Middleware for Incremental
*               Development of Software-Defined Cloud Networks.
* Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
*
* Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
*/
package edu.emory.bmi.datacafe.hdfs;

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
public abstract class WarehouseConnector {

    private static Logger logger = LogManager.getLogger(WarehouseConnector.class.getName());

    /**
     * Makes a file from the results.
     *
     * @param fileName Name of the file
     * @param lines    lines to be written.
     */
    public abstract void createFile(String fileName, List<String> lines);

    /**
     * Write the data to the data warehouse
     *
     * @param datasourcesNames the identifiers of the data sources
     * @param texts            the data to be written, in text format.
     * @param queries,         queries for the data.
     */
    public abstract void writeToWarehouse(String[] datasourcesNames, List<String>[] texts, String[] queries);
}
