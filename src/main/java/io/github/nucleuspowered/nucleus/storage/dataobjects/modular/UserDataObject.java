/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.ICatalogedUserDataModule;
import io.github.nucleuspowered.storage.dataobjects.modular.IdentifiableDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;

import java.util.Optional;

public class UserDataObject extends IdentifiableDataObject<ICatalogedUserDataModule, ITransientDataModule> {

    @Override protected <T extends ITransientDataModule> Optional<T> tryGetTransient(Class<T> module) {
        return Optional.empty();
    }

    @Override protected <T extends ICatalogedUserDataModule> Optional<T> tryGet(Class<T> module) {
        return Optional.empty();
    }
}
