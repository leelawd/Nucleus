/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.queryobjects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractUUIDKeyedQueryObject<T extends IQueryObject<UUID, T>>
        extends AbstractQueryObject<UUID, T> {

    private final Set<UUID> keys = new HashSet<>();

    @Override
    public final Class<UUID> keyType() {
        return UUID.class;
    }

    public AbstractUUIDKeyedQueryObject<T> addKey(UUID uuid) {
        this.keys.add(uuid);
        return this;
    }

    public AbstractUUIDKeyedQueryObject<T> addAllKeys(Collection<? extends UUID> collection) {
        this.keys.addAll(collection);
        return this;
    }

    @Override
    public final Collection<UUID> keys() {
        return Collections.unmodifiableCollection(this.keys);
    }

}
