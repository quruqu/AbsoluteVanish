package me.ujun.listeners;

import me.ujun.utils.VanishManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!(VanishManager.isVanished(event.getPlayer()))) return;
        event.setDeathMessage(null);
    }
}
