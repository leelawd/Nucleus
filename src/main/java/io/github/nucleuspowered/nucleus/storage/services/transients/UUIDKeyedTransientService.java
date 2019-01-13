package io.github.nucleuspowered.nucleus.storage.services.transients;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.DataModuleFactory;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;
import io.github.nucleuspowered.storage.services.transients.AbstractTransientKeyedService;
import io.github.nucleuspowered.storage.services.transients.ITransientService;

import java.util.UUID;

public class UUIDKeyedTransientService extends AbstractTransientKeyedService<UUID, ITransientDataModule>
        implements ITransientService.Keyed<UUID, ITransientDataModule> {

    @Override
    public <T extends ITransientDataModule> T tryCreate(Class<T> module) throws Exception {
        return DataModuleFactory.getTransientForce(module);
    }
}
