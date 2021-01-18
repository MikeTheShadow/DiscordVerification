package com.miketheshadow.discordverification;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.security.auth.login.LoginException;

public class BotThread implements Runnable {


    public static JDA jda;

    @Override
    public void run() {
        try {
            jda = JDABuilder.createDefault(DiscordVerification.config.getString("token"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new VerifyCommand()).build();
        } catch (LoginException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Token: " + DiscordVerification.config.getString("token") + " is invalid!");
        }
    }
}
