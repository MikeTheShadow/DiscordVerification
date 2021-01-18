package com.miketheshadow.discordverification;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VerifyCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        if(member == null || member.getRoles().stream().noneMatch(role -> role.getId().equals(DiscordVerification.config.getString("rank_id")))) return;

        String message = event.getMessage().getContentRaw();

        if(message.contains(DiscordVerification.config.getString("command"))) {
            if(message.split(" ").length != 2) {
                event.getChannel().sendMessage("Error! You need to specify a username! ex: `!verify miketheshadow`").queue();
                return;
            }
            String username = message.split(" ")[1];
            boolean wasAdded = DiscordVerification.addUser(username,member.getId());
            if(wasAdded) {
                event.getChannel().sendMessage("You are now whitelisted on the server!").queue();
            } else {
                event.getChannel().sendMessage("You are already whitelisted on the server").queue();
            }
        }
    }
}
