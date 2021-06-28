package dev.negativekb.api.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import dev.negativekb.api.Bot;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.*;

public abstract class ICommand extends Command {
    private final ArrayList<ISubCommand> subCommands = new ArrayList<>();
    private CommandEvent event;

    public ICommand(String command) {
        this(command, Collections.emptyList());
    }

    public ICommand(String command, List<String> aliases) {
        this.name = command;

        if (!aliases.isEmpty()) {
            this.aliases = aliases.toArray(new String[0]);
        }

        Bot.getInstance().getBuilder().addCommand(this);
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        if (args.length == 0) {
            this.event = event;
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

        this.event = event;
        runCommand(event, args);
    }

    public abstract void runCommand(CommandEvent event, String[] args);

    public void addSubCommands(ISubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    public void replyThenDelete(String message, int delayUntilDelete) {
        event.reply(message, msg -> {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    msg.delete().queue();
                }
            }, 1000L * delayUntilDelete);
        });
    }

    public void replyThenDelete(EmbedBuilder embedBuilder, int delayUntilDelete) {
        event.reply(embedBuilder.build(), message -> {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    message.delete().queue();
                }
            }, 1000L * delayUntilDelete);
        });
    }

}
