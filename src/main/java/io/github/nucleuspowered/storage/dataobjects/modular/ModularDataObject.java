/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.storage.dataobjects.modular;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.dataservices.modular.DataKey;
import io.github.nucleuspowered.storage.dataobjects.AbstractConfigurateBackedDataObject;
import io.github.nucleuspowered.storage.dataobjects.modules.IDataModule;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ModularDataObject<DM extends IDataModule, TM extends ITransientDataModule>
        extends AbstractConfigurateBackedDataObject {

    private static final Map<Class<? extends IDataModule>, List<FieldData>> moduleFieldData = new HashMap<>();

    private final Map<Class<? extends DM>, DM> cached = new HashMap<>();
    private final Map<Class<? extends TM>, TM> transientCache = new HashMap<>();

    private final Object lockingObject = new Object();

    @Override
    public ConfigurationNode getBackingNode() {
        updateBackingNode();
        return super.getBackingNode();
    }

    @Override
    public void setBackingNode(ConfigurationNode node) {
        setBackingNode(node, true);
    }

    public void setBackingNode(ConfigurationNode node, boolean clearCache) {
        super.setBackingNode(node);
        if (clearCache) {
            this.cached.clear(); // remove any cached data - resetting
        }
    }

    @SuppressWarnings("unchecked")
    public void updateBackingNode() {
        if (!this.cached.isEmpty() || !(this.backingNode.isVirtual() || this.backingNode.getValue() == null)) {
            ImmutableMap.copyOf(this.cached).forEach(this::populateNodeFuzzy);
        }
    }

    private static synchronized void initModuleMetadata(Class<? extends IDataModule> moduleClass) {
        // Get the fields.
        List<Field> fields = Arrays.stream(moduleClass.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(DataKey.class))
                .collect(Collectors.toList());

        fields.forEach(x -> x.setAccessible(true));
        moduleFieldData.put(
                moduleClass,
                fields.stream()
                        .map(x -> new FieldData(
                                x.getAnnotation(DataKey.class).value(),
                                TypeToken.of(x.getGenericType()),
                                x))
                        .collect(ImmutableList.toImmutableList())
        );
    }

    @SuppressWarnings("unchecked")
    public final <T extends TM> T getTransient(Class<T> module) {
        if (this.transientCache.containsKey(module)) {
            return (T) this.transientCache.get(module);
        }

        try {

            T dm;
            Optional<T> m = tryGetTransient(module);
            if (m.isPresent()) {
                dm = m.get();
            } else {
                Nucleus.getNucleus().getLogger()
                        .warn("Attempting to construct " + module.getSimpleName() + " by reflection. Please add this to the factory.");
                dm = module.newInstance();
            }

            setTransient(dm);
            return dm;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected abstract <T extends TM> Optional<T> tryGetTransient(Class<T> module);

    @SuppressWarnings({"unchecked"})
    public final <T extends DM> T get(Class<T> module) {
        synchronized (this.lockingObject) {
            if (this.cached.containsKey(module)) {
                return (T) this.cached.get(module);
            }

            if (!moduleFieldData.containsKey(module)) {
                initModuleMetadata(module);
            }

            try {
                Optional<T> m = tryGet(module);
                T dm;
                if (m.isPresent()) {
                    dm = m.get();
                } else {
                    Nucleus.getNucleus().getLogger()
                            .warn("Attempting to construct " + module.getSimpleName() + " by reflection. Please add this to the factory.");

                    if (IDataModule.Identifiable.class.isAssignableFrom(module)) {
                        Constructor s = module.getDeclaredConstructor(this.getClass());
                        s.setAccessible(true);
                        dm = (T) s.newInstance(this);
                    } else {
                        dm = module.newInstance();
                    }
                }

                populateModule(module, dm);
                set(dm);
                return dm;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract <T extends DM> Optional<T> tryGet(Class<T> module);

    public <T extends DM> void set(T dataModule) {
        synchronized (this.lockingObject) {
            this.cached.put((Class<T>) dataModule.getClass(), dataModule);
        }
    }

    private <T extends TM> void setTransient(T dataModule) {
        this.transientCache.put((Class<T>) dataModule.getClass(), dataModule);
    }

    private <T extends DM> void populateModule(Class<T> clazz, T module) {
        // get the fields
        List<FieldData> fieldData = moduleFieldData.get(clazz);
        for (FieldData fieldDatum : fieldData) { // don't look at me, this was autogenned by IntelliJ.
            // get the data from the node
            try {
                Object obj = this.backingNode.getNode(fieldDatum.path).getValue(fieldDatum.clazz);
                fieldDatum.field.set(module, obj);
            } catch (ObjectMappingException | IllegalAccessException e) {
                // couldn't parse, friendly message and be on our way.
                Nucleus.getNucleus().getLogger().warn("Could not load " + fieldDatum.path + ", falling back to defaults.", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends DM> void populateNodeFuzzy(Class<T> clazz, Object module) {
        populateNode(clazz, (T) module);
    }

    private <T extends DM> void populateNode(Class<T> clazz, T module) {
        // get the fields
        List<FieldData> fieldData = moduleFieldData.get(clazz);
        for (FieldData fieldDatum : fieldData) { // don't look at me, this was autogenned by IntelliJ.
            // get the data from the node
            try {
                // needed to ensure type compliance.
                saveFieldData(module, fieldDatum.clazz, fieldDatum.field, fieldDatum.path);
            } catch (ObjectMappingException | IllegalAccessException e) {
                // couldn't parse, friendly message and be on our way.
                Nucleus.getNucleus().getLogger().warn("Could not save " + fieldDatum.path + ".", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <R> void saveFieldData(Object module, TypeToken<R> typeToken, Field field, String path) throws IllegalAccessException,
            ObjectMappingException {
        R ret = (R) field.get(module);
        this.backingNode.getNode(path).setValue(typeToken, ret);
    }

    public static class FieldData {

        private final String path;
        private final TypeToken<?> clazz;
        private final Field field;

        private FieldData(String path, TypeToken<?> clazz, Field field) {
            this.path = path;
            this.clazz = clazz;
            this.field = field;
        }

        public String getPath() {
            return path;
        }

        public TypeToken<?> getClazz() {
            return clazz;
        }

        public Field getField() {
            return field;
        }
    }
}
