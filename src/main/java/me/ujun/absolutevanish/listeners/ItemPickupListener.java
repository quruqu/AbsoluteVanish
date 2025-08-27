package me.ujun.absolutevanish.listeners;

import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;


public class ItemPickupListener implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(VanishManager.isVanished(player))) return;

        event.setCancelled(true);

    }
}
