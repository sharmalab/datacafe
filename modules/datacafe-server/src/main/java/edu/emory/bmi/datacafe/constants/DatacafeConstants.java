/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.constants;

import java.io.File;

/**
 * The common constants of Datacafe
 */
public class DatacafeConstants {

    public static final String CONF_FOLDER = "conf";
    public static final String DELIMITER = ",";
    public static final boolean IS_APPEND = false;

    public static final String DATACAFE_PROPERTIES_FILE = "conf/datacafe.properties";
    public static final String FILE_EXTENSION = ".csv";

    public static final int NUMBER_OF_COMPOSING_DATA_SOURCES = 2;

    public static final boolean IS_REMOTE_HIVE_SERVER = true;

    public static final String PRIVATE_KEY = HDFSConstants.CLIENT_ORIGIN_DIR + File.separator + "pradeeban.pem";
}
