/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.DataModuleFactory;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.IGeneralDataModule;
import io.github.nucleuspowered.storage.dataobjects.modular.ModularDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;

public class GeneralDataObject extends ModularDataObject<IGeneralDataModule, ITransientDataModule> {

    @Override protected <T extends ITransientDataModule> T tryGetTransient(Class<T> module) throws Exception {
        return DataModuleFactory.getTransientForce(module);
    }

    @Override protected <T extends IGeneralDataModule> T tryGet(Class<T> module) throws Exception {
        return DataModuleFactory.getForce(module);
    }
}
