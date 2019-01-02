/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.queryobjects;

public class WorldQueryObject extends AbstractUUIDKeyedQueryObject {

    @Override
    public final String objectType() {
        return "worlddata";
    }

}
