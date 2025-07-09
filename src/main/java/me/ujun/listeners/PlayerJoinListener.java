package me.ujun.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.AbsoluteVanish;
import me.ujun.config.ConfigHandler;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
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

            event.setJoinMessage(null);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.equals(player) || !VanishManager.isVanished(p)) {
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
