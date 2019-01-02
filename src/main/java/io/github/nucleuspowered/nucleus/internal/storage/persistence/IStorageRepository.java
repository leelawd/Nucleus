/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.persistence;

import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataDeleteException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataLoadException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataSaveException;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.IQueryObject;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Collection;
import java.util.Optional;

/**
 * Base class for interfacing with storage engines
 *
 * @param <Q> The query object
 * @param <R> The result object
 */
public interface IStorageRepository<Q extends IQueryObject, R extends AbstractDataObject> {

    /**
     * Whether this storage mechanism supports complex queries
     *
     * @return whether the mechanism is supported.
     */
    default boolean supportsNonKeyQueries() {
        return false;
    }

    /**
     * Gets whether an object specified by the {@code query} exists.
     *
     * @param query The query.
     * @return Whether the object exists.
     */
    boolean exists(Q query);

    /**
     * Gets an object based on the {@code query}
     *
     * @param query The query
     * @return The object, if it exists
     */
    Optional<R> get(Q query) throws DataLoadException, DataQueryException;

    /**
     * Gets the objects that satisfy the {@code query}
     *
     * @param query The query
     * @return The objects, if any exist, or an empty collection if {@link #supportsNonKeyQueries()} is {@code false} and the query is more than
     *         just a key.
     */
    Collection<R> getAll(Q query) throws DataLoadException;

    /**
     * Gets the number of objects that satisfies the query.
     *
     * @param query The query
     * @return The number of items that satisfy the query, or -1 if the {@link #supportsNonKeyQueries()} is {@code false} and the query is more than
     *         just a key.
     */
    int count(Q query);

    /**
     * Saves the supplied {@code object} in the position suggested by the suppleid {@code query}
     *
     * @param query The query that indicates the location to store the object in
     * @param object The object to save
     */
    void save(Q query, R object) throws ObjectMappingException, DataSaveException;

    /**
     * Deletes the object at the supplied {@code query}
     *
     * @param query The query
     */
    void delete(Q query) throws DataDeleteException;

}
