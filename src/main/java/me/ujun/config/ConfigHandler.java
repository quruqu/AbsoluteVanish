package me.ujun.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConfigHandler {

    private final JavaPlugin plugin;
    private static ConfigHandler instance;

    public static String discordJoinMessage;
    public static String discordQuitMessage;
    public static String discordFirstJoinMessage;
    public static boolean discordEnabled;
    public static String defaultQuitMessage;
    public static String defaultJoinMessage;
    public static boolean sendFakeMessageOnVanish;
    public static boolean sendFakeMessageOnUnvanish;
    private List<String> filteredCommands;
    public static boolean isDiscordEnabled = false;

    public ConfigHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void init(JavaPlugin plugin) {
        instance = new ConfigHandler(plugin);
        instance.loadConfig();
    }

    public static ConfigHandler getInstance() {
        return instance;
    }

    public void loadConfig() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        discordJoinMessage = config.getString("DiscordJoinMessage");
        discordQuitMessage = config.getString("DiscordQuitMessage");
        discordFirstJoinMessage = config.getString("DiscordFirstJoinMessage");
        discordEnabled = config.getBoolean("DiscordEnabled");

        defaultQuitMessage = config.getString("DefaultQuitMessage");
        defaultJoinMessage = config.getString("DefaultJoinMessage");

        sendFakeMessageOnVanish = config.getBoolean("SendFakeMessageOnVanish");
        sendFakeMessageOnUnvanish = config.getBoolean("SendFakeMessageOnUnvanish");

        filteredCommands = config.getStringList("TabCompleteFilterCommands");

        if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV") && ConfigHandler.discordEnabled) {
            isDiscordEnabled = true;
        }
    }

    public boolean shouldFilter(String input) {
        for (String command : filteredCommands) {
            if (input.startsWith(command)) return true;
        }
        return false;
    }

}
