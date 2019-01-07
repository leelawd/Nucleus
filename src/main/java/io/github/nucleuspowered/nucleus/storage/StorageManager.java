package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.internal.interfaces.Reloadable;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.persistence.configurate.FlatFileStorageRepositoryFactory;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;

import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
public final class StorageManager implements IStorageManager, Reloadable {

    @Nullable private IStorageRepository<UserQueryObject, UserDataObject> userRepository;
    @Nullable private IStorageRepository<WorldQueryObject, WorldDataObject> worldRepository;
    @Nullable private IStorageRepository<GeneralQueryObject, GeneralDataObject> generalRepository;

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
    public IStorageRepository<UserQueryObject, UserDataObject> getUserRepository() {
        if (this.userRepository == null) {
            // fallback to flat file
            this.userRepository = FlatFileStorageRepositoryFactory.INSTANCE.userRepository();
        }
        return this.userRepository;
    }

    @Override @Nullable
    public IStorageRepository<WorldQueryObject, WorldDataObject> getWorldRepository() {
        if (this.worldRepository== null) {
            // fallback to flat file
            this.worldRepository = FlatFileStorageRepositoryFactory.INSTANCE.worldRepository();
        }
        return this.worldRepository;
    }

    @Override @Nullable
    public IStorageRepository<GeneralQueryObject, GeneralDataObject> getGeneralRepository() {
        if (this.generalRepository == null) {
            // fallback to flat file
            this.generalRepository = FlatFileStorageRepositoryFactory.INSTANCE.generalRepository();
        }
        return this.generalRepository;
    }

    @Override
    public void onReload() throws Exception {
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
