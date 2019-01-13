/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.storage.dataobjects.modules;

import com.google.common.collect.ImmutableMap;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.modules.back.datamodules.BackUserTransientModule;
import io.github.nucleuspowered.nucleus.modules.commandspy.datamodules.CommandSpyUserDataModule;
import io.github.nucleuspowered.nucleus.modules.core.datamodules.CoreUserDataModule;
import io.github.nucleuspowered.nucleus.modules.environment.datamodule.EnvironmentWorldDataModule;
import io.github.nucleuspowered.nucleus.modules.fly.datamodules.FlyUserDataModule;
import io.github.nucleuspowered.nucleus.modules.freezeplayer.datamodules.FreezePlayerUserDataModule;
import io.github.nucleuspowered.nucleus.modules.home.datamodules.HomeUserDataModule;
import io.github.nucleuspowered.nucleus.modules.ignore.datamodules.IgnoreUserDataModule;
import io.github.nucleuspowered.nucleus.modules.invulnerability.datamodules.InvulnerabilityUserDataModule;
import io.github.nucleuspowered.nucleus.modules.jail.datamodules.JailGeneralDataModule;
import io.github.nucleuspowered.nucleus.modules.jail.datamodules.JailUserDataModule;
import io.github.nucleuspowered.nucleus.modules.kit.datamodules.KitUserDataModule;
import io.github.nucleuspowered.nucleus.modules.mail.datamodules.MailUserDataModule;
import io.github.nucleuspowered.nucleus.modules.message.datamodules.MessageUserDataModule;
import io.github.nucleuspowered.nucleus.modules.mute.datamodules.MuteUserDataModule;
import io.github.nucleuspowered.nucleus.modules.nickname.datamodules.NicknameUserDataModule;
import io.github.nucleuspowered.nucleus.modules.note.datamodules.NoteUserDataModule;
import io.github.nucleuspowered.nucleus.modules.powertool.datamodules.PowertoolUserDataModule;
import io.github.nucleuspowered.nucleus.modules.serverlist.datamodules.ServerListGeneralDataModule;
import io.github.nucleuspowered.nucleus.modules.spawn.datamodules.SpawnGeneralDataModule;
import io.github.nucleuspowered.nucleus.modules.spawn.datamodules.SpawnWorldDataModule;
import io.github.nucleuspowered.nucleus.modules.staffchat.datamodules.StaffChatTransientModule;
import io.github.nucleuspowered.nucleus.modules.teleport.datamodules.TeleportUserDataModule;
import io.github.nucleuspowered.nucleus.modules.vanish.datamodules.VanishUserDataModule;
import io.github.nucleuspowered.nucleus.modules.warn.datamodules.WarnUserDataModule;
import io.github.nucleuspowered.nucleus.modules.warp.datamodules.WarpGeneralDataModule;
import io.github.nucleuspowered.nucleus.modules.world.datamodules.WorldgenWorldDataModule;
import io.github.nucleuspowered.storage.dataobjects.modules.IDataModule;
import io.github.nucleuspowered.storage.dataobjects.modules.ITransientDataModule;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class DataModuleFactory {

    private DataModuleFactory() {}

    private static final Map<Class<? extends ITransientDataModule>, Supplier<? extends ITransientDataModule>> TRANSIENT_MODULES;
    private static final Map<Class<? extends IGeneralDataModule>, Supplier<? extends IGeneralDataModule>> GENERAL_MODULES;
    private static final Map<Class<? extends IWorldDataModule>, Supplier<? extends IWorldDataModule>> WORLD_MODULES;
    private static final Map<Class<? extends IUserDataModule>, Supplier<? extends IUserDataModule>> USER_MODULES;

    static {
        TRANSIENT_MODULES = ImmutableMap.<Class<? extends ITransientDataModule>, Supplier<? extends ITransientDataModule>>builder()
                .put(BackUserTransientModule.class, BackUserTransientModule::new)
                .put(StaffChatTransientModule.class, StaffChatTransientModule::new)
                .build();

        GENERAL_MODULES = ImmutableMap.<Class<? extends IGeneralDataModule>, Supplier<? extends IGeneralDataModule>>builder()
                .put(JailGeneralDataModule.class, JailGeneralDataModule::new)
                .put(SpawnGeneralDataModule.class, SpawnGeneralDataModule::new)
                .put(WarpGeneralDataModule.class, WarpGeneralDataModule::new)
                .put(ServerListGeneralDataModule.class, ServerListGeneralDataModule::new)
                .build();

        WORLD_MODULES = ImmutableMap.<Class<? extends IWorldDataModule>, Supplier<? extends IWorldDataModule>>builder()
                .put(EnvironmentWorldDataModule.class, EnvironmentWorldDataModule::new)
                .put(SpawnWorldDataModule.class, SpawnWorldDataModule::new)
                .put(WorldgenWorldDataModule.class, WorldgenWorldDataModule::new)
                .build();

        USER_MODULES = ImmutableMap.<Class<? extends IUserDataModule>, Supplier<? extends IUserDataModule>>builder()
                .put(CommandSpyUserDataModule.class, CommandSpyUserDataModule::new)
                .put(CoreUserDataModule.class, CoreUserDataModule::new)
                .put(FlyUserDataModule.class, FlyUserDataModule::new)
                .put(FreezePlayerUserDataModule.class, FreezePlayerUserDataModule::new)
                .put(HomeUserDataModule.class, HomeUserDataModule::new)
                .put(IgnoreUserDataModule.class, IgnoreUserDataModule::new)
                .put(JailUserDataModule.class, JailUserDataModule::new)
                .put(KitUserDataModule.class, KitUserDataModule::new)
                .put(MailUserDataModule.class, MailUserDataModule::new)
                .put(MessageUserDataModule.class, MessageUserDataModule::new)
                .put(InvulnerabilityUserDataModule.class, InvulnerabilityUserDataModule::new)
                .put(MuteUserDataModule.class, MuteUserDataModule::new)
                .put(NicknameUserDataModule.class, NicknameUserDataModule::new)
                .put(NoteUserDataModule.class, NoteUserDataModule::new)
                .put(PowertoolUserDataModule.class, PowertoolUserDataModule::new)
                .put(TeleportUserDataModule.class, TeleportUserDataModule::new)
                .put(VanishUserDataModule.class, VanishUserDataModule::new)
                .put(WarnUserDataModule.class, WarnUserDataModule::new)
                .build();
    }

    public static <T extends IDataModule> T getForce(Class<T> module) throws Exception {
        Optional<T> o = get(module);
        if (o.isPresent()) {
            return o.get();
        }

        return reflectivelyCreate(module);
    }

    @SuppressWarnings("unchecked")
    public static <T extends IDataModule> Optional<T> get(Class<T> module) {
        if (IUserDataModule.class.isAssignableFrom(module)) {
            if (USER_MODULES.containsKey(module)) {
                return Optional.of((T) USER_MODULES.get(module).get());
            }
        } else if (IWorldDataModule.class.isAssignableFrom(module)) {
            if (WORLD_MODULES.containsKey(module)) {
                return Optional.of((T) WORLD_MODULES.get(module).get());
            }
        } else if (IGeneralDataModule.class.isAssignableFrom(module)) {
            if (GENERAL_MODULES.containsKey(module)) {
                return Optional.of((T) GENERAL_MODULES.get(module).get());
            }
        }

        return Optional.empty();
    }

    public static <T extends ITransientDataModule> T getTransientForce(Class<T> module) throws Exception {
        Optional<T> o = getTransient(module);
        if (o.isPresent()) {
            return o.get();
        }

        return reflectivelyCreate(module);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITransientDataModule> Optional<T> getTransient(Class<T> module) {
        if (TRANSIENT_MODULES.containsKey(module)) {
            return Optional.of((T) TRANSIENT_MODULES.get(module).get());
        }

        return Optional.empty();
    }

    private static <T> T reflectivelyCreate(Class<T> clazz) throws Exception {
        Nucleus.getNucleus().getLogger().warn("{} instance is being created reflectively - this should be added to the data module factory."
                        + "This is a bug in Nucleus.",
                clazz);
        return clazz.newInstance();
    }
}
