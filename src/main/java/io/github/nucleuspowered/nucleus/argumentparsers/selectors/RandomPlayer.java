/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Selector to get all players
 */
public class RandomPlayer extends StandardSelector.PlayerSelector {

    public static final RandomPlayer INSTANCE = new RandomPlayer();
    private final Pattern pattern = Pattern.compile("^r$");
    private final Random random = new Random();

    private RandomPlayer() {}

    @Override
    public Pattern selector() {
        return pattern;
    }
}
