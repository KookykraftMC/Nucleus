/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.annotations;

import io.github.nucleuspowered.nucleus.internal.command.AbstractCommand;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandAliases.class)
public @interface CommandAlias {
    /**
     * The subcommand that this represents. Defaults to the {@link AbstractCommand} class.
     *
     * @return The subcommand.
     */
    Class<? extends AbstractCommand> subcommandOf() default AbstractCommand.class;

    /**
     * The aliases for this command.
     *
     * @return Aliases for this command.
     */
    String[] value();
}
