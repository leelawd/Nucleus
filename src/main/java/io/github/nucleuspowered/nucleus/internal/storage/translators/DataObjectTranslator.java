/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.translators;

import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.WorldDataObject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A Configurate translator that allows for {@link AbstractDataObject}s to be translated.
 */
public class DataObjectTranslator implements TypeSerializer<AbstractDataObject> {

    public static final DataObjectTranslator INSTANCE = new DataObjectTranslator();

    private DataObjectTranslator() {}

    @Nullable
    @Override
    public AbstractDataObject deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
        AbstractDataObject ado = null;
        if (type.isSupertypeOf(UserDataObject.class)) {
            ado = new UserDataObject();
        } else if (type.isSupertypeOf(WorldDataObject.class)) {
            ado = new WorldDataObject();
        } else if (type.isSupertypeOf(GeneralDataObject.class)) {
            ado = new GeneralDataObject();
        }

        if (ado != null) {
            ado.setBackingNode(value);
        }

        return ado;
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable AbstractDataObject obj, @NonNull ConfigurationNode value) throws ObjectMappingException {
        if (obj != null) {
            value.setValue(obj.getBackingNode());
        }
    }
}
