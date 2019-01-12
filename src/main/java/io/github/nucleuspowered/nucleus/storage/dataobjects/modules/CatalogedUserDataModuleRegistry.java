/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modules;

import io.github.nucleuspowered.nucleus.internal.registry.NucleusRegistryModule;

public class CatalogedUserDataModuleRegistry extends NucleusRegistryModule<ICatalogedUserDataModule> {

    private static CatalogedUserDataModuleRegistry INSTANCE;

    public static CatalogedUserDataModuleRegistry getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is not yet initialised");
        }
        return INSTANCE;
    }

    public CatalogedUserDataModuleRegistry() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton already exists");
        }
        INSTANCE = this;
    }

    @Override
    public Class<ICatalogedUserDataModule> catalogClass() {
        return ICatalogedUserDataModule.class;
    }

    @Override
    public void registerDefaults() {

    }
}
