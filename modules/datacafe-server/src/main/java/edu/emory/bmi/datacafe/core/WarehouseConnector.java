/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
public interface WarehouseConnector {

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
