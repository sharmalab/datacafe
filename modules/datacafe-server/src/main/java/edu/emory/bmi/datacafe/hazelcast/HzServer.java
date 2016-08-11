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
package edu.emory.bmi.datacafe.hazelcast;

import com.hazelcast.core.MultiMap;
import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import edu.emory.bmi.datacafe.core.hazelcast.HzInstance;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple Hazelcast Server instance
 */
public class HzServer extends HzInstance{

    public static void main(String[] args) {
        init();
    }

    /**
     * Adds an entry to a map
     * invoke: HzServer.addValueToMap("my-distributed-map", "sample-key", "sample-value");
     * @param mapName the name of the map
     * @param key the key
     * @param value the value
     */
    public static void addValueToMap(String mapName, String key, String value) {
        ConcurrentMap<String, String> map = firstInstance.getMap(mapName);
        map.put(key, value);
    }


    /**
     * Reads an entry from the multi-map
     * invoke: QueryBuilderServer.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the values of the entry.
     */
    public Collection<String> readValuesFromMultiMap(String mapName, String key) {
        MultiMap<String, String> map = firstInstance.getMultiMap(mapName);
        return map.get(key);
    }


    /**
     * Reads an entry from the map
     * invoke: QueryBuilderServer.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the value of the entry.
     */
    public String readValuesFromMap(String mapName, String key) {
        ConcurrentMap<String, String> map = firstInstance.getMap(mapName);
        return map.get(key);
    }


    /**
     * Get keys from a map.
     *
     * @param mapName the name of the map
     * @return the collection of keys.
     */
    public Collection<String> getKeysFromMap(String mapName) {
        ConcurrentMap<String, String> map = firstInstance.getMap(mapName);
        return map.keySet();
    }


    /**
     * Adds an entry to a multi-map. The beauty is, each key can have multiple value entries.
     * invoke: HzServer.addValueToMultiMap("my-distributed-map", "sample-key", "sample-value");
     * @param mapName the name of the map
     * @param key the key
     * @param value the value
     */
    public static void addValueToMultiMap(String mapName, String key, String value) {
        MultiMap<String, String> map = firstInstance.getMultiMap(mapName);
        map.put(key, value);
    }

    /**
     * Adds a collection of entries to a multi-map. The beauty is, each key can have multiple value entries.
     * invoke: HzServer.addValueToMultiMap("my-distributed-map", "sample-key", "sample-value");
     * @param mapName the name of the map
     * @param key the key
     * @param collection the values
     */
    public static void addValuesToMultiMap(String mapName, String key, Collection<String> collection) {
        MultiMap<String, String> map = firstInstance.getMultiMap(mapName);
        for (String value: collection) {
            map.put(key, value);
        }
    }

    /**
     * Adds an entry to the default multi-map. The beauty is, each key can have multiple value entries.
     * invoke: HzServer.addValueToMultiMap("sample-key", "sample-value");
     * @param key the key
     * @param value the value
     */
    public static void addValueToMultiMap(String key, String value) {
        addValueToMultiMap(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP, key, value);
    }


    /**
     * Let the data provider add joins in a separate map.
     * @param tenantName the name of the tenant/execution.
     * @param attribute the attribute name.
     * @param joinedAttribute the joined attribute.
     */
    public static void addJoins(String tenantName, String attribute, String joinedAttribute) {
        MultiMap<String, String> map = firstInstance.getMultiMap(tenantName + DatacafeConstants.RELATIONS_MAP_SUFFIX);
        map.put(attribute, joinedAttribute);
    }


    /**
     * Adds a set of entries to a map
     * invoke: HzServer.addValueToMap("my-distributed-map", "sample-key", Set<"sample-value">);
     * @param mapName the name of the map
     * @param key the key
     * @param valueSet the value set
     */
    public static void addValueToMap(String mapName, String key, Set<String> valueSet) {
        ConcurrentMap<String, String> map = firstInstance.getMap(mapName);

        String value = "";

        for (String val : valueSet) {
            value += val + "\n";
        }
        map.put(key, value);
    }

    /**
     * Adds an entry to the default map
     * invoke: HzServer.addValueToMap("sample-key", Set<"sample-value">);
     * @param key the key
     * @param valueSet the value set
     */
    public static void addValueToMap(String key, Set<String> valueSet) {
        addValueToMap(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP, key, valueSet);
    }


    /**
     * Gets a Hazelcast distributed map.
     * @param mapName the name of the map
     * @return the concurrent map.
     */
    public static ConcurrentMap<String, String> getMap(String mapName) {
        return firstInstance.getMap(mapName);
    }

    /**
     * Gets the default Hazelcast distributed map.
     * @return the concurrent map.
     */
    public static ConcurrentMap<String, String> getMap() {
        return firstInstance.getMap(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP);
    }
}
