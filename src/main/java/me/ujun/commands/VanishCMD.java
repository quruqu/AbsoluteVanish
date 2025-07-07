package me.ujun.commands;

import me.ujun.AbsoluteVanish;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishCMD implements CommandExecutor {

    private Set<UUID> vanishedPlayers;

    public VanishCMD() {
        this.vanishedPlayers = VanishManager.vanishedPlayers;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }


        if (VanishManager.isVanished(player)) {
            VanishManager.getInstance().unvanish(player);
        } else {
            VanishManager.getInstance().vanish(player);
        }
        return true;
    }


}
