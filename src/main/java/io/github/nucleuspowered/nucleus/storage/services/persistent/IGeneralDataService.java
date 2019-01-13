package io.github.nucleuspowered.nucleus.storage.services.persistent;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modular.GeneralDataObject;
import io.github.nucleuspowered.storage.services.storage.IStorageService;

import java.util.Optional;

public interface IGeneralDataService extends IStorageService.Single<GeneralDataObject> {

    Optional<GeneralDataObject> getCached();
}
