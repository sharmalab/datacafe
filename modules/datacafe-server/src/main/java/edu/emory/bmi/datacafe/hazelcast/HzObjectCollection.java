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

import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;

/**
 * Access to the hazelcast distributed objects.
 */
public class HzObjectCollection extends HazelSim {
    private static HzObjectCollection hzObjectCollection = null;

    private HzObjectCollection() {
    }

    /**
     * Gets the singleton object
     * @return the hzObjectCollection singleton object
     */
    public static HzObjectCollection getHzObjectCollection() {
        if(hzObjectCollection == null) {
            hzObjectCollection = new HzObjectCollection();
        }
        return hzObjectCollection;
    }
}
