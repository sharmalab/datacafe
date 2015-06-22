/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Composes the composite object from the queries. A Singleton.
 */
public class Composer {
    private static Composer composer;
    private static List<Merger> mergerList;

    /**
     * Singleton. Not to invoke from outside. Constructs the mergerList.
     */
    private Composer() {
        mergerList = new ArrayList<>();
    }

    /**
     * Method to create the singleton composer object.
     * @return the composer.
     */
    public static Composer getComposer() {
        if (composer == null) {
            composer = new Composer();
        }
        return composer;
    }

    /**
     * Gets the merger list.
     * @return the merger list.
     */
    public List<Merger> getMergerList() {
        return mergerList;
    }

    /**
     * Adds entry to the list.
     * @param merger entry to be added to the list.
     */
    public void addEntry(Merger merger) {
        mergerList.add(merger);
    }
}

