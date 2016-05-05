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
package edu.emory.bmi.datacafe.impl.hazelcast.main;

import com.hazelcast.core.HazelcastInstance;
import edu.emory.bmi.datacafe.core.hazelcast.HazelSim;
import edu.emory.bmi.datacafe.core.hazelcast.HzInitiator;

import java.util.concurrent.ConcurrentMap;

/**
 * A sample Hazelcast Server
 */
public class HzServer {
    public static void main(String[] args) {
        HzInitiator.initInstance();
        HazelcastInstance firstInstance = HazelSim.getHazelSim().getFirstInstance();
        ConcurrentMap<String, String> map = firstInstance.getMap("my-distributed-map");
        map.put("key", "sample-value");
    }
}
