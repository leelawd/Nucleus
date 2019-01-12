/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.internal.interfaces.Reloadable;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IUserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IWorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;
import io.github.nucleuspowered.storage.IStorageManager;
import io.github.nucleuspowered.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.storage.dataaccess.IModularDataAccess;
import io.github.nucleuspowered.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.storage.persistence.configurate.FlatFileStorageRepositoryFactory;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
public final class StorageManager implements IStorageManager, Reloadable {

    @Nullable private IStorageRepository.Keyed<UUID, IUserQueryObject> userRepository;
    @Nullable private IStorageRepository.Keyed<UUID, IWorldQueryObject> worldRepository;
    @Nullable private IStorageRepository.Single generalRepository;

    private final IModularDataAccess<UserDataObject> userDataAccess = UserDataObject::new;
    private final IModularDataAccess<WorldDataObject> worldDataAccess = WorldDataObject::new;
    private final IModularDataAccess<GeneralDataObject> generalDataAccess = GeneralDataObject::new;

    private final GeneralService generalService = new GeneralService(() -> this.generalDataAccess, this::getGeneralRepository);
    private final UserService userService = new UserService(() -> this.userDataAccess, this::getUserRepository);
    private final WorldService worldService = new WorldService(() -> this.worldDataAccess, this::getWorldRepository);

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

    @Override public IDataAccess<UserDataObject> getUserDataAccess() {
        return this.userDataAccess;
    }

    @Override public IDataAccess<WorldDataObject> getWorldDataAccess() {
        return this.worldDataAccess;
    }

    @Override public IDataAccess<GeneralDataObject> getGeneralDataAccess() {
        return this.generalDataAccess;
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
