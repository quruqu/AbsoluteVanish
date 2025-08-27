package me.ujun.absolutevanish.listeners;

import me.ujun.absolutevanish.config.ConfigHandler;
import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;
    private Set<UUID> vanishedPlayers;

    public PlayerJoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.vanishedPlayers = VanishManager.vanishedPlayers;
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        if (VanishManager.isVanished(player)) {

            String joinMessage = "§7[§bVanish§7]§f " + event.getJoinMessage();

            event.setJoinMessage(null);
            VanishManager.sendVanishedChat(joinMessage);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.equals(player) && !VanishManager.isVanished(p)) {
                    p.hidePlayer(plugin, player);
                }
            }
        } else {
            VanishManager.currentJoinMessage = event.getJoinMessage().replace(player.getName(), "%player%");

            //discordSRV
            if (ConfigHandler.isDiscordEnabled) {
                if (player.hasPlayedBefore()) {
                  DiscordUtil.sendJoinEmbed(player);
                } else {
                  DiscordUtil.sendFirstJoinEmbed(player);
                }
            }

            for (UUID uuid : vanishedPlayers) {
                Player vanishedPlayer = Bukkit.getPlayer(uuid);
                if (vanishedPlayer != null) {
                    player.hidePlayer(plugin, vanishedPlayer);
                }
            }
        }
    }


}
