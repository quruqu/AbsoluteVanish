package me.ujun.utils;

import me.ujun.config.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {
    private final JavaPlugin plugin;
    private static VanishManager instance;
    public static final Set<UUID> vanishedPlayers = new HashSet<>();

    public VanishManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static VanishManager getInstance() {
        return instance;
    }

    public static void init(JavaPlugin plugin) {
        instance = new VanishManager(plugin);
    }

    public static boolean isVanished(Player player) { return vanishedPlayers.contains(player.getUniqueId()); }

    public static boolean isVanished(OfflinePlayer player) { return vanishedPlayers.contains(player.getUniqueId()); }



    public void vanish(Player target) {
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
    }

    public void unvanish(Player target) {
        vanishedPlayers.remove(target.getUniqueId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(plugin, target);

            if (VanishManager.isVanished(p)) {
                target.hidePlayer(plugin, p);
            }
        }
        target.sendMessage("§7[§bVanish§7] §fYou are now visible.");
    }

}
