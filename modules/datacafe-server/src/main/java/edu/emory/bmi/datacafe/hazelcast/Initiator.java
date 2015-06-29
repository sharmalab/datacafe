/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *               Toolkit for Modeling and Simulation
 *               of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datacafe.hazelcast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An empty hazelcast instance
 */
public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());

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
