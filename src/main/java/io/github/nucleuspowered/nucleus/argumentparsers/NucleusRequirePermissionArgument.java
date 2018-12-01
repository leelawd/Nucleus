/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers;

import static org.spongepowered.api.util.SpongeApiTranslationHelper.t;

import com.google.common.collect.ImmutableList;
import io.github.nucleuspowered.nucleus.argumentparsers.util.WrappedElement;
import io.github.nucleuspowered.nucleus.internal.traits.PermissionTrait;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.List;

import javax.annotation.Nullable;

public class NucleusRequirePermissionArgument extends WrappedElement implements PermissionTrait {

    private final String permission;

    public NucleusRequirePermissionArgument(CommandElement wrapped, String permission) {
        super(wrapped);
        this.permission = permission;
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        return null;
    }

    @Override
    public void parse(CommandSource source, CommandArgs args, CommandContext context) throws ArgumentParseException {
        if (!hasPermission(source, this.permission)) {
            Text key = getKey();
            throw args.createError(t("You do not have permission to use the %s argument", key != null ? key : t("unknown")));
        }
        getWrappedElement().parse(source, args, context);
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        if (!hasPermission(src, this.permission)) {
            return ImmutableList.of();
        }
        return getWrappedElement().complete(src, args, context);
    }
}
