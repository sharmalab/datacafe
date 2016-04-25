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
package edu.emory.bmi.datacafe.core;

import edu.emory.bmi.datacafe.conf.ConfigReader;

/**
 * The core Mongo executor engine singleton
 */
public final class CoreExecutorEngine {
    private static CoreExecutorEngine coreExecutorEngine;

    /**
     * Initialize the singleton object
     */
    public static void init() {
        if (coreExecutorEngine == null) {
            coreExecutorEngine = new CoreExecutorEngine();
        }
    }

    /**
     * Executes the initialization workflow of Data Cafe once, and only once.
     */
    private CoreExecutorEngine() {
        ConfigReader.readConfig();
        DataSourcesRegistry.init();
    }
}
