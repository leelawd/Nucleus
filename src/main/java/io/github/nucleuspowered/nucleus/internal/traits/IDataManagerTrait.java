package io.github.nucleuspowered.nucleus.internal.traits;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;
import io.github.nucleuspowered.storage.services.transients.ITransientService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IDataManagerTrait {

    default CompletableFuture<UserDataObject> getOrCreateUser(UUID uuid) {
        return Nucleus.getNucleus().getStorageManager().getUserService().getOrNew(uuid);
    }

    default CompletableFuture<Optional<UserDataObject>> getUser(UUID uuid) {
        return Nucleus.getNucleus().getStorageManager().getUserService().get(uuid);
    }

    default <T extends ITransientDataModule> Optional<T> getOrCreateUserTransient(UUID uuid, Class<T> module) {
        return getOrCreateTransient(Nucleus.getNucleus().getStorageManager().getUserTransientService(), uuid, module);
    }

    default <T extends ITransientDataModule> Optional<T> getOrCreateWorldTransient(UUID uuid, Class<T> module) {
        return getOrCreateTransient(Nucleus.getNucleus().getStorageManager().getWorldTransientService(), uuid, module);
    }

    default <T extends ITransientDataModule> Optional<T> getOrCreateGeneralTransient(Class<T> module) {
        try {
            return Optional.of(Nucleus.getNucleus().getStorageManager().getGeneralTransientService().getOrCreate(module));
        } catch (Exception e) {
            Nucleus.getNucleus().getLogger().error("Could not construct transient", e);
            return Optional.empty();
        }
    }

    default <T extends ITransientDataModule> Optional<T> getOrCreateTransient(ITransientService.Keyed<UUID, ITransientDataModule> service,
            UUID uuid,
            Class<T> module) {
        try {
            return Optional.of(service.getOrCreate(uuid, module));
        } catch (Exception e) {
            Nucleus.getNucleus().getLogger().error("Could not construct transient", e);
            return Optional.empty();
        }
    }

}
