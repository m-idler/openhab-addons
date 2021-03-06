/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.openuv.internal.json;

/**
 * The {@link OpenUVResponse} is the Java class used to map the JSON
 * response to the OpenUV request.
 *
 * @author Gaël L'hopital - Initial contribution
 */
public class OpenUVResponse {
    private String error;
    private OpenUVResult result;

    public OpenUVResult getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
