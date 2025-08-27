package me.ujun.absolutevanish.listeners;

import me.ujun.absolutevanish.config.ConfigHandler;
import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (VanishManager.isVanished(player)) {
            String quitMessage = "§7[§bVanish§7]§f " + event.getQuitMessage();

            event.setQuitMessage(null);
            VanishManager.sendVanishedChat(quitMessage);
        } else {
            VanishManager.currentQuitMessage = event.getQuitMessage().replace(player.getName(), "%player%");
            if (ConfigHandler.isDiscordEnabled) {
                DiscordUtil.sendQuitEmbed(player);
            }
        }
    }
}
