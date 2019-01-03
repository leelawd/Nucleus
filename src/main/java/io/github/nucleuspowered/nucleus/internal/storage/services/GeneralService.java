/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.services;

import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.GeneralQueryObject;

import java.util.Collection;
import java.util.Optional;

public class GeneralService implements IStorageService<Void, GeneralQueryObject, GeneralDataObject> {

    public Optional<GeneralDataObject> get() {
        return get((Void) null);
    }

    @Override public Optional<GeneralDataObject> get(Void key) {
        return Optional.empty();
    }

    @Override public Optional<GeneralDataObject> get(GeneralQueryObject query) {
        return Optional.empty();
    }

    @Override public Collection<GeneralDataObject> getAll(GeneralQueryObject query) {
        return null;
    }

    @Override public boolean exists(Void key) {
        return false;
    }

    @Override public boolean exists(GeneralQueryObject query) {
        return false;
    }

    @Override public int count(GeneralQueryObject query) {
        return 0;
    }

    @Override public void save(Void key) {

    }

    @Override public void saveAllInCache() {

    }

    @Override public void delete(Void key) {

    }
}
