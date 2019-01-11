/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataaccess;

import com.google.gson.JsonObject;
import io.github.nucleuspowered.nucleus.storage.dataaccess.configurate.ConfigurationNodeJsonTranslator;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.ModularDataObject;
import ninja.leaping.configurate.ConfigurationNode;

@FunctionalInterface
public interface IModularDataAccess<R extends ModularDataObject<R>> extends IDataAccess<R> {

    @Override
    default R fromJsonObject(JsonObject object) {
        // Get the ConfigNode from the JsonObject
        ConfigurationNode node = ConfigurationNodeJsonTranslator.INSTANCE.from(object);
        R obj = createNew().get();
        obj.setBackingNode(node);
        return obj;
    }

    @Override
    default JsonObject toJsonObject(R object) {
        ConfigurationNode node = object.getBackingNode();
        return ConfigurationNodeJsonTranslator.INSTANCE.jsonFrom(node);
    }

}
