/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

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

    @Nullable IStorageRepository<UserQueryObject> getUserRepository();

    @Nullable IStorageRepository<WorldQueryObject> getWorldRepository();

    @Nullable IStorageRepository<GeneralQueryObject> getGeneralRepository();
}
