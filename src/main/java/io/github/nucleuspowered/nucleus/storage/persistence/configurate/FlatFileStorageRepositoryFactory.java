package io.github.nucleuspowered.nucleus.storage.persistence.configurate;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepositoryFactory;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;

import java.util.Collection;
import java.util.UUID;

public final class FlatFileStorageRepositoryFactory implements IStorageRepositoryFactory {

    public static final IStorageRepositoryFactory INSTANCE = new FlatFileStorageRepositoryFactory();

    private static final String wd = "worlddata";
    private static final String ud = "userdata";

    private FlatFileStorageRepositoryFactory() {}

    @Override
    public IStorageRepository<UserQueryObject> userRepository() {
        return repository(ud);
    }

    @Override
    public IStorageRepository<WorldQueryObject> worldRepository() {
        return repository(wd);
    }

    private <Q extends IQueryObject.Keyed<UUID>> IStorageRepository<Q> repository(final String p) {
        return new FlatFileStorageRepository.UUIDKeyed<>(query -> {
            if (query.keys().size() == 1) {
                Collection<UUID> uuids = query.keys();
                String uuid = uuids.iterator().next().toString();
                return Nucleus.getNucleus().getDataPath().resolve(p).resolve(uuid.substring(0, 2)).resolve(uuid  + ".json");
            }

            throw new DataQueryException("There must only a key", query);
        },
        uuid -> Nucleus.getNucleus().getDataPath().resolve(p).resolve(uuid.toString().substring(0, 2)).resolve(uuid.toString() + ".json"),
        () -> Nucleus.getNucleus().getDataPath().resolve(p));
    }

    @Override
    public IStorageRepository<GeneralQueryObject> generalRepository() {
        return new FlatFileStorageRepository<>(query -> Nucleus.getNucleus().getDataPath().resolve("general.json"));
    }

    @Override public String getId() {
        return "nucleus:flatfile";
    }

    @Override public String getName() {
        return "Flat File";
    }
}
