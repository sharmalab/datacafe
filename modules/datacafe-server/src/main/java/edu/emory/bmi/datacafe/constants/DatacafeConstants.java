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
package edu.emory.bmi.datacafe.constants;

import java.io.File;

/**
 * The common constants of Datacafe
 */
public final class DatacafeConstants {

    /**
     * Suppress instantiation.
     */
    private DatacafeConstants() {
    }

    public static final String DELIMITER = ",";
    public static final boolean IS_APPEND = false;

    public static final String DATACAFE_PROPERTIES_FILE = "conf/datacafe.properties";
    public static final String FILE_EXTENSION = ".csv";

    public static final int NUMBER_OF_COMPOSING_DATA_SOURCES = 2;

    public static final boolean IS_REMOTE_SERVER = false;
    public static final boolean IS_REMOTE_MONGO_SERVER = false;

    public static final String PRIVATE_KEY = HDFSConstants.CLIENT_ORIGIN_DIR + File.separator + "pradeeban.pem";
}
