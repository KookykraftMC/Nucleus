/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.kit.commands;

import com.google.inject.Inject;
import io.github.nucleuspowered.nucleus.Util;
import io.github.nucleuspowered.nucleus.internal.annotations.*;
import io.github.nucleuspowered.nucleus.internal.command.OldCommandBase;
import io.github.nucleuspowered.nucleus.internal.permissions.SuggestedLevel;
import io.github.nucleuspowered.nucleus.modules.kit.handlers.KitHandler;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * Sets kit items.
 *
 * Command Usage: /kit add Permission: nucleus.kit.add.base
 */
@Permissions(root = "kit", suggestedLevel = SuggestedLevel.ADMIN)
@RegisterCommand(value = {"add"}, subcommandOf = KitCommand.class)
@NoWarmup
@NoCooldown
@NoCost
public class KitAddCommand extends OldCommandBase<Player> {

    @Inject private KitHandler kitConfig;

    private final String name = "name";

    @Override
    public CommandSpec createSpec() {
        return getSpecBuilderBase().description(Text.of("Adds kit."))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of(name)))).build();
    }

    @Override
    public CommandResult executeCommand(final Player player, CommandContext args) throws Exception {
        String kitName = args.<String>getOne(name).get();

        if (!kitConfig.getKitNames().contains(kitName)) {
            kitConfig.saveKit(kitName, kitConfig.createKit().updateKitInventory(player));
            player.sendMessage(Util.getTextMessageWithFormat("command.kit.add.success", kitName));
            return CommandResult.success();
        } else {
            player.sendMessage(Util.getTextMessageWithFormat("command.kit.add.alreadyexists", kitName));
            return CommandResult.empty();
        }
    }
}