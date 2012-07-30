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
package de.cwclan.cwsa.commons.abstraction;

import de.cwclan.cwsa.commons.domain.ServerStatus;
import de.cwclan.cwsa.commons.exceptions.ServerException;

/**
 * Defines basic operations which every server should support. The
 * implementation depends on how the specific script starts or stops the service
 * and how the outupt is parsed. The {@code customOperation}-Method is used to
 * support custom, server specific, operations like refresh for example.
 *
 * @author Simon Beckstein <simon.beckstein@gmail.com>
 */
public interface ServerInterface {

    /**
     * Calls the status-Parameter of the Service to determine if its running or
     * not.
     *
     * @return A {@link ServerStatus}-Object with the current state of the
     * service
     * @throws ServerException if the call fails or the output of status is
     * unexpected
     */
    public ServerStatus getStatus() throws ServerException;

    /**
     * Calls the start-Command of the service to start it.
     *
     * @return A {@link ServerStatus}-Object with the current state of the
     * service
     * @throws ServerException if the call fails or the output of status is
     * unexpected
     */
    public ServerStatus startServer() throws ServerException;

    /**
     * Calls the stop-Command of the service to stop it.
     *
     * @return A {@link ServerStatus}-Object with the current state of the
     * service
     * @throws ServerException if the call fails or the output of status is
     * unexpected
     */
    public ServerStatus stopServer() throws ServerException;

    /**
     * Calls the restart-Command to restart the service.
     *
     * @return A {@link ServerStatus}-Object with the current state of the
     * service
     * @throws ServerException if the call fails or the output of status is
     * unexpected
     */
    public ServerStatus restartServer() throws ServerException;

    /**
     * Used to call custom commands of the service.
     *
     * @param operationName the parameter of the shell script
     * @return A {@link ServerStatus}-Object with the current state of the
     * service
     * @throws ServerException if the call fails or the output of status is
     * unexpected
     */
    public ServerStatus customOperation(String operationName) throws ServerException;
}
