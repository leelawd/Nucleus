/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.queryobjects;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractQueryObject<K> implements IQueryObject<K> {

    private final Map<QueryKey<?>, List<?>> queryKeyObjectMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void addConstraint(QueryKey<T> key, T value) {
        this.queryKeyObjectMap.compute(key, (k, oldValue) -> {
            if (oldValue == null) {
                oldValue = k.createList();
            }

            ((List<T>) oldValue).add(value);
            return oldValue;
        });
    }

    @Override
    public Map<QueryKey<?>, List<?>> queries() {
        return ImmutableMap.copyOf(this.queryKeyObjectMap);
    }
}
