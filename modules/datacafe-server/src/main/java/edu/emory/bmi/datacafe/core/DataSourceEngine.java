/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Core initialization engine of DataCafe.
 */
public abstract class DataSourceEngine {

    private static Map<String, Map<String, String>> dataSourcesMap = null;

    public DataSourceEngine() {
        if (dataSourcesMap == null) {
            dataSourcesMap = new HashMap<>();
        }
    }

    public String addDataSource(Map<String, String> dataSource) {
        String uuid = UUID.randomUUID().toString();
        dataSourcesMap.put(uuid, dataSource);
        return uuid;
    }

    public Map<String, String> getDataSource(String id) {
        return dataSourcesMap.get(id);
    }
}
