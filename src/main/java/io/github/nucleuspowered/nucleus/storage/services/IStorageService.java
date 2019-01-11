/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.services;

import io.github.nucleuspowered.nucleus.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.storage.queryobjects.IQueryObject;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

/**
 * The entry point into the storage system. All storage checks are not dependent on the main thread,
 * that is, they should never use game objects.
 */
public interface IStorageService<D extends AbstractDataObject> {

    /**
     * Provides a hint to the storage engine that anything that has been cached
     * should be cleared. Implementors may not do anything.
     */
    CompletableFuture<Void> clearCache();

    /**
     * A service where there is one data point.
     *
     * @param <D> The data object type
     */
    interface Single<D extends AbstractDataObject> extends IStorageService<D> {

        /**
         * Gets the data.
         *
         * @return A {@link CompletableFuture} that contains the data, if it exists.
         */
        CompletableFuture<Optional<D>> get();

        /**
         * Save the data.
         *
         * @param value The data to save.
         * @return A {@link CompletableFuture} that may contain an exception.
         */
        CompletableFuture<Void> save(@Nonnull D value);
    }

    /**
     * A service where multiple data objects may be retured based on a query.
     *
     * <p>Note that while a query object may be provided, the storage engine
     * backing the service <em>may not</em> support them. An exception will be
     * returned in this scenario.</p>
     *
     * <p>Users should check {@link #supportsNonPrimaryKeyQueries()} before using
     * query objects.</p>
     *
     * @param <K> The primary key type
     * @param <Q> The {@link IQueryObject} that can contain query parameters
     * @param <D> The {@link AbstractDataObject} that this service deals with.
     */
    interface Keyed<K, Q extends IQueryObject, D extends AbstractDataObject> extends IStorageService<D> {

        /**
         * Whether the backing storage engine supports queries that is not simply
         * the primary key.
         *
         * @return true if non-primary key queries are supported.
         */
        default boolean supportsNonPrimaryKeyQueries() {
            return false;
        }

        /**
         * Gets the object based on the provided key, if it exists.
         *
         * @param key The key
         * @return The object, if it exists
         */
        CompletableFuture<Optional<D>> get(@Nonnull K key);

        /**
         * Gets an object based on the supplied query, if one can be uniquely identified.
         *
         * <p>If {@link #supportsNonPrimaryKeyQueries()} and {@link Q#restrictedToKeys()} are both false,
         * the future will contain an error.</p>
         *
         * @param query The query
         * @return The {@link CompletableFuture} containing the result, or an exception.
         */
        CompletableFuture<Optional<D>> get(@Nonnull Q query);

        /**
         * Gets all objects that match the specified query.
         *
         * <p>If {@link #supportsNonPrimaryKeyQueries()} and {@link Q#restrictedToKeys()} are both false,
         * the future will contain an error.</p>
         *
         * @param query The query
         * @return The {@link CompletableFuture} containing the results, if any
         */
        CompletableFuture<Collection<D>> getAll(@Nonnull Q query);

        /**
         * Gets whether the object with the associated key exists.
         *
         * @param key The key
         * @return The {@link CompletableFuture} containing the whether the object exists
         */
        CompletableFuture<Boolean> exists(@Nonnull K key);

        /**
         * Gets whether at least one result can be returned by the associated query.
         *
         * <p>If {@link #supportsNonPrimaryKeyQueries()} and {@link Q#restrictedToKeys()} are both false,
         * the future will contain an error.</p>
         *
         * @param query The query
         * @return The {@link CompletableFuture} containing the whether an object exists
         */
        default CompletableFuture<Boolean> exists(@Nonnull Q query) {
            return count(query).thenApply(x -> x > 0);
        }

        /**
         * Gets the number of results can be returned by the associated query.
         *
         * <p>If {@link #supportsNonPrimaryKeyQueries()} and {@link Q#restrictedToKeys()} are both false,
         * the future will contain an error.</p>
         *
         * @param query The query
         * @return The {@link CompletableFuture} containing the count
         */
        CompletableFuture<Integer> count(@Nonnull Q query);

        /**
         * Saves an object of type {@link D} against the primary key of type {@link K}.
         *
         * @param key The key to save the object under
         * @param value The value of the object
         * @return A {@link CompletableFuture} that will contain an exception if there was a failure
         */
        CompletableFuture<Void> save(@Nonnull K key, @Nonnull D value);

        /**
         * Deletes the object associated with the key {@link K}.
         *
         * @param key The key to delete the object for
         * @return A {@link CompletableFuture} that will contain an exception if there was a failure
         */
        CompletableFuture<Void> delete(@Nonnull K key);
    }

}
