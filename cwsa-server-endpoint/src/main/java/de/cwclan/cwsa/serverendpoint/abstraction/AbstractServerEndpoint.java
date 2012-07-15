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
import de.cwclan.cwsa.commons.domain.ServerStatus;
import de.cwclan.cwsa.commons.exceptions.ServerException;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public abstract class AbstractServerEndpoint implements ServerInterface {

    private static Logger log = LoggerFactory.getLogger(AbstractServerEndpoint.class);
    private boolean running;
    private AbstractService service;
    private ServerCommand shellScript;

    public AbstractServerEndpoint(AbstractService service) {
	this.service = service;
	this.shellScript = service.getShellScript();
    }

    @Override
    public ServerStatus getStatus() throws ServerException {
	String output = executeOperation(shellScript.getStatusCmd());
	return service.parseStatusCommand(output);
    }

    @Override
    public ServerStatus startServer() throws ServerException {
	String output = executeOperation(shellScript.getStartCmd());
	return service.parseStartCommand(output);
    }

    @Override
    public ServerStatus stopServer() throws ServerException {
	String output = executeOperation(shellScript.getStopCmd());
	return service.parseStopCommand(output);
    }

    @Override
    public ServerStatus restartServer() throws ServerException {
	ServerStatus stop = service.parseStopCommand(executeOperation(shellScript.getStopCmd()));
	if (!stop.isRunning()) {
	    return startServer();
	} else {
	    return stop;
	}
    }

    @Override
    public ServerStatus customOperation(String operationName) throws ServerException {
	String output = executeOperation(operationName);
	return service.parseCustomCommand(operationName, output);
    }

    public String executeOperation(String operationName) throws ServerException {
	StringBuilder output = new StringBuilder();
	try {

	    Process process = Runtime.getRuntime().exec(shellScript.getShellScriptPath().getAbsolutePath());
	    InputStream in = process.getInputStream();

	    int c;
	    while ((c = in.read()) != -1) {
		output.append(c);
	    }
	    in.close();
	    process.waitFor();
	    int returnVal = process.exitValue();
	    if ((returnVal) != 0) {
		throw new ServerException("Script has terminated with return value != 0: " + returnVal);
	    }


	} catch (InterruptedException ex) {
	} catch (IOException ex) {
	}
	return output.toString();
    }

    public abstract ServerStatus refreshStatus(String output) throws ServerException;
}
