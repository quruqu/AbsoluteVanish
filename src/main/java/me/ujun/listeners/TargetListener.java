package me.ujun.listeners;

import me.ujun.utils.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class TargetListener implements Listener {

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player player)) return;
        if (!(VanishManager.isVanished(player))) return;

        event.setCancelled(true);
    }
}
