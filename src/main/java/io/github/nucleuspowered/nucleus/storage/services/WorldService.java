/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IWorldQueryObject;
import io.github.nucleuspowered.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.storage.services.AbstractKeyedService;

import java.util.UUID;
import java.util.function.Supplier;

public class WorldService extends AbstractKeyedService<IWorldQueryObject, WorldDataObject> {

    public WorldService(Supplier<IDataAccess<WorldDataObject>> dataAccessSupplier,
            Supplier<IStorageRepository.Keyed<UUID, IWorldQueryObject>> storageRepositorySupplier) {
        super(dataAccessSupplier, storageRepositorySupplier);
    }
}
