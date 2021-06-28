package dev.negativekb.api.command;

import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ISubCommand {

    private final String argument;
    private final List<String> aliases;
    private final List<ISubCommand> subCommands = new ArrayList<>();

    public ISubCommand(String argument) {
        this(argument, Collections.emptyList());
    }

    public ISubCommand(String argument, List<String> aliases) {
        this.argument = argument;
        this.aliases = aliases;
    }

    public void execute(CommandEvent event, String[] args) {
        if (args.length == 0) {
            this.runCommand(event, args);
            return;
        }

        String arg = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        for (ISubCommand subCommand : subCommands) {
            if (subCommand.getArgument().equalsIgnoreCase(arg)) {
                subCommand.execute(event, newArgs);
                return;
            }

            List<String> aliases = subCommand.getAliases();
            if (aliases == null || aliases.isEmpty())
                continue;

            for (String alias : aliases) {
                if (alias.equalsIgnoreCase(arg)) {
                    subCommand.execute(event, newArgs);
                    return;
                }
            }
        }

        runCommand(event, args);
    }

    public abstract void runCommand(CommandEvent event, String[] args);

    public String getArgument() {
        return argument;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addSubCommands(ISubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }
}
