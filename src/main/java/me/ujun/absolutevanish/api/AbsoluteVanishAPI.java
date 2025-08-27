package me.ujun.absolutevanish.api;

import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class AbsoluteVanishAPI {

    public static boolean isVanished(Player player) {
        return VanishManager.isVanished(player);
    }

    public static boolean isVanished(OfflinePlayer player) {
        return VanishManager.isVanished(player);
    }

    public static Set<UUID> getVanishedPlayers() {
       return VanishManager.vanishedPlayers;
   }

//    public static void vanish(Player player) {
//        VanishManager.getInstance().vanish(player);
//    }
//
//    public static void vanish(Player player, boolean sendFakeMessage) {
//        VanishManager.getInstance().vanish(player, sendFakeMessage);
//    }
//
//    public static void unvanish(Player player) {
//        VanishManager.getInstance().unvanish(player);
//    }
//
//    public static void unvanish(Player player, boolean sendFakeMessage) {
//        VanishManager.getInstance().unvanish(player, sendFakeMessage);
//    }
//
//
//    public static void sendFakeJoinMessage(OfflinePlayer player) {
//        VanishManager.sendFakeJoinMessage(player);
//    }
//
//    public static void sendFakeQuitMessage(OfflinePlayer player) {
//        VanishManager.sendFakeQuitMessage(player);
//    }
}
