/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;

public class WorldService extends AbstractKeyedService<WorldQueryObject, WorldDataObject> {

    @Override IStorageRepository<WorldQueryObject, WorldDataObject> getRepository() {
        return Nucleus.getNucleus().getStorageManager().getWorldRepository();
    }

}
