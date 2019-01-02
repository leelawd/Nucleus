/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.storage.persistence.configurate;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.configurate.ConfigurateHelper;
import io.github.nucleuspowered.nucleus.internal.storage.dataobjects.AbstractDataObject;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataDeleteException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataLoadException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataQueryException;
import io.github.nucleuspowered.nucleus.internal.storage.exceptions.DataSaveException;
import io.github.nucleuspowered.nucleus.internal.storage.persistence.IStorageRepository;
import io.github.nucleuspowered.nucleus.internal.storage.queryobjects.IQueryObject;
import io.github.nucleuspowered.nucleus.util.ThrownFunction;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nullable;

public class FlatFileStorageRepository<Q extends IQueryObject, R extends AbstractDataObject> implements IStorageRepository<Q, R> {

    private final ThrownFunction<Q, Path, DataQueryException> FILENAME_RESOLVER;
    private final TypeToken<R> DATA_OBJECT_TYPE_TOKEN;

    public FlatFileStorageRepository(ThrownFunction<Q, Path, DataQueryException> filename_resolver, TypeToken<R> data_object_type_token) {
        FILENAME_RESOLVER = filename_resolver;
        DATA_OBJECT_TYPE_TOKEN = data_object_type_token;
    }

    @Override
    public boolean exists(Q query) {
        try {
            return existsInternal(query) != null;
        } catch (DataQueryException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<R> get(Q query) throws DataLoadException {
        Path path;
        try {
            path = existsInternal(query);
        } catch (Exception e) {
            throw new DataLoadException("Query not valid", e);
        }

        if (path != null) {
            try {
                ConfigurationNode node = DataConfigurationLoaderWrapper.INSTANCE.loadFrom(path);
                return Optional.ofNullable(node.getValue(DATA_OBJECT_TYPE_TOKEN));
            } catch (Exception e) {
                throw new DataLoadException("Could not load file at " + path.toAbsolutePath().toString(), e);
            }
        }

        return Optional.empty();
    }

    @Override
    public Collection<R> getAll(Q query) throws DataLoadException {
        return get(query).map(ImmutableSet::of).orElseGet(ImmutableSet::of);
    }

    @Override
    public int count(Q query) {
        return exists(query) ? 1 : 0;
    }

    @Override
    public void save(Q query, R object) throws DataSaveException {
        try {
            ConfigurationNode node = DataConfigurationLoaderWrapper.INSTANCE.createEmptyNode();
            node.setValue(DATA_OBJECT_TYPE_TOKEN, object);
            DataConfigurationLoaderWrapper.INSTANCE.saveTo(FILENAME_RESOLVER.apply(query), node);
        } catch (Exception ex) {
            throw new DataSaveException("Could not save " + query.toString(), ex);
        }
    }

    @Override
    public void delete(Q query) throws DataDeleteException {
        Path filename = null;
        try {
            filename = FILENAME_RESOLVER.apply(query);
        } catch (DataQueryException e) {
            throw new DataDeleteException("Could not find file based on query " + query.toString(), e);
        }

        try {
            Files.delete(filename);
        } catch (IOException e) {
            throw new DataDeleteException("Could not delete " + filename, e);
        }
    }

    @Nullable
    private Path existsInternal(Q query) throws DataQueryException {
        Path path = FILENAME_RESOLVER.apply(query);
        if (Files.exists(FILENAME_RESOLVER.apply(query))) {
            return path;
        }

        return null;
    }

    protected static final class DataConfigurationLoaderWrapper {

        protected static DataConfigurationLoaderWrapper INSTANCE = new DataConfigurationLoaderWrapper();
        private static final Object lockingObject = new Object();

        private DataConfigurationLoaderWrapper() { }

        private final ThreadLocal<Path> path = ThreadLocal.withInitial(() -> null);

        private final GsonConfigurationLoader gcl = GsonConfigurationLoader.builder()
                .setDefaultOptions(ConfigurateHelper.setOptions(ConfigurationOptions.defaults()))
                .setSource(() -> Files.newBufferedReader(this.path.get()))
                .setSink(() -> Files.newBufferedWriter(this.path.get(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
                .build();

        final ConfigurationNode loadFrom(Path path) throws IOException {
            try {
                this.path.set(path);
                synchronized (lockingObject) {
                    return this.gcl.load();
                }
            } finally {
                this.path.remove();
            }
        }

        final void saveTo(Path path, ConfigurationNode node) throws IOException {
            try {
                this.path.set(path);
                synchronized (lockingObject) {
                    this.gcl.save(node);
                }
            } finally {
                this.path.remove();
            }
        }

        final ConfigurationNode createEmptyNode() {
            return this.gcl.createEmptyNode();
        }
    }

}
