/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.exceptions;

import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.IQueryObject;

public class DataQueryException extends Exception {

    public DataQueryException(String message, IQueryObject queryObject) {
        super(message + " - " + queryObject.toString());
    }
}
