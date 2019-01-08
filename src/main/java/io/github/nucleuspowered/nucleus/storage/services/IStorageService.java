/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The entry point into the storage system. All storage checks are not dependent on the main thread,
 * that is, they should never use game objects.
 */
public interface IStorageService<D extends AbstractDataObject> {

    interface Single<D extends AbstractDataObject> extends IStorageService<D> {

        CompletableFuture<Optional<D>> get();

        CompletableFuture<Void> save(D value);
    }

    interface Keyed<K, Q extends IQueryObject, D extends AbstractDataObject> extends IStorageService<D> {

        CompletableFuture<Optional<D>> get(K key);

        CompletableFuture<Optional<D>> get(Q query);

        CompletableFuture<Collection<D>> getAll(Q query);

        CompletableFuture<Boolean> exists(K key);

        CompletableFuture<Boolean> exists(Q query);

        CompletableFuture<Integer> count(Q query);

        CompletableFuture<Void> save(K key, D value);

        CompletableFuture<Void> delete(K key);
    }

}
