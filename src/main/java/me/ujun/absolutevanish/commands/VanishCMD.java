package me.ujun.absolutevanish.commands;

import me.ujun.absolutevanish.utils.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

        Boolean sendFakeMessage = null;

        if (args.length == 1) {
            sendFakeMessage = CommandUtil.toBooleanOrNull(args[0]);
        }

        if (VanishManager.isVanished(player)) {
            if (sendFakeMessage == null) {
                VanishManager.getInstance().unvanish(player);
            }
            else {
                VanishManager.getInstance().unvanish(player, sendFakeMessage);
            }
        } else {
            if (sendFakeMessage == null) {
                VanishManager.getInstance().vanish(player);
            }
            else {
                VanishManager.getInstance().vanish(player, sendFakeMessage);
            }
        }
        return true;
    }


}
