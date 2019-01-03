/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.services;

import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.IQueryObject;

import java.util.Collection;
import java.util.Optional;

public interface IStorageService<K, Q extends IQueryObject<K>, D extends AbstractDataObject> {

    Optional<D> get(K key);

    Optional<D> get(Q query);

    Collection<D> getAll(Q query);

    boolean exists(K key);

    boolean exists(Q query);

    int count(Q query);

    void save(K key);

    void saveAllInCache();

    void delete(K key);

}
