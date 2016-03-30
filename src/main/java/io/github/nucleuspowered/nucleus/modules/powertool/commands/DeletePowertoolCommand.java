/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.powertool.commands;

import com.google.inject.Inject;
import io.github.nucleuspowered.nucleus.Util;
import io.github.nucleuspowered.nucleus.config.loaders.UserConfigLoader;
import io.github.nucleuspowered.nucleus.internal.annotations.*;
import io.github.nucleuspowered.nucleus.internal.command.OldCommandBase;
import io.github.nucleuspowered.nucleus.internal.interfaces.InternalNucleusUser;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * Deletes the powertool associated with the item in the hand.
 *
 * Permission: nucleus.powertool.base (uses the base permission)
 */
@Permissions(alias = "powertool")
@RunAsync
@NoCooldown
@NoWarmup
@NoCost
@RegisterCommand(value = {"delete", "del", "rm", "remove"}, subcommandOf = PowertoolCommand.class)
public class DeletePowertoolCommand extends OldCommandBase<Player> {

    @Inject private UserConfigLoader loader;

    @Override
    public CommandSpec createSpec() {
        return getSpecBuilderBase().build();
    }

    @Override
    public CommandResult executeCommand(Player src, CommandContext args) throws Exception {
        Optional<ItemStack> itemStack = src.getItemInHand();
        if (!itemStack.isPresent()) {
            src.sendMessage(Util.getTextMessageWithFormat("command.powertool.noitem"));
            return CommandResult.empty();
        }

        InternalNucleusUser user = loader.getUser(src);
        ItemType item = itemStack.get().getItem();

        Optional<List<String>> cmds = user.getPowertoolForItem(item);
        if (cmds.isPresent() && !cmds.get().isEmpty()) {
            user.clearPowertool(item);
            src.sendMessage(Util.getTextMessageWithFormat("command.powertool.removed", item.getId()));
        } else {
            src.sendMessage(Util.getTextMessageWithFormat("command.powertool.nocmds", item.getId()));
        }

        return CommandResult.success();
    }
}