/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class GeneralService implements IStorageService.Single<GeneralDataObject> {

    @Override public CompletableFuture<Optional<GeneralDataObject>> get() {
        return null;
    }

    @Override public CompletableFuture<Void> save(GeneralDataObject value) {
        return null;
    }
}
