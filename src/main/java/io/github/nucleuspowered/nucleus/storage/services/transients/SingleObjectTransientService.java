package io.github.nucleuspowered.nucleus.storage.services.transients;

import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.DataModuleFactory;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;
import io.github.nucleuspowered.storage.services.transients.AbstractTransientSingleService;
import io.github.nucleuspowered.storage.services.transients.ITransientService;

public class SingleObjectTransientService extends AbstractTransientSingleService<ITransientDataModule>
        implements ITransientService.Single<ITransientDataModule> {

    @Override
    public <T extends ITransientDataModule> T tryCreate(Class<T> module) throws Exception {
        return DataModuleFactory.getTransientForce(module);
    }
}
