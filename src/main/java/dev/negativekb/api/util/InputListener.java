package dev.negativekb.api.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class InputListener extends ListenerAdapter {

    private static final Map<String, BiConsumer<Member, Message>> listenForInput = new HashMap<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        if (member == null) // ????
            return;

        String id = event.getMember().getId();
        if (!listenForInput.containsKey(id))
            return;

        BiConsumer<Member, Message> consumer = listenForInput.get(id);
        try {
            event.getMessage().delete().queue();
            consumer.accept(member, event.getMessage());
        } catch (Exception ignored) {
        } finally {
            listenForInput.remove(id, consumer);
        }
    }
}
