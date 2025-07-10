package me.ujun.utils;

import me.ujun.config.ConfigHandler;
import me.ujun.listeners.DiscordUtil;
import org.bukkit.*;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.text.StyledEditorKit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {
    private final JavaPlugin plugin;
    private static VanishManager instance;
    public static final Set<UUID> vanishedPlayers = new HashSet<>();
    public static String currentQuitMessage;
    public static String currentJoinMessage;

    public VanishManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static VanishManager getInstance() {
        return instance;
    }

    public static void init(JavaPlugin plugin) {
        instance = new VanishManager(plugin);
        currentQuitMessage = ConfigHandler.defaultQuitMessage;
        currentJoinMessage = ConfigHandler.defaultJoinMessage;
    }

    public static boolean isVanished(Player player) { return vanishedPlayers.contains(player.getUniqueId()); }

    public static boolean isVanished(OfflinePlayer player) { return vanishedPlayers.contains(player.getUniqueId()); }


    public void vanish(Player target) {
        vanish(target, ConfigHandler.sendFakeMessageOnVanish);
    }

    public void vanish(Player target, Boolean sendFakeMessage) {
        vanishedPlayers.add(target.getUniqueId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(target)) {
                if (VanishManager.isVanished(p)) {
                    target.showPlayer(plugin, p);
                } else {
                    p.hidePlayer(plugin, target);
                }
            }
        }



        target.sendMessage("§7[§bVanish§7] §fYou are now vanished.");

        if (sendFakeMessage) {
            sendFakeQuitMessage(target);
        }
    }

    public void unvanish(Player target) {
        unvanish(target, ConfigHandler.sendFakeMessageOnUnvanish);
    }

    public void unvanish(Player target, Boolean sendFakeMessage) {
        vanishedPlayers.remove(target.getUniqueId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(plugin, target);

            if (VanishManager.isVanished(p)) {
                target.hidePlayer(plugin, p);
            }
        }
        target.sendMessage("§7[§bVanish§7] §fYou are now visible.");

        if (sendFakeMessage) {
            sendFakeJoinMessage(target);
        }
    }

    public static void sendFakeJoinMessage(OfflinePlayer target) {
        String message = VanishManager.currentJoinMessage
                .replace("%player%", target.getName());

        if (target.isOnline() && ConfigHandler.isDiscordEnabled) {
            Player onlineTarget = (Player) target;
            DiscordUtil.sendJoinEmbed(onlineTarget);
        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendFakeQuitMessage(OfflinePlayer target) {
        String message = VanishManager.currentQuitMessage
                .replace("%player%", target.getName());

        if (target.isOnline() && ConfigHandler.isDiscordEnabled) {
            Player onlineTarget = (Player) target;
            DiscordUtil.sendQuitEmbed(onlineTarget);
        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
