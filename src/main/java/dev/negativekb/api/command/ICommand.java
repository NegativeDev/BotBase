package dev.negativekb.api.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import dev.negativekb.api.Bot;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.*;

public abstract class ICommand extends Command {
    @Getter
    private final ArrayList<ISubCommand> subCommands = new ArrayList<>();

    @Getter
    @Setter
    private CommandEvent event;

    public ICommand(String command) {
        this(command, Collections.emptyList());
    }

    public ICommand(String command, List<String> aliases) {
        this.name = command;

        if (!aliases.isEmpty())
            this.aliases = aliases.toArray(new String[0]);


        Bot.getInstance().getBuilder().addCommand(this);
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        if (args.length == 0) {
            setEvent(event);
            this.runCommand(event, args);
            return;
        }

        String arg = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        ISubCommand subCommand = getSubCommands().stream().filter(iSubCommand -> {
            if (iSubCommand.getArgument().equalsIgnoreCase(arg))
                return true;

            List<String> aliases = iSubCommand.getAliases();
            if (aliases == null || aliases.isEmpty())
                return false;

            return aliases.contains(arg.toLowerCase());
        }).findFirst().orElse(null);

        if (subCommand != null) {
            subCommand.execute(event, newArgs);
            return;
        }

        setEvent(event);
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
