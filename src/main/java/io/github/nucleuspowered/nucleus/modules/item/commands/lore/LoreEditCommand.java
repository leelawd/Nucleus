/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.item.commands.lore;

import io.github.nucleuspowered.nucleus.internal.annotations.command.Permissions;
import io.github.nucleuspowered.nucleus.internal.annotations.command.RegisterCommand;
import io.github.nucleuspowered.nucleus.internal.command.NucleusParameters;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.annotation.NonnullByDefault;

@Permissions(prefix = "lore", mainOverride = "set")
@NonnullByDefault
@RegisterCommand(value = "edit", subcommandOf = LoreCommand.class)
public class LoreEditCommand extends LoreModifyBaseCommand {
    @Override
    protected CommandResult executeCommand(Player src, CommandContext args, Cause cause) throws Exception {
        return setLore(src, args.<String>getOne(NucleusParameters.Keys.LORE).get(), args.<Integer>getOne(this.loreLine).get(), true);
    }
}
