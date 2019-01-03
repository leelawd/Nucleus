/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.queryobjects;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public abstract class VoidQueryObject implements IQueryObject<Void> {

    @Override
    public Map<QueryKey<?>, List<?>> queries() {
        return ImmutableMap.of();
    }

    @Override
    public <T> void addConstraint(QueryKey<T> key, T value) {
        // noop
    }

}
