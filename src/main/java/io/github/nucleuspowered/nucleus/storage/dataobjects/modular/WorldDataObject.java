/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.modules.DataModule;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.modules.TransientDataModule;

import java.util.Optional;

public class WorldDataObject extends IdentifiableDataObject<WorldDataObject> {

    @Override <T extends TransientDataModule<WorldDataObject>> Optional<T> tryGetTransient(Class<T> module) {
        return Optional.empty();
    }

    @Override <T extends DataModule<WorldDataObject>> Optional<T> tryGet(Class<T> module) {
        return Optional.empty();
    }
}
