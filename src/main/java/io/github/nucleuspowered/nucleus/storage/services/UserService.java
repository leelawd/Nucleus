/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class UserService implements IStorageService<UUID, UserQueryObject, UserDataObject> {

    @Override public Optional<UserDataObject> get(UUID key) {
        return Optional.empty();
    }

    @Override public Optional<UserDataObject> get(UserQueryObject query) {
        return Optional.empty();
    }

    @Override public Collection<UserDataObject> getAll(UserQueryObject query) {
        return null;
    }

    @Override public boolean exists(UUID key) {
        return false;
    }

    @Override public boolean exists(UserQueryObject query) {
        return false;
    }

    @Override public int count(UserQueryObject query) {
        return 0;
    }

    @Override public void save(UUID key) {

    }

    @Override public void saveAllInCache() {

    }

    @Override public void delete(UUID key) {

    }
}
