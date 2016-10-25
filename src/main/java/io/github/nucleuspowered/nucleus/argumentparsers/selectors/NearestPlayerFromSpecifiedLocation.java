/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import com.flowpowered.math.vector.Vector3d;
import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.interfaces.SelectorParser;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Obtains the nearest player to the specified location.
 */
public class NearestPlayerFromSpecifiedLocation implements SelectorParser<Player> {

    public final static NearestPlayerFromSpecifiedLocation INSTANCE = new NearestPlayerFromSpecifiedLocation();

    private final Pattern selector = Pattern.compile("^p\\[([\\w-]+),(-?\\d+),(\\d{1,3}),(-?\\d+)\\]$", Pattern.CASE_INSENSITIVE);

    private NearestPlayerFromSpecifiedLocation() {}

    @Override
    public Pattern selector() {
        return selector;
    }

    @Override
    public Player get(String rawSelector, CommandSource source, CommandArgs args) throws ArgumentParseException {
        // Get the regex groups.
        Matcher m = selector.matcher(rawSelector);
        m.matches();
        String world = m.group(1);
        int x = Integer.parseInt(m.group(2));
        int y = Integer.parseInt(m.group(3));
        int z = Integer.parseInt(m.group(4));

        World spongeWorld = Sponge.getServer().getWorld(world).orElseThrow(() -> args.createError(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("args.selector.noworld", world)));

        // Remove players "out of this world", then sort players by distance from current location.
        return getNearestPlayerFromLocation(Sponge.getServer().getOnlinePlayers(), new Location<>(spongeWorld, x, y, z), args);
    }


    private Player getNearestPlayerFromLocation(Collection<Player> playerCollection, Location<World> locationInWorld, CommandArgs args) throws ArgumentParseException {
        Vector3d currentLocation = locationInWorld.getPosition();
        return playerCollection.parallelStream()
            .filter(x -> x.getWorld().getUniqueId().equals(locationInWorld.getExtent().getUniqueId()))
            .map(x -> new Tuple<>(x, x.getLocation().getPosition().distanceSquared(currentLocation)))
            .min((x, y) -> x.getSecond().compareTo(y.getSecond()))
            .orElseThrow(() -> args.createError(Nucleus.getNucleus().getMessageProvider().getTextMessageWithFormat("args.selector.notarget")))
            .getFirst();
    }

}
