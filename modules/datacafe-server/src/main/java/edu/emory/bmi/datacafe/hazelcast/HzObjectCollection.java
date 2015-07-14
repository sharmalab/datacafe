/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
