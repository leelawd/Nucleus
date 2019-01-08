/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractKeyedService<Q extends IQueryObject, D extends AbstractDataObject> implements IStorageService<UUID, Q, D> {

    final Cache<UUID, D> cache = Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    @Override public CompletableFuture<Optional<D>> get(UUID key) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override public CompletableFuture<Optional<D>> get(Q query) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override public CompletableFuture<Collection<D>> getAll(Q query) {
        return null;
    }

    @Override public CompletableFuture<Boolean> exists(UUID key) {
        return CompletableFuture.completedFuture(false);
    }

    @Override public CompletableFuture<Boolean> exists(Q query) {
        return CompletableFuture.completedFuture(false);
    }

    @Override public CompletableFuture<Integer> count(Q query) {
        return CompletableFuture.completedFuture(0);
    }

    @Override public CompletableFuture<Void> save(UUID key, D value) {
        return CompletableFuture.completedFuture(null);
    }

    @Override public CompletableFuture<Void> delete(UUID key) {
        return CompletableFuture.completedFuture(null);
    }
}
