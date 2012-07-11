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
package de.cwclan.cwsa.serverendpoint.abstraction;

import de.cwclan.cwsa.commons.abstraction.ServerInterface;
import de.cwclan.cwsa.commons.domain.ServerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public abstract class AbstractServerEndpoint implements ServerInterface {
    private static Logger log = LoggerFactory.getLogger(AbstractServerEndpoint.class);
    
    private ServerCommand shellScript;

    public AbstractServerEndpoint(ServerCommand shellScript) {
	this.shellScript = shellScript;
    }
    
    
    
    
}
