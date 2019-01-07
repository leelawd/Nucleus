package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;

import javax.annotation.Nullable;

public interface IStorageManager {

    GeneralService getGeneralService();

    UserService getUserService();

    WorldService getWorldService();

    @Nullable IStorageRepository<UserQueryObject, UserDataObject> getUserRepository();

    @Nullable IStorageRepository<WorldQueryObject, WorldDataObject> getWorldRepository();

    @Nullable IStorageRepository<GeneralQueryObject, GeneralDataObject> getGeneralRepository();
}
