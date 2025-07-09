package me.ujun.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.AbsoluteVanish;
import me.ujun.config.ConfigHandler;
import me.ujun.utils.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (VanishManager.isVanished(player)) {
            event.setQuitMessage(null);
        } else {
            VanishManager.currentQuitMessage = event.getQuitMessage().replace(player.getName(), "%player%");
            if (ConfigHandler.isDiscordEnabled) {
                DiscordUtil.sendQuitEmbed(player);
            }
        }
    }
}
