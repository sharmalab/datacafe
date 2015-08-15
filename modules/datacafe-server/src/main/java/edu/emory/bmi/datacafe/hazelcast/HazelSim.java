/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A singleton that integrates Hazelcast into Cloud2Sim and initiates Hazelcast.
 */
public class HazelSim {
    private static HazelSim hazelSim = null;
    protected static List<HazelcastInstance> instances = new ArrayList<>();

    protected static Map<String, List<HazelcastInstance>> instancesMap;
    protected boolean multiTenanted = false;

    /**
     * Protected constructor to avoid instantiation of the singleton class
     */
    protected HazelSim() {
        if (multiTenanted) {
            for (String key : instancesMap.keySet()) {
                List<HazelcastInstance> instances = new ArrayList<>();
                instancesMap.put(key, instances);
            }
        }
    }

    public boolean isMultiTenanted() {
        return multiTenanted;
    }

    public void setMultiTenanted(boolean multiTenanted) {
        this.multiTenanted = multiTenanted;
    }

    /**
     * Creates a HazelSim object and initializes an array of Hazelcast instances.
     *
     * @return the hazelsim object.
     */
    public static HazelSim getHazelSim() {
        if (hazelSim == null) {
            hazelSim = new HazelSim();
        }
        return hazelSim;
    }

    /**
     * Start multiple hazelcast instances
     *
     * @param config        hazelcast configurations
     * @param instanceCount number of instances to be spawned
     */
    public static void spawnInstances(Config config, int instanceCount) {
        for (int i = 0; i < instanceCount; i++) {
            instances.add(Hazelcast.newHazelcastInstance(config));
        }
    }

    /**
     * Start a single hazelcast instance
     *
     * @param config hazelcast configurations
     */
    public static void spawnInstance(Config config) {
        instances.add(Hazelcast.newHazelcastInstance(config));
    }

    /**
     * Gets the compatibility instances.
     *
     * @return the compatibility instances.
     */
    public List<HazelcastInstance> getHazelcastInstances() {
        return instances;
    }

    public HazelcastInstance getFirstInstance() {
        return getNthInstance(HzConstants.FIRST);
    }

    /**
     * For a multi-tenanted deployment
     *
     * @return first instance of the cluster
     */
    public HazelcastInstance getFirstInstance(String clusterName) {
        return getNthInstance(clusterName, HzConstants.FIRST);
    }


    public HazelcastInstance getLastInstance() {
        return getNthInstance(HzConstants.LAST);
    }

    /**
     * Gets a specific instance, marked by the index
     *
     * @param i, the index
     * @return the hazelcast instance
     */
    public HazelcastInstance getNthInstance(int i) {
        /**
         * Delaying for the time to catch up with the initialization.
         */
        while (instances.size() <= i) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instances.get(i);
    }

    /**
     * Gets a specific instance, marked by the index. For a multi-tenanted deployment.
     *
     * @param i,           the index
     * @param clusterName, name of the cluster.
     * @return the hazelcast instance
     */
    public HazelcastInstance getNthInstance(String clusterName, int i) {
        List<HazelcastInstance> instances = instancesMap.get(clusterName);
        return instances.get(i);
    }

}

