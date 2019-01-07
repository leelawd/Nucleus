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

public interface IStorageService<K, Q extends IQueryObject<K>, D extends AbstractDataObject> {

    CompletableFuture<Optional<D>> get(K key);

    CompletableFuture<Optional<D>> get(Q query);

    CompletableFuture<Collection<D>> getAll(Q query);

    CompletableFuture<Boolean> exists(K key);

    CompletableFuture<Boolean> exists(Q query);

    CompletableFuture<Integer> count(Q query);

    CompletableFuture<Void> save(K key, D value);

    CompletableFuture<Void> delete(K key);

}
