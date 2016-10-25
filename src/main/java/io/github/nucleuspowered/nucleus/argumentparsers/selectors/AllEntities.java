/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.argumentparsers.selectors;

import java.util.regex.Pattern;

public class AllEntities extends StandardSelector.EntitySelector {
    public static final AllEntities INSTANCE = new AllEntities();
    private final Pattern pattern = Pattern.compile("^e(\\[.*\\])?$");

    private AllEntities() {}

    @Override
    public Pattern selector() {
        return pattern;
    }
}
