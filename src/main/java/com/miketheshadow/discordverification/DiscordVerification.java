package com.miketheshadow.discordverification;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class DiscordVerification extends JavaPlugin {

    public static FileConfiguration config;
    public static FileConfiguration userConfig;

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    @Override
    public void onEnable() {
        // Plugin startup logic
        userConfig = getUserConfig();

        File file = new File(this.getDataFolder(),"config.yml");
        if(!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
            config = YamlConfiguration.loadConfiguration(file);
            config.set("token","token-here");
            config.set("rank_id","800598890977034280");
            config.set("command","!verify");
            try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
        } else config = YamlConfiguration.loadConfiguration(file);

        EXECUTOR.execute(new BotThread());
    }

    private static File userConfigFile;

    private FileConfiguration getUserConfig() {
        userConfigFile = new File(this.getDataFolder(),"user.yml");
        if(!userConfigFile.exists()) {
            try { userConfigFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        return YamlConfiguration.loadConfiguration(userConfigFile);
    }

    public static boolean addUser(String username,String discordID) {
        if(userConfig.getString(discordID) == null) {
            userConfig.set(discordID,username);
            try { userConfig.save(userConfigFile); } catch (IOException e) { e.printStackTrace(); }
            Bukkit.getServer().getOfflinePlayer(username).setWhitelisted(true);
            Bukkit.getServer().reloadWhitelist();
            return true;
        } else {
          return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BotThread.jda.shutdownNow();
    }
}
