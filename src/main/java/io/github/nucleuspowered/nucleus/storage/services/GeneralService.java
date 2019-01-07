/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class GeneralService implements IStorageService<Void, GeneralQueryObject, GeneralDataObject> {


    @Override public CompletableFuture<Optional<GeneralDataObject>> get(Void key) {
        return null;
    }

    @Override public CompletableFuture<Optional<GeneralDataObject>> get(GeneralQueryObject query) {
        return null;
    }

    @Override public CompletableFuture<Collection<GeneralDataObject>> getAll(GeneralQueryObject query) {
        return null;
    }

    @Override public CompletableFuture<Boolean> exists(Void key) {
        return null;
    }

    @Override public CompletableFuture<Boolean> exists(GeneralQueryObject query) {
        return null;
    }

    @Override public CompletableFuture<Integer> count(GeneralQueryObject query) {
        return null;
    }

    @Override public CompletableFuture<Void> save(Void key, GeneralDataObject value) {
        return null;
    }

    @Override public CompletableFuture<Void> delete(Void key) {
        return null;
    }
}
