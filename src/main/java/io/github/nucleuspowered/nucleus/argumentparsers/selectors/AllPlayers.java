/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import java.util.regex.Pattern;

/**
 * Selector to get all players
 */
public class AllPlayers extends StandardSelector.PlayerSelector {

    public static final AllPlayers INSTANCE = new AllPlayers();
    private final Pattern pattern = Pattern.compile("^a(\\[.*\\])?$");

    private AllPlayers() {}

    @Override
    public Pattern selector() {
        return pattern;
    }
}
