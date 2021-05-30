package dev.negativekb.api.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import dev.negativekb.api.Bot;

public abstract class ICommand extends Command {

    public ICommand(String command) {
        this(command, null);
    }

    public ICommand(String command, String[] aliases) {
        this.name = command;

        if (aliases != null)
            this.aliases = aliases;

        Bot.getInstance().getBuilder().addCommand(this);
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        this.runCommand(commandEvent);
    }

    public abstract void runCommand(CommandEvent event);


    public void setHelp(String help) {
        this.help = help;
    }

}
