package dev.negativekb.api;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public abstract class Bot {

    private static Bot instance;

    private final String id;
    private final String token;
    private String prefix;
    private String helpCommand;
    private JDA jda;
    private CommandClientBuilder builder;

    public Bot(String id, String token, String prefix) {
        this(id, token, prefix, null, null);
    }

    public Bot(String id, String token, String prefix, Activity activity) {
        this(id, token, prefix, null, activity);
    }

    public Bot(String id, String token, String prefix, String helpWord, Activity activity) {
        this.id = id;
        this.token = token;

        JDABuilder jdaBuilder = JDABuilder.createDefault(getToken());
        jda = null;
        try {
            jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        builder = new CommandClientBuilder();

        builder.setOwnerId(getID());
        builder.setPrefix(prefix);

        if (activity != null)
            builder.setActivity(activity);

        if (helpWord != null)
            builder.setHelpWord(helpWord);

        CommandClient client = builder.build();

        jda.addEventListener(client);
    }

    public static Bot getInstance() {
        return instance;
    }

    public static void setInstance(Bot instance) {
        Bot.instance = instance;
    }

    public String getID() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHelpCommand() {
        return helpCommand;
    }

    public JDA getJDA() {
        return jda;
    }

    public CommandClientBuilder getBuilder() {
        return builder;
    }

    public void addListeners(ListenerAdapter... listenerAdapters) {
        for (ListenerAdapter listenerAdapter : listenerAdapters) {
            getJDA().addEventListener(listenerAdapter);
        }
    }
}
