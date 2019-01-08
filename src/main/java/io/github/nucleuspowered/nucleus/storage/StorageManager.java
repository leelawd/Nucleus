/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.internal.interfaces.Reloadable;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.persistence.configurate.FlatFileStorageRepositoryFactory;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IUserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IWorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
public final class StorageManager implements IStorageManager, Reloadable {

    @Nullable private IStorageRepository.Keyed<UUID, IUserQueryObject> userRepository;
    @Nullable private IStorageRepository.Keyed<UUID, IWorldQueryObject> worldRepository;
    @Nullable private IStorageRepository.Single generalRepository;

    private final GeneralService generalService = new GeneralService();
    private final UserService userService = new UserService();
    private final WorldService worldService = new WorldService();

    @Override
    public GeneralService getGeneralService() {
        return this.generalService;
    }

    @Override
    public UserService getUserService() {
        return this.userService;
    }

    @Override
    public WorldService getWorldService() {
        return this.worldService;
    }

    @Override @Nullable
    public IStorageRepository.Keyed<UUID, IUserQueryObject> getUserRepository() {
        if (this.userRepository == null) {
            // fallback to flat file
            this.userRepository = FlatFileStorageRepositoryFactory.INSTANCE.userRepository();
        }
        return this.userRepository;
    }

    @Override @Nullable
    public IStorageRepository.Keyed<UUID, IWorldQueryObject> getWorldRepository() {
        if (this.worldRepository== null) {
            // fallback to flat file
            this.worldRepository = FlatFileStorageRepositoryFactory.INSTANCE.worldRepository();
        }
        return this.worldRepository;
    }

    @Override @Nullable
    public IStorageRepository.Single getGeneralRepository() {
        if (this.generalRepository == null) {
            // fallback to flat file
            this.generalRepository = FlatFileStorageRepositoryFactory.INSTANCE.generalRepository();
        }
        return this.generalRepository;
    }

    @Override
    public void onReload() {
        // TODO: Data registry
        if (this.generalRepository != null) {
            this.generalRepository.shutdown();
        }

        this.generalRepository = null; // TODO: config

        if (this.worldRepository != null) {
            this.worldRepository.shutdown();
        }

        this.worldRepository = null; // TODO: config

        if (this.userRepository != null) {
            this.userRepository.shutdown();
        }

        this.userRepository = null; // TODO: config
    }

}
