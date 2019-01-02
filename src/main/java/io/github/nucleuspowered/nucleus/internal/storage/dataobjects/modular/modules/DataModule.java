package io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.modules;

import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.IdentifiableDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.ModularDataObject;

import java.util.UUID;

public abstract class DataModule<S extends ModularDataObject<S>> {

    // TODO: remove this
    public abstract class Identifiable<S extends IdentifiableDataObject<S>> {

        private UUID parentIdentifier;

        public void init(UUID id) {
            if (this.parentIdentifier != null) {
                this.parentIdentifier = id;
            }
        }

        public UUID getParentIdentifier() {
            return this.parentIdentifier;
        }
    }
}
