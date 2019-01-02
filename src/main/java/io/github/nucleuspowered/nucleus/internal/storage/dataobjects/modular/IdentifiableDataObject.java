package io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular;

import java.util.UUID;

public abstract class IdentifiableDataObject<S extends ModularDataObject<S>> extends ModularDataObject<S> {

    private UUID uuid = null;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        if (this.uuid == null) {
            this.uuid = uuid;
        }
    }
}
