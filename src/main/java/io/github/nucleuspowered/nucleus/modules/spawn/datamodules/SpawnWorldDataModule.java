/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.spawn.datamodules;

import com.flowpowered.math.vector.Vector3d;
import io.github.nucleuspowered.nucleus.dataservices.modular.DataModule;
import io.github.nucleuspowered.nucleus.dataservices.modular.ModularWorldService;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.modular.modules.DataKey;

import java.util.Optional;

import javax.annotation.Nullable;

public class SpawnWorldDataModule extends DataModule<ModularWorldService> {

    @Nullable
    @DataKey("spawn-rotation")
    private Vector3d spawnRotation;

    public Optional<Vector3d> getSpawnRotation() {
        return Optional.ofNullable(this.spawnRotation);
    }

    public void setSpawnRotation(@Nullable Vector3d spawnRotation) {
        this.spawnRotation = spawnRotation;
    }
}
