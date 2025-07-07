package me.ujun.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import java.util.ArrayList;
import java.util.List;



public class PingServerListener implements Listener {

    @EventHandler
    public void onPing(PaperServerListPingEvent event) {
        if (VanishManager.vanishedPlayers.isEmpty()) return;

        List<PaperServerListPingEvent.ListedPlayerInfo> visiblePlayers = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!VanishManager.isVanished(p)) {
                visiblePlayers.add(new PaperServerListPingEvent.ListedPlayerInfo(p.getName(), p.getUniqueId()));
            }
        }


        event.setNumPlayers(visiblePlayers.size());
        event.getListedPlayers().clear();
        event.getListedPlayers().addAll(visiblePlayers);

    }

}
