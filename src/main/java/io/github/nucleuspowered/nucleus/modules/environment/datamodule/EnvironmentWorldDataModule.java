/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.environment.datamodule;

import io.github.nucleuspowered.nucleus.dataservices.modular.DataModule;
import io.github.nucleuspowered.nucleus.dataservices.modular.ModularWorldService;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.modules.DataKey;

public class EnvironmentWorldDataModule extends DataModule<ModularWorldService> {

    @DataKey("lock-weather")
    private boolean lockWeather = false;

    public boolean isLockWeather() {
        return this.lockWeather;
    }

    public void setLockWeather(boolean lockWeather) {
        this.lockWeather = lockWeather;
    }
}
