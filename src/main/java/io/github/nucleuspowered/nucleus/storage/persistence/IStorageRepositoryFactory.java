package io.github.nucleuspowered.nucleus.storage.persistence;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.UserDataObject;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.WorldDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.GeneralQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.UserQueryObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.WorldQueryObject;
import org.spongepowered.api.CatalogType;

/**
 * Contains methods to create appropriate {@link IStorageRepository}s for user, world and general data objects.
 */
public interface IStorageRepositoryFactory extends CatalogType {

    IStorageRepository<UserQueryObject, UserDataObject> userRepository();

    IStorageRepository<WorldQueryObject, WorldDataObject> worldRepository();

    IStorageRepository<GeneralQueryObject, GeneralDataObject> generalRepository();

}
