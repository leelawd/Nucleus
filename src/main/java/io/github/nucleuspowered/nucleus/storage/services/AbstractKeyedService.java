/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import io.github.nucleuspowered.nucleus.storage.KeyedObject;
import io.github.nucleuspowered.nucleus.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public abstract class AbstractKeyedService<Q extends IQueryObject<UUID, Q>, D extends AbstractDataObject>
        implements IStorageService.Keyed<UUID, Q, D> {

    private final Cache<UUID, D> cache = Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

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

    @Override public CompletableFuture<Optional<KeyedObject<UUID, D>>> get(@Nonnull final Q query) {
        return ServicesUtil.run(() -> {
            Optional<KeyedObject<UUID, D>> r =
                    this.storageRepositorySupplier.get().get(query).map(o -> o.mapValue(this.dataAccessSupplier.get()::fromJsonObject));
            r.ifPresent(d -> {
                if (d.getValue().isPresent()) {
                    this.cache.put(d.getKey(), d.getValue().get());
                } else {
                    this.cache.invalidate(d.getKey());
                }
            });
            return r;
        });
    }

    @Override public CompletableFuture<Map<UUID, D>> getAll(@Nonnull Q query) {
        return ServicesUtil.run(() -> {
            IDataAccess<D> dataAccess = this.getDataAccess();
            Map<UUID, JsonObject> r = this.storageRepositorySupplier.get().getAll(query);
            Map<UUID, D> res = r.entrySet().stream()
                    .filter(x -> x.getValue() != null)
                    .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, v -> dataAccess.fromJsonObject(v.getValue())));
            res.forEach(this.cache::put);
            return res;
        });
    }

    @Override public CompletableFuture<Boolean> exists(UUID key) {
        return ServicesUtil.run(() -> getStorageRepository().exists(key));
    }

    @Override public CompletableFuture<Integer> count(Q query) {
        return ServicesUtil.run(() -> getStorageRepository().count(query));
    }

    @Override public CompletableFuture<Void> save(final UUID key, final D value) {
        return ServicesUtil.run(() -> {
            getStorageRepository().save(key, getDataAccess().toJsonObject(value));
            this.cache.put(key, value);
            return null;
        });
    }

    @Override public CompletableFuture<Void> delete(UUID key) {
        return ServicesUtil.run(() -> {
            getStorageRepository().delete(key);
            this.cache.invalidate(key);
            return null;
        });
    }



}
