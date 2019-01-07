/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.persistence;

import com.google.gson.JsonObject;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataDeleteException;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataLoadException;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.storage.exceptions.DataSaveException;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Base class for interfacing with storage engines
 *
 * @param <Q> The query object
 */
public interface IStorageRepository<Q extends IQueryObject> {

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
    Optional<JsonObject> get(Q query) throws DataLoadException, DataQueryException;

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
    void save(Q query, JsonObject object) throws ObjectMappingException, DataSaveException;

    /**
     * Deletes the object at the supplied {@code query}
     *
     * @param query The query
     */
    void delete(Q query) throws DataDeleteException;

    /**
     * Shutdown the repository, finishing any operations.
     *
     * <p>This will be called in one of three situations:</p>
     * <ul>
     *     <li>The data source has been changed</li>
     *     <li>The game server has been stopped (on the client only)</li>
     *     <li>The game itself is being shutdown</li>
     * </ul>
     *
     * <p>Implementors should note that calling this method is not guaranteed and should
     * consider data consistency accordingly.</p>
     */
    void shutdown();

    /**
     * Interface for repositories that should have unique {@link UUID}s
     *
     * @param <Q> The query object
     */
    interface UUIDKeyed<Q extends IQueryObject.Keyed<UUID>> {

        /**
         * Gets whether an object specified by the {@link UUID} exists.
         *
         * @param uuid The {@link UUID}.
         * @return Whether the object exists.
         */
        boolean exists(UUID uuid);

        /**
         * Gets an object based on the {@code query}
         *
         * @param uuid The {@link UUID}
         * @return The object, if it exists
         */
        Optional<JsonObject> get(UUID uuid) throws DataLoadException, DataQueryException;

        /**
         * Gets all the stored keys
         *
         * @return The objects, if any exist, or an empty collection if {@link #supportsNonKeyQueries()} is {@code false} and the query is more than
         *         just a key.
         */
        Collection<UUID> getAllKeys() throws DataLoadException;

        /**
         * Gets the objects that satisfy the {@code query}
         *
         * @param query The query
         * @return The objects, if any exist, or an empty collection if {@link #supportsNonKeyQueries()} is {@code false} and the query is more than
         *         just a key.
         */
        Collection<UUID> getAllKeys(Q query) throws DataLoadException, DataQueryException;
    }
}
