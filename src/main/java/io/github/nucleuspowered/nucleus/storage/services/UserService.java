/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;

public class UserService extends AbstractKeyedService<UserQueryObject, UserDataObject> {

    @Override
    IStorageRepository<UserQueryObject, UserDataObject> getRepository() {
        return Nucleus.getNucleus().getStorageManager().getUserRepository();
    }
}
