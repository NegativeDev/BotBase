package dev.negativekb.api;

import net.dv8tion.jda.api.entities.Activity;

public class BotClassExample extends Bot {

    public BotClassExample() {
        super("ID HERE", "TOKEN HERE", "!", "help", Activity.watching("your mother"));

        setInstance(this);

        // Other stuff here
    }
}
