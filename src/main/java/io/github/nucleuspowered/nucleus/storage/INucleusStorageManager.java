/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IUserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IWorldQueryObject;
import io.github.nucleuspowered.nucleus.storage.services.persistent.IGeneralDataService;
import io.github.nucleuspowered.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;
import io.github.nucleuspowered.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.storage.services.storage.IStorageService;
import io.github.nucleuspowered.storage.services.transients.ITransientService;

import java.util.UUID;

import javax.annotation.Nullable;

public interface INucleusStorageManager {

    IGeneralDataService getGeneralService();

    IStorageService.Keyed<UUID, IUserQueryObject, UserDataObject> getUserService();

    IStorageService.Keyed<UUID, IWorldQueryObject, WorldDataObject> getWorldService();

    IDataAccess<UserDataObject> getUserDataAccess();

    IDataAccess<WorldDataObject> getWorldDataAccess();

    IDataAccess<GeneralDataObject> getGeneralDataAccess();

    @Nullable IStorageRepository.Keyed<UUID, IUserQueryObject> getUserRepository();

    @Nullable IStorageRepository.Keyed<UUID, IWorldQueryObject> getWorldRepository();

    @Nullable IStorageRepository.Single getGeneralRepository();

    ITransientService.Single<ITransientDataModule> getGeneralTransientService();

    ITransientService.Keyed<UUID, ITransientDataModule> getUserTransientService();

    ITransientService.Keyed<UUID, ITransientDataModule> getWorldTransientService();

}
