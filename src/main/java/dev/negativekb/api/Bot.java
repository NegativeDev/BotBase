package dev.negativekb.api;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public abstract class Bot {

    private static Bot instance;

    private final String id;
    private final String token;
    private JDA jda;
    private final CommandClientBuilder builder;

    public Bot(String id, String token, String prefix) {
        this(id, token, prefix, null, null, null);
    }

    public Bot(String id, String token, String prefix, Activity activity) {
        this(id, token, prefix, null, activity, null);
    }

    public Bot(String id, String token, String prefix, String helpWord, Activity activity, OnlineStatus onlineStatus) {
        this.id = id;
        this.token = token;

        JDABuilder jdaBuilder = JDABuilder.createDefault(getToken());
        jda = null;
        try {
            jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        this.init();

        builder = new CommandClientBuilder();

        builder.setOwnerId(getID());
        builder.setPrefix(prefix);

        if (helpWord != null)
            builder.setHelpWord(helpWord);

        if (activity != null)
            builder.setActivity(activity);

        if (onlineStatus != null)
            builder.setStatus(onlineStatus);

        CommandClient client = builder.build();

        jda.addEventListener(client);
    }

    public abstract void init();

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
