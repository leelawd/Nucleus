/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services.persistent;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.storage.dataaccess.IDataAccess;
import io.github.nucleuspowered.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.storage.services.ServicesUtil;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public class GeneralService implements IGeneralDataService {

    private final Supplier<IStorageRepository.Single> storageRepository;
    private final Supplier<IDataAccess<GeneralDataObject>> dataAccess;

    private GeneralDataObject cached = null;

    public GeneralService(Supplier<IDataAccess<GeneralDataObject>> dataAccess, Supplier<IStorageRepository.Single> storageRepository) {
        this.storageRepository = storageRepository;
        this.dataAccess = dataAccess;
    }

    @Override
    public IStorageRepository.Single getStorageRepository() {
        return this.storageRepository.get();
    }

    @Override
    public IDataAccess<GeneralDataObject> getDataAccess() {
        return this.dataAccess.get();
    }

    @Override
    public Optional<GeneralDataObject> getCached() {
        return Optional.ofNullable(this.cached);
    }

    @Override
    public CompletableFuture<Optional<GeneralDataObject>> get() {
        if (this.cached != null) {
            return CompletableFuture.completedFuture(Optional.of(this.cached));
        }

        return ServicesUtil.run(() -> {
            Optional<GeneralDataObject> gdo = getStorageRepository().get().map(getDataAccess()::fromJsonObject);
            gdo.ifPresent(x -> this.cached = x);
            return gdo;
        });
    }

    @Override
    public CompletableFuture<GeneralDataObject> getOrNew() {
        CompletableFuture<GeneralDataObject> d = IGeneralDataService.super.getOrNew();
        d.whenComplete((r, x) -> {
            if (r != null) {
                this.cached = r;
            }
        });
        return d;
    }

    @Override public CompletableFuture<Void> save(@Nonnull GeneralDataObject value) {
        return ServicesUtil.run(() -> {
            getStorageRepository().save(getDataAccess().toJsonObject(value));
            this.cached = value;
            return null;
        });
    }

    @Override public CompletableFuture<Void> clearCache() {
        this.cached = null;
        if (getStorageRepository().hasCache()) {
            return ServicesUtil.run(() -> {
                getStorageRepository().clearCache();
                return null;
            });
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }
}
