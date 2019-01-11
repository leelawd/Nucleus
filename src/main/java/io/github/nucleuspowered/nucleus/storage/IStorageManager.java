/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IUserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IWorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;

import java.util.UUID;

import javax.annotation.Nullable;

public interface IStorageManager {

    GeneralService getGeneralService();

    UserService getUserService();

    WorldService getWorldService();

    IDataAccess<UserDataObject> getUserDataAccess();

    IDataAccess<WorldDataObject> getWorldDataAccess();

    IDataAccess<GeneralDataObject> getGeneralDataAccess();

    @Nullable IStorageRepository.Keyed<UUID, IUserQueryObject> getUserRepository();

    @Nullable IStorageRepository.Keyed<UUID, IWorldQueryObject> getWorldRepository();

    @Nullable IStorageRepository.Single getGeneralRepository();
}
