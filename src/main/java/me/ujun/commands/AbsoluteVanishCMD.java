package me.ujun.commands;

import com.google.common.graph.ValueGraphBuilder;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.config.ConfigHandler;
import me.ujun.listeners.DiscordUtil;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbsoluteVanishCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].equals("reload")) {
                sender.sendMessage("§7[§bVanish§7]§f successfully reload the config.");
                ConfigHandler.getInstance().loadConfig();
                return true;
            }
            else if (args[0].equals("list")) {
                if (VanishManager.vanishedPlayers.isEmpty()) {
                    sender.sendMessage("§7[§bVanish§7]§f there is no vanished player");
                    return false;
                }
                List<String> names = VanishManager.vanishedPlayers.stream()
                        .map(Bukkit::getOfflinePlayer)
                        .map(OfflinePlayer::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());


                String list = String.join("§7, §f", names);
                sender.sendMessage("§7[§bVanish§7]§f Vanished Players: §f" + list);
                return true;
            }
        }
        else if ((args.length <= 3)) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (target == null) {
                sender.sendMessage("§7[§bVanish§7]§f wrong player!");
                return false;
            }

            if (args[0].equals("vanish")) {

                if (VanishManager.isVanished(target)) {
                    sender.sendMessage("§7[§bVanish§7]§c " + target.getName() + " is already vanished!");
                    return false;
                }

                Boolean sendFakeMessage = null;

                if (args.length == 3) {
                    sendFakeMessage = CommandUtil.toBooleanOrNull(args[2]);
                }

               if (target.isOnline()) {
                   Player onlineTarget = (Player) target;

                   if (sendFakeMessage == null) {
                       VanishManager.getInstance().vanish(onlineTarget);
                   } else {
                       VanishManager.getInstance().vanish(onlineTarget, sendFakeMessage);
                   }
               } else {
                   VanishManager.vanishedPlayers.add(target.getUniqueId());
               }
                sender.sendMessage("§7[§bVanish§7]§f " + target.getName() + " is now vanished");
                return true;
            }
            else if (args[0].equals("unvanish")) {

                if (!VanishManager.isVanished(target)) {
                    sender.sendMessage("§7[§bVanish§7]§c " + target.getName() + " is not vanished!");
                    return false;
                }

                Boolean sendFakeMessage = null;

                if (args.length == 3) {
                    sendFakeMessage = CommandUtil.toBooleanOrNull(args[2]);
                }

                if (target.isOnline()) {
                    Player onlineTarget = (Player) target;
                    if (sendFakeMessage == null) {
                        VanishManager.getInstance().unvanish(onlineTarget);
                    } else {
                        VanishManager.getInstance().unvanish(onlineTarget, sendFakeMessage);
                    }
                } else {
                    VanishManager.vanishedPlayers.remove(target.getUniqueId());
                }
                sender.sendMessage("§7[§bVanish§7]§f " + target.getName() + " is now visible");
                return true;
            }
            else if (args[0].equals("fakequitmessage")) {
               VanishManager.sendFakeQuitMessage(target);
               return true;
            }
            else if (args[0].equals("fakejoinmessage")) {
                VanishManager.sendFakeJoinMessage(target);
                return true;
            }
            return false;
        }

        return false;
    }
}
