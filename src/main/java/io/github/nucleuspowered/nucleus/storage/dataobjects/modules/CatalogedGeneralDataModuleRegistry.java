/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modules;

import io.github.nucleuspowered.nucleus.internal.registry.NucleusRegistryModule;

public class CatalogedGeneralDataModuleRegistry extends NucleusRegistryModule<ICatalogedGeneralDataModule> {

    private static CatalogedGeneralDataModuleRegistry INSTANCE;

    public static CatalogedGeneralDataModuleRegistry getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is not yet initialised");
        }
        return INSTANCE;
    }

    public CatalogedGeneralDataModuleRegistry() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton already exists");
        }
        INSTANCE = this;
    }

    @Override
    public Class<ICatalogedGeneralDataModule> catalogClass() {
        return ICatalogedGeneralDataModule.class;
    }

    @Override
    public void registerDefaults() {

    }
}
