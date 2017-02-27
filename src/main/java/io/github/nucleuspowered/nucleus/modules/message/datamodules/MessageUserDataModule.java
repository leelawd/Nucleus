/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.message.datamodules;

import io.github.nucleuspowered.nucleus.dataservices.modular.DataKey;
import io.github.nucleuspowered.nucleus.dataservices.modular.DataModule;
import io.github.nucleuspowered.nucleus.dataservices.modular.ModularUserService;

public class MessageUserDataModule extends DataModule.ReferenceService<ModularUserService> {

    @DataKey("socialspy")
    private boolean socialspy = false;

    public MessageUserDataModule(ModularUserService modularDataService) {
        super(modularDataService);
    }

    public boolean isSocialSpy() {
        return this.socialspy;
    }

    public void setSocialSpy(boolean socialSpy) {
        this.socialspy = socialSpy;
    }
}
