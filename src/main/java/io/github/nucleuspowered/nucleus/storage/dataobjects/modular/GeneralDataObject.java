/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.ICatalogedGeneralDataModule;
import io.github.nucleuspowered.storage.dataobjects.modular.ModularDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;

import java.util.Optional;

public class GeneralDataObject extends ModularDataObject<ICatalogedGeneralDataModule, ITransientDataModule> {

    @Override public <T extends ITransientDataModule> Optional<T> tryGetTransient(Class<T> module) {
        return Optional.empty();
    }

    @Override public <T extends ICatalogedGeneralDataModule> Optional<T> tryGet(Class<T> module) {
        return Optional.empty();
    }
}
