/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import java.util.regex.Pattern;

/**
 * Obtains the nearest player to the locatable object.
 */
public class NearestPlayer extends StandardSelector.PlayerSelector {

    public final static NearestPlayer INSTANCE = new NearestPlayer();

    private final Pattern selector = Pattern.compile("^p(\\[.*\\])?$", Pattern.CASE_INSENSITIVE);

    private NearestPlayer() {}

    @Override
    public Pattern selector() {
        return selector;
    }
}
