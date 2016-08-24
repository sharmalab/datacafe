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
package edu.emory.bmi.datacafe.client.core;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import edu.emory.bmi.datacafe.core.conf.DatacafeConstants;
import edu.emory.bmi.datacafe.core.hazelcast.HazelSim;
import edu.emory.bmi.datacafe.core.hazelcast.HazelSimCore;
import edu.emory.bmi.datacafe.core.hazelcast.HzConfigReader;
import edu.emory.bmi.datacafe.core.hazelcast.HzInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * The client instance for Hazelcast In-Memory Data Grid
 */
public class HzClient extends HzInstance {
    private static Logger logger = LogManager.getLogger(HzClient.class.getName());
    private static HazelcastInstance clientInstance;

    /**
     * Initializes a Hazelcast client instance. Core of the Data Cafe Client Instance.
     */
    public static void initClient() {
        logger.info("Initiating a Hazelcast Client instance.");
        HzConfigReader.readConfig();
        ClientConfig clientConfig = new ClientConfig();
        // The credentials as set in hazelcast.xml.
        clientConfig.getGroupConfig().setName(HzConfigReader.getMainClusterName());
        clientConfig.getGroupConfig().setPassword("dev-pass");
        clientInstance = HazelcastClient.newHazelcastClient(clientConfig);
    }

    /**
     * Initializes a Hazelcast lite instance. Recommended to use the initClient() method instead.
     */
    public static void initLite() {
        logger.info("Initiating a Hazelcast Lite instance.");
        HzConfigReader.readConfig();
        Config config = HazelSimCore.getCfg();
        config.setLiteMember(true);
        clientInstance = Hazelcast.newHazelcastInstance(config);
        int size = HazelSim.getHazelSim().getFirstInstance().getCluster().getMembers().size();
        logger.info("Number of instances in this cluster: " + size);
    }

    /**
     * Reads an entry from the multi-map
     * invoke: HzClient.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the values of the entry.
     */
    public static Collection<String> readValuesFromMultiMap(String mapName, String key) {
        MultiMap<String, String> map = clientInstance.getMultiMap(mapName);
        return map.get(key);
    }

    /**
     * Gets all the datalakes in the cluster.
     *
     * @return the datalakes.
     */
    public static Collection<String> getDataLakeNames() {
        return readValuesFromMultiMap(DatacafeConstants.DATALAKES_META_MAP,
                DatacafeConstants.DATALAKES_NAMES);
    }

    /**
     * Reads an entry from the multi-map
     * invoke: HzClient.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @return the values of the entry.
     */
    public static Collection<DistributedObject> getDistributedObjects() {
        Collection<DistributedObject> distributedObjects = clientInstance.getDistributedObjects();

        for (DistributedObject distributedObject: distributedObjects) {
            logger.info(distributedObject.getName());
        }
        return distributedObjects;
    }

    /**
     * Return a value for a given key from the multi-map
     * invoke: HzClient.readValuesFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return a value of the entry.
     */
    private static String getAValueFromMultiMap(String mapName, String key) {
        MultiMap<String, String> map = clientInstance.getMultiMap(mapName);
        Collection<String> entries = map.get(key);
        Object[] anEntry = entries.toArray();
        return anEntry[0].toString();
    }

    /**
     * Return an attribute for a given key from the multi-map
     * invoke: HzClient.getAnAttributeFromMultiMap("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param attributeName     the key
     * @return a value of the entry.
     */
    public static String getAnAttributeFromMultiMap(String mapName, String attributeName) {
        MultiMap<String, String> map = clientInstance.getMultiMap(mapName);
        Collection<String> entries = map.get(attributeName.toLowerCase());
        Object[] anEntry = entries.toArray();
        return anEntry[0].toString();
    }


    /**
     * Reads an entry from the default multi-map
     * invoke: HzClient.readValuesFromMultiMap("sample-key");
     *
     * @param key the key
     * @return the values of the entry.
     */
    public static Collection<String> readValuesFromDefaultMultiMap(String key) {
        return readValuesFromMultiMap(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP, key);
    }

    /**
     * Reads an entry from the map
     * invoke: HzClient.readValues("my-distributed-map", "sample-key");
     *
     * @param mapName the name of the map
     * @param key     the key
     * @return the value of the entry.
     */
    public static String readValues(String mapName, String key) {
        ConcurrentMap<String, String> map = clientInstance.getMap(mapName);
        return map.get(key);
    }

    /**
     * Reads and prints a value from a multi-map.
     *
     * @param mapName the name of the map
     * @param key     the key
     */
    public static void printValuesFromMultiMap(String mapName, String key) {
        Collection<String> values = readValuesFromMultiMap(mapName, key);
        for (String val : values) {
            logger.info("The key, value is: " + key + ", " + val);
        }
        if (values.size() <= 0) {
            logger.info("No entry found for the key: " + key);
        }
    }



    /**
     * Reads an entry from the default map
     * invoke: HzClient.readValues("sample-key");
     *
     * @param key the key
     * @return the value of the entry.
     */
    public static String readValues(String key) {
        return readValues(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP, key);
    }

    /**
     * Reads and prints a value from the default multi-map.
     *
     * @param key the key
     */
    public static void printValuesFromMultiMap(String key) {
        printValuesFromMultiMap(DatacafeConstants.DEFAULT_HAZELCAST_MULTI_MAP, key);
    }

    /**
     * Gets the join attributes for a given attribute.
     * @param datalakeID the datalake id.
     * @param joinAttribute the attribute
     * @return the collection of attributes that are equal in a join with the current attribute.
     */
    public static Collection<String> getJoins(String datalakeID, String joinAttribute) {
        MultiMap<String, String> map = clientInstance.getMultiMap(datalakeID + DatacafeConstants.RELATIONS_MAP_SUFFIX);
        return map.get(joinAttribute);
    }
}
