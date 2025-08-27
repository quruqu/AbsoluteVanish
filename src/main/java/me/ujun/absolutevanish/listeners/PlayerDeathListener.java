package me.ujun.absolutevanish.listeners;

import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player deadPlayer = event.getPlayer();
        Player killerPlayer = deadPlayer.getKiller();

        if (killerPlayer != null) {
            if (!VanishManager.isVanished(killerPlayer)) return;
        } else {
            if (!(VanishManager.isVanished(deadPlayer))) return;
        }

        String deathMessage = "§7[§bVanish§7]§f " + event.getDeathMessage();

        event.setDeathMessage(null);
        VanishManager.sendVanishedChat(deathMessage);
    }
}
