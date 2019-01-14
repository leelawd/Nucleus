/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modular;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.DataModuleFactory;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.IWorldDataModule;
import io.github.nucleuspowered.storage.dataobjects.modular.IdentifiableDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;

public class WorldDataObject extends IdentifiableDataObject<IWorldDataModule> {

    @Override protected <T extends ITransientDataModule> T tryGetTransient(Class<T> module) throws Exception {
        return DataModuleFactory.getTransientForce(module);
    }

    @Override protected <T extends IWorldDataModule> T tryGet(Class<T> module) throws Exception {
        return DataModuleFactory.getForce(module);
    }
}
