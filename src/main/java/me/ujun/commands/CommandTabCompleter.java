package me.ujun.commands;

import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class CommandTabCompleter implements TabCompleter {

    private final List<String> SUBCOMMANDS = Arrays.asList("reload", "list", "vanish", "unvanish");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completion = new ArrayList<>();

        if (command.getName().equals("vanish")) {
            return completion;
        }
        else if (command.getName().equals("absolutevanish")) {
            if (args.length == 1) {
                return SUBCOMMANDS.stream()
                        .filter(s -> s.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
            else if (args.length == 2) {
                if (args[0].equals("vanish")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String name = player.getName();

                        if (!VanishManager.isVanished(player) && name.toLowerCase().startsWith(args[1].toLowerCase())) {
                            completion.add(name);
                        }
                    }

                    return completion;
                }
                else if (args[0].equals("unvanish")) {
                    for (UUID uuid : VanishManager.vanishedPlayers) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                        String name = player.getName();

                        if (name != null && name.toLowerCase().startsWith(args[1].toLowerCase())) {
                            completion.add(name);
                        }
                    }

                    return completion;
                }
                else {
                    return completion;
                }
            }
        }

        return completion;
    }
}
