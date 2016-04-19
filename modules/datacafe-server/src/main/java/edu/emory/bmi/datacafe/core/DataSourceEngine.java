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
