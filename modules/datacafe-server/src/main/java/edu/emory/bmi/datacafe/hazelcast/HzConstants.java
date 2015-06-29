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

/**
 * Constants specific to Hazelcast
 */
public final class HzConstants {
    /**
     * Suppress instantiation.
     */
    private HzConstants() {
    }

    public static final int HAZELCAST_INSTANCES_STARTED_SIMULTANEOUSLY = HzConfigReader.getSimultaneousInstances();
    public static final int NO_OF_PARALLEL_EXECUTIONS = HzConfigReader.getNoOfExecutions();
    public static final int NO_OF_HAZELCAST_INSTANCES = NO_OF_PARALLEL_EXECUTIONS *
            HAZELCAST_INSTANCES_STARTED_SIMULTANEOUSLY;


    public static final String HAZELCAST_CONFIG_FILE = HzConfigReader.getHazelcastXml();
    public static final String HAZELCAST_CONFIG_FILE_NOT_FOUND_ERROR =
            "Hazelcast Configuration File not found. Using the default.";
    public static final int FIRST = 0;
    public static final int LAST = HAZELCAST_INSTANCES_STARTED_SIMULTANEOUSLY - 1;

    public static final String MAIN_HZ_CLUSTER = HzConfigReader.getMainClusterName();
    public static final String SUB_HZ_CLUSTER = HzConfigReader.getSubClusterName();
}
