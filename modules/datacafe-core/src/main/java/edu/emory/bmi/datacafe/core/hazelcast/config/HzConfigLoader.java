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
package edu.emory.bmi.datacafe.core.hazelcast.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.GroupConfig;
import edu.emory.bmi.datacafe.core.hazelcast.HzIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

/**
 * The singleton base class loading hazelcast configurations for Data Cafe.
 */
public class HzConfigLoader {
    private static HzConfigLoader hzConfigLoader = null;
    private static String clusterGroup = HzConstants.MAIN_HZ_CLUSTER;
    private static Logger logger = LogManager.getLogger(HzConfigLoader.class.getName());


    private HzConfigLoader(int noOfSimultaneousInstances) {
        Config cfg = getCfg();
        HzIntegrator.spawnInstances(cfg, noOfSimultaneousInstances);
    }

    /**
     * Gets the configurations
     * @return hazelcast configurations
     */
    public static Config getCfg() {
        return getCfg(clusterGroup);
    }

    /**
     * Gets the configurations
     * @return hazelcast configurations
     */
    public static Config getCfg(String mainCluster) {
        Config cfg;
        try {
            cfg = new FileSystemXmlConfig(HzConstants.HAZELCAST_CONFIG_FILE);
        } catch (FileNotFoundException e) {
            logger.info(HzConstants.HAZELCAST_CONFIG_FILE_NOT_FOUND_ERROR);
            cfg = new Config();
        }
        cfg.setProperty("hazelcast.initial.min.cluster.size", String.valueOf(HzConstants.NO_OF_PARALLEL_EXECUTIONS));
        cfg.setProperty("hazelcast.operation.call.timeout.millis", "50000000");
        GroupConfig groupConfig = new GroupConfig(mainCluster);
        cfg.setGroupConfig(groupConfig);
        return cfg;
    }



    public static HzConfigLoader getHzConfigLoader(int noOfSimultaneousInstances, String _clusterGroup) {
        if (hzConfigLoader == null) {
            clusterGroup = _clusterGroup;
            hzConfigLoader = new HzConfigLoader(noOfSimultaneousInstances);
        }
        return hzConfigLoader;
    }

    public static HzConfigLoader getHzConfigLoader(int noOfSimultaneousInstances) {
        if (hzConfigLoader == null) {
            hzConfigLoader = new HzConfigLoader(noOfSimultaneousInstances);
        }
        return hzConfigLoader;
    }
}
