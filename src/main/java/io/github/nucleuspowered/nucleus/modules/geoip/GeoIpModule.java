/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.geoip;

import io.github.nucleuspowered.nucleus.internal.qsml.module.ConfigurableModule;
import io.github.nucleuspowered.nucleus.modules.geoip.config.GeoIpConfigAdapter;
import uk.co.drnaylor.quickstart.annotations.ModuleData;
import uk.co.drnaylor.quickstart.enums.LoadingStatus;

@ModuleData(id = GeoIpModule.ID, name = "Geo IP", status = LoadingStatus.DISABLED)
public class GeoIpModule extends ConfigurableModule<GeoIpConfigAdapter> {

    public static final String ID = "geo-ip";

    @Override public GeoIpConfigAdapter createAdapter() {
        return new GeoIpConfigAdapter();
    }

}
