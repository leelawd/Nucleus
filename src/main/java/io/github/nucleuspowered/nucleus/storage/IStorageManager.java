package io.github.nucleuspowered.nucleus.storage;

import io.github.nucleuspowered.nucleus.storage.services.GeneralService;
import io.github.nucleuspowered.nucleus.storage.services.UserService;
import io.github.nucleuspowered.nucleus.storage.services.WorldService;

public interface IStorageManager {

    GeneralService getGeneralService();

    UserService getUserService();

    WorldService getWorldService();

}
