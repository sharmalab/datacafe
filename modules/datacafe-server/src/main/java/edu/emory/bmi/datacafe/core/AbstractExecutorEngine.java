/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

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
