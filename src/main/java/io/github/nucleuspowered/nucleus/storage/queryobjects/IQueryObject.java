/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.queryobjects;

import io.github.nucleuspowered.nucleus.storage.persistence.IStorageRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IQueryObject<K> {

    /**
     * Any queries that aren't the primary key.
     *
     * <p>Ensure {@link IStorageRepository#supportsNonKeyQueries()} is true if using this</p>
     *
     * @return
     */
    Map<QueryKey<?>, List<?>> queries();

    /**
     * Adds a constraint to the query
     *
     * @param key The key to add the constraint to
     * @param value The value of the constraint
     * @param <T> The type of the constraint
     */
    <T> void addConstraint(QueryKey<T> key, T value);

    /**
     * The type of object that is to be stored.
     *
     * @return The type of object
     */
    String objectType();

    /**
     * Indicates that the data repository is keyed.
     *
     * @param <K> The type of key
     */
    interface Keyed<K> extends IQueryObject<K> {

        /**
         * The type of primary key
         *
         * @return The class of the key type
         */
        Class<K> keyType();

        /**
         * Keys to restrict to
         *
         * @return The keys to restrict to
         */
        Collection<K> keys();

        /**
         * Whether the query is dependent on keys.
         *
         * @return The keys.
         */
        default boolean restrictedToKeys() {
            return !keys().isEmpty();
        }

    }
}
