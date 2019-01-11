/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataaccess;

import com.google.gson.JsonObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;

import java.util.function.Supplier;

public interface IDataAccess<R extends AbstractDataObject> {

    Supplier<R> createNew();

    R fromJsonObject(JsonObject object);

    JsonObject toJsonObject(R object);

}
