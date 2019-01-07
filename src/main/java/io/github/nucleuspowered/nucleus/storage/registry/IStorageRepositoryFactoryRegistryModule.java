package io.github.nucleuspowered.nucleus.storage.registry;

import io.github.nucleuspowered.nucleus.internal.annotations.Registry;
import io.github.nucleuspowered.nucleus.internal.registry.NucleusRegistryModule;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepositoryFactory;
import io.github.nucleuspowered.nucleus.storage.persistence.configurate.FlatFileStorageRepositoryFactory;

import javax.inject.Singleton;

@Singleton
@Registry(IStorageRepositoryFactory.class)
public class IStorageRepositoryFactoryRegistryModule extends NucleusRegistryModule<IStorageRepositoryFactory> {

    private static IStorageRepositoryFactoryRegistryModule INSTANCE;

    public static IStorageRepositoryFactoryRegistryModule getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is not yet initialised");
        }
        return INSTANCE;
    }

    public IStorageRepositoryFactoryRegistryModule() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton already exists");
        }
        INSTANCE = this;
    }

    @Override
    public Class<IStorageRepositoryFactory> catalogClass() {
        return IStorageRepositoryFactory.class;
    }

    @Override
    public void registerDefaults() {
        this.registerAdditionalCatalog(FlatFileStorageRepositoryFactory.INSTANCE);
    }
}
