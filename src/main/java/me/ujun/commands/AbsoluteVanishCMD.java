package me.ujun.commands;

import com.google.common.graph.ValueGraphBuilder;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.config.ConfigHandler;
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
        else if (args.length == 2) {
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

               if (target.isOnline()) {
                   Player onlineTarget = (Player) target;
                   VanishManager.getInstance().vanish(onlineTarget);
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

                if (target.isOnline()) {
                    Player onlineTarget = (Player) target;
                    VanishManager.getInstance().unvanish(onlineTarget);
                } else {
                    VanishManager.vanishedPlayers.remove(target.getUniqueId());
                }
                sender.sendMessage("§7[§bVanish§7]§f " + target.getName() + " is now visible");
                return true;
            }
            else if (args[0].equals("fakequitmessage")) {
                String message = VanishManager.currentQuitMessage
                        .replace("%player%", target.getName());

                if (target.isOnline() && ConfigHandler.isDiscordEnabled) {
                    Player onlineTarget = (Player) target;
                    sendQuitEmbed(onlineTarget);
                }

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            return false;
        }

        return false;
    }

    public void sendQuitEmbed(Player player) {
        TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("global");
        if (channel == null) return;

        String playerName = player.getName();
        String avatarUrl = "https://minotar.net/helm/" + playerName +  "/128.png";

        String message = ConfigHandler.discordQuitMessage;
        message = message.replace("%player%", playerName);

        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor(message, null, avatarUrl)
                .setColor(Color.RED);

        channel.sendMessageEmbeds(eb.build()).queue();

    }
}
