/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.fly.datamodules;

import io.github.nucleuspowered.nucleus.dataservices.modular.DataKey;
import io.github.nucleuspowered.nucleus.storage.dataobjects.modules.IUserDataModule;

public class FlyUserDataModule implements IUserDataModule {

    @DataKey("fly")
    private boolean fly = false;

    public boolean isFlyingSafe() {
        return this.fly;
    }

    public void setFlying(boolean fly) {
        this.fly = fly;
    }
}
