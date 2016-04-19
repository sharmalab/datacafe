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

import edu.emory.bmi.datacafe.interfaces.DataSourceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class of the executor engine. Relevant data source integrations should extend this class.
 */
public abstract class AbstractExecutorEngine {
    protected static Map<Class<? extends DataSourceBean>, DataSource> dataSourceMap;
    protected static Map<Class<? extends DataSourceBean>, String> datasourceNames;
    private static Logger logger = LogManager.getLogger(AbstractExecutorEngine.class.getName());

    /**
     * Initiate the data lake creation workflow
     */
    protected AbstractExecutorEngine(Map<Class<? extends DataSourceBean>, DataSource> dataSources) {
        dataSourceMap = dataSources;
        datasourceNames = new HashMap<>();
        if (logger.isDebugEnabled()) {
            logger.debug("Data source representations and connections of Data Cafe are initialized");
        }
    }
}
