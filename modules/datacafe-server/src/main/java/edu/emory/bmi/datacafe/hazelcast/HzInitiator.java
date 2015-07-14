/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.hazelcast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An empty hazelcast instance
 */
public class HzInitiator {
    private static Logger logger = LogManager.getLogger(HzInitiator.class.getName());

    public static void main(String[] args) {
        initInstance();
    }

    /**
     * Initiates the empty hazelcast instances
     */
    public static void initInstance() {
        logger.info("Initiating a Hazelcast instance.");
        HzConfigReader.readConfig();
        HazelSim.spawnInstance(HazelSimCore.getCfg());
        int size = HzObjectCollection.getHzObjectCollection().getFirstInstance().getCluster().getMembers().size();
        logger.info("Number of instances in this cluster: " + size);
    }

    /**
     * Initiates the empty hazelcast instances for a multi-tenanted deployment
     */
    public static void initInstance(String clusterName) {
        logger.info("Initiating a Hazelcast instance with cluster name, " + clusterName);
        HzConfigReader.readConfig();
        HazelSim.spawnInstance(HazelSimCore.getCfg(clusterName));
        int size = HzObjectCollection.getHzObjectCollection().getFirstInstance().getCluster().getMembers().size();
        logger.info("Number of instances in the cluster: " + clusterName + " is, " + size);
    }
}
