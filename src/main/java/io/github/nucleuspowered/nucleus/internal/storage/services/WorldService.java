/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.services;

import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.WorldQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class WorldService implements IStorageService<UUID, WorldQueryObject, WorldDataObject> {

    @Override public Optional<WorldDataObject> get(UUID key) {
        return Optional.empty();
    }

    @Override public Optional<WorldDataObject> get(WorldQueryObject query) {
        return Optional.empty();
    }

    @Override public Collection<WorldDataObject> getAll(WorldQueryObject query) {
        return null;
    }

    @Override public boolean exists(UUID key) {
        return false;
    }

    @Override public boolean exists(WorldQueryObject query) {
        return false;
    }

    @Override public int count(WorldQueryObject query) {
        return 0;
    }

    @Override public void save(UUID key) {

    }

    @Override public void saveAllInCache() {

    }

    @Override public void delete(UUID key) {

    }
}
