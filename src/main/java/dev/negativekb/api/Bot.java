package dev.negativekb.api;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import dev.negativekb.api.util.InputListener;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public abstract class Bot {

    @Getter
    @Setter
    private static Bot instance;

    @Getter
    private final String id;

    @Getter
    private final String token;
    @Getter
    private final CommandClientBuilder builder;
    @Getter
    @Setter
    private JDA jda;


    public Bot(String id, String token, String prefix) {
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

        builder.setOwnerId(getId());
        builder.setPrefix(prefix);

        jda.addEventListener(new InputListener());

        this.init();

        CommandClient client = builder.build();

        jda.addEventListener(client);
    }

    public abstract void init();

}
