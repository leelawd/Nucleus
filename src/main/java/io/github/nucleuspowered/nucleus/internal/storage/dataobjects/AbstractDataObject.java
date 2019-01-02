/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.dataobjects;

import ninja.leaping.configurate.ConfigurationNode;

/**
 * Indicates that this is the basic object for saving data with
 */
public abstract class AbstractDataObject {

    protected ConfigurationNode backingNode;

    public final boolean isAttached() {
        return this.backingNode != null;
    }

    public ConfigurationNode getBackingNode() {
        return this.backingNode.copy();
    }

    public void setBackingNode(ConfigurationNode node) {
        this.backingNode = node;
    }

}
