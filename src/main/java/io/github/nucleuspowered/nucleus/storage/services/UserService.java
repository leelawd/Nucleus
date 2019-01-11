/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IUserQueryObject;

import java.util.UUID;
import java.util.function.Supplier;

public class UserService extends AbstractKeyedService<IUserQueryObject, UserDataObject> {

    public UserService(Supplier<IDataAccess<UserDataObject>> dataAccessSupplier,
            Supplier<IStorageRepository.Keyed<UUID, IUserQueryObject>> storageRepositorySupplier) {
        super(dataAccessSupplier, storageRepositorySupplier);
    }
}
