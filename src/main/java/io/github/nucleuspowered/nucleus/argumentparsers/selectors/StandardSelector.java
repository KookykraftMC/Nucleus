/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import io.github.nucleuspowered.nucleus.internal.interfaces.SelectorParser;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.selector.Selector;

import java.util.Collection;
import java.util.stream.Collectors;

abstract class StandardSelector<T> implements SelectorParser<Collection<T>> {

    @Override
    public final Collection<T> get(String selector, CommandSource source, CommandArgs args) throws ArgumentParseException {
        return parseOutput(Selector.parse("@" + selector).resolve(source));
    }

    public abstract Collection<T> parseOutput(Collection<Entity> entities);

    static abstract class PlayerSelector extends StandardSelector<Player> {

        @Override public final Collection<Player> parseOutput(Collection<Entity> entities) {
            return entities.stream().filter(x -> x instanceof Player).map(z -> (Player)z).collect(Collectors.toList());
        }
    }

    static abstract class EntitySelector extends StandardSelector<Entity> {

        @Override public final Collection<Entity> parseOutput(Collection<Entity> entities) {
            return entities;
        }
    }
}
