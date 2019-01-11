/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.util.ThrownSupplier;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.CompletableFuture;

public class ServicesUtil {

    static <R> CompletableFuture<R> run(ThrownSupplier<R, Exception> taskConsumer) {
        CompletableFuture<R> future = new CompletableFuture<>();
        Task.builder().async().execute(t -> {
            try {
                future.complete(taskConsumer.get());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).submit(Nucleus.getNucleus());
        return future;
    }
}
