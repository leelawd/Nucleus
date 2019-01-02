/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.persistence.configurate;

import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.WorldQueryObject;

import java.util.Collection;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class FlatFileStorageRepositories {

    public static final FlatFileStorageRepository<UserQueryObject, UserDataObject> USERS =
            new FlatFileStorageRepository<>(query -> {
                if (query.keys().size() == 1) {
                    Collection<UUID> uuids = query.keys();
                    String uuid = uuids.iterator().next().toString();
                    return Nucleus.getNucleus().getDataPath().resolve("userdata").resolve(uuid.substring(0, 2)).resolve(uuid);
                }

                throw new DataQueryException("There must only a key", query);
            }, TypeToken.of(UserDataObject.class));

    public static final FlatFileStorageRepository<WorldQueryObject, WorldDataObject> WORLD =
            new FlatFileStorageRepository<>(query -> {
                if (query.keys().size() == 1) {
                    Collection<UUID> uuids = query.keys();
                    String uuid = uuids.iterator().next().toString();
                    return Nucleus.getNucleus().getDataPath().resolve("worlddata").resolve(uuid.substring(0, 2)).resolve(uuid);
                }

                throw new DataQueryException("There must only a key", query);
            }, TypeToken.of(WorldDataObject.class));

    public static final FlatFileStorageRepository<GeneralQueryObject, GeneralDataObject> GENERAL =
            new FlatFileStorageRepository<>(query -> Nucleus.getNucleus().getDataPath().resolve("general.json"), TypeToken.of(GeneralDataObject.class));

}
