/*
 * Copyright 2012 CWCLAN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cwclan.cwsa.serverendpoint.implementations;

import de.cwclan.cwsa.commons.domain.ServerCommand;
import de.cwclan.cwsa.commons.domain.ServerStatus;
import de.cwclan.cwsa.serverendpoint.abstraction.AbstractServerEndpoint;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public class DummyServer extends AbstractServerEndpoint {

    private boolean running = false;

    public DummyServer(ServerCommand shellScript) {
	super(shellScript);
    }

    @Override
    public ServerStatus parseOutput(String output) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    
    

}
