/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.dataservices.modular.DataModule;
import io.github.nucleuspowered.nucleus.dataservices.modular.TransientModule;

import java.util.Optional;

public class WorldDataObject extends IdentifiableDataObject<WorldDataObject> {

    @Override <T extends TransientModule<WorldDataObject>> Optional<T> tryGetTransient(Class<T> module) {
        return Optional.empty();
    }

    @Override <T extends DataModule<WorldDataObject>> Optional<T> tryGet(Class<T> module) {
        return Optional.empty();
    }
}
