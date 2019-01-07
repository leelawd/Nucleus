package io.github.nucleuspowered.nucleus.storage.persistence.configurate;

import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepositoryFactory;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;

import java.util.Collection;
import java.util.UUID;

public final class FlatFileStorageRepositoryFactory implements IStorageRepositoryFactory {

    public static final IStorageRepositoryFactory INSTANCE = new FlatFileStorageRepositoryFactory();

    private FlatFileStorageRepositoryFactory() {}

    @Override
    public IStorageRepository<UserQueryObject, UserDataObject> userRepository() {
        return new FlatFileStorageRepository<>(query -> {
            if (query.keys().size() == 1) {
                Collection<UUID> uuids = query.keys();
                String uuid = uuids.iterator().next().toString();
                return Nucleus.getNucleus().getDataPath().resolve("userdata").resolve(uuid.substring(0, 2)).resolve(uuid);
            }

            throw new DataQueryException("There must only a key", query);
        }, TypeToken.of(UserDataObject.class));
    }

    @Override
    public IStorageRepository<WorldQueryObject, WorldDataObject> worldRepository() {
        return new FlatFileStorageRepository<>(query -> {
            if (query.keys().size() == 1) {
                Collection<UUID> uuids = query.keys();
                String uuid = uuids.iterator().next().toString();
                return Nucleus.getNucleus().getDataPath().resolve("worlddata").resolve(uuid.substring(0, 2)).resolve(uuid);
            }

            throw new DataQueryException("There must only a key", query);
        }, TypeToken.of(WorldDataObject.class));
    }

    @Override
    public IStorageRepository<GeneralQueryObject, GeneralDataObject> generalRepository() {
        return new FlatFileStorageRepository<>(query -> Nucleus.getNucleus().getDataPath().resolve("general.json"), TypeToken.of(GeneralDataObject.class));
    }

    @Override public String getId() {
        return "nucleus:flatfile";
    }

    @Override public String getName() {
        return "Flat File";
    }
}
