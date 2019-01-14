package io.github.nucleuspowered.nucleus.internal.traits;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.IUserDataModule;
import io.github.nucleuspowered.nucleus.storage.services.persistent.IGeneralDataService;
import io.github.nucleuspowered.nucleus.util.ThrownConsumer;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface IDataManagerTrait {

    default CompletableFuture<UserDataObject> getOrCreateUser(UUID uuid) {
        return Nucleus.getNucleus().getStorageManager().getUserService().getOrNew(uuid);
    }

    default CompletableFuture<Optional<UserDataObject>> getUser(UUID uuid) {
        return Nucleus.getNucleus().getStorageManager().getUserService().get(uuid);
    }

    default CompletableFuture<Void> saveUser(UUID uuid, UserDataObject object) {
        return Nucleus.getNucleus().getStorageManager().getUserService().save(uuid, object);
    }

    default GeneralDataObject getGeneral() {
        IGeneralDataService gs = Nucleus.getNucleus().getStorageManager().getGeneralService();
        return gs.getCached().orElseGet(() -> gs.getOrNew().join());
    }

    default <T extends IUserDataModule> void saveUserWithDataNoEx(Class<T> module, UUID uuid, Consumer<T> consumer) {
        saveUserWithData(module, uuid, consumer::accept);
    }

    default <T extends IUserDataModule, X extends Throwable> void saveUserWithData(Class<T> module, UUID uuid, ThrownConsumer<T, X> consumer) throws X {
        UserDataObject userDataObject = getOrCreateUser(uuid).join();
        T m = userDataObject.get(module);
        consumer.accept(m);
        userDataObject.set(m);
        saveUser(uuid, userDataObject);
    }
}
