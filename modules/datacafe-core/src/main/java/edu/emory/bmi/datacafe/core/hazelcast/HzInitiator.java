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
package edu.emory.bmi.datacafe.core.hazelcast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiating an empty hazelcast instance
 */
public class HzInitiator {
    private static Logger logger = LogManager.getLogger(HzInitiator.class.getName());

    /**
     * Initiates the empty hazelcast instances
     */
    public static void initInstance() {
        logger.info("Initiating a Hazelcast instance.");
        HzConfigReader.readConfig();
        HazelSim.spawnInstance(HazelSimCore.getCfg());
        int size = HazelSim.getHazelSim().getFirstInstance().getCluster().getMembers().size();
        logger.info("Number of instances in this cluster: " + size);
    }

    /**
     * Initiates the empty hazelcast instances for a multi-tenanted deployment
     */
    public static void initInstance(String clusterName) {
        logger.info("Initiating a Hazelcast instance with cluster name, " + clusterName);
        HzConfigReader.readConfig();
        HazelSim.spawnInstance(HazelSimCore.getCfg(clusterName));
        int size = HazelSim.getHazelSim().getFirstInstance().getCluster().getMembers().size();
        logger.info("Number of instances in the cluster: " + clusterName + " is, " + size);
    }
}
