/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.nucleuspowered.nucleus.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class AbstractKeyedService<Q extends IQueryObject<UUID, Q>, D extends AbstractDataObject>
        implements IStorageService.Keyed<UUID, Q, D> {

    final Cache<UUID, D> cache = Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private final Supplier<IDataAccess<D>> dataAccessSupplier;
    private final Supplier<IStorageRepository.Keyed<UUID, Q>> storageRepositorySupplier;

    AbstractKeyedService(Supplier<IDataAccess<D>> dataAccessSupplier, Supplier<IStorageRepository.Keyed<UUID, Q>> storageRepositorySupplier) {
        this.dataAccessSupplier = Objects.requireNonNull(dataAccessSupplier);
        this.storageRepositorySupplier = Objects.requireNonNull(storageRepositorySupplier);
    }

    @Override
    public IDataAccess<D> getDataAccess() {
        return this.dataAccessSupplier.get();
    }

    @Override
    public IStorageRepository.Keyed<UUID, Q> getStorageRepository() {
        return this.storageRepositorySupplier.get();
    }

    @Override public CompletableFuture<Void> clearCache() {
        this.cache.invalidateAll();
        return ServicesUtil.run(() -> {
            this.storageRepositorySupplier.get().clearCache();
            return null;
        });
    }

    @Override public CompletableFuture<Optional<D>> get(final UUID key) {
        D result = this.cache.getIfPresent(key);
        if (result != null) {
            return CompletableFuture.completedFuture(Optional.of(result));
        }

        return ServicesUtil.run(() -> {
            Optional<D> r = this.storageRepositorySupplier.get().get(key).map(o -> this.dataAccessSupplier.get().fromJsonObject(o));
            r.ifPresent(d -> this.cache.put(key, d));
            return r;
        });
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
