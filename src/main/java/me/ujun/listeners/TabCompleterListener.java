package me.ujun.listeners;

import me.ujun.config.ConfigHandler;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.List;

public class TabCompleterListener implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player player)) return;
        if (VanishManager.isVanished(player)) return;


        String buffer = event.getBuffer();

        ConfigHandler.getInstance().shouldFilter(buffer);

        List<String> completions = event.getCompletions();

        completions.removeIf(s -> {
            if (s.startsWith("user:")) {
                String name = s.substring("user:".length()); // user: 제거
                Player target = Bukkit.getPlayerExact(name);
                return target != null && VanishManager.isVanished(target);
            } else {
                Player target = Bukkit.getPlayerExact(s);
                return target != null && VanishManager.isVanished(target);
            }
        });
    }
}
