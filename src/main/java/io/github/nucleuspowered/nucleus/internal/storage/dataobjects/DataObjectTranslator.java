package io.github.nucleuspowered.nucleus.internal.storage.dataobjects;

import com.google.common.reflect.TypeToken;
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
        return null;
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable AbstractDataObject obj, @NonNull ConfigurationNode value) throws ObjectMappingException {

    }
}
