/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.queryobjects;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractQueryObject<K, T extends IQueryObject<K, T>> implements IQueryObject<K, T> {

    private final Map<QueryKey<?, T>, List<?>> queryKeyObjectMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <R> void addConstraint(QueryKey<R, T> key, R value) {
        this.queryKeyObjectMap.compute(key, (k, oldValue) -> {
            if (oldValue == null) {
                oldValue = k.createList();
            }

            ((List<R>) oldValue).add(value);
            return oldValue;
        });
    }

    @Override
    public Map<QueryKey<?, T>, List<?>> queries() {
        return ImmutableMap.copyOf(this.queryKeyObjectMap);
    }
}
