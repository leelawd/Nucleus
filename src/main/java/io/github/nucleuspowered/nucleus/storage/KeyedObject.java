/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage;

import java.util.Optional;

import javax.annotation.Nullable;

public class KeyedObject<K, O> {

    private final K key;
    @Nullable private final O value;

    public KeyedObject(K key, @Nullable O value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public Optional<O> getValue() {
        return Optional.ofNullable(this.value);
    }

}
