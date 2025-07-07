package me.ujun.listeners;

import me.ujun.utils.VanishManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
    @EventHandler
    public void onDoneAdvancement(PlayerAdvancementDoneEvent event) {
        if (!(VanishManager.isVanished(event.getPlayer()))) return;
        event.message(null);
    }

}
