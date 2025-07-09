package me.ujun.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.config.ConfigHandler;
import org.bukkit.entity.Player;

import java.awt.*;

public class DiscordUtil {
    public static void sendJoinEmbed(Player player) {
        TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("global");
        if (channel == null) return;

        String playerName = player.getName();
        String avatarUrl = "https://minotar.net/helm/" + playerName +  "/128.png";

        String message = ConfigHandler.discordJoinMessage;
        message = message.replace("%player%", playerName);

        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor(message, null, avatarUrl)
                .setColor(Color.GREEN);

        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public static void sendFirstJoinEmbed(Player player) {
        TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("global");
        if (channel == null) return;

        String playerName = player.getName();
        String avatarUrl = "https://minotar.net/helm/" + playerName +  "/128.png";

        String message = ConfigHandler.discordFirstJoinMessage;
        message = message.replace("%player%", playerName);

        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor(message, null, avatarUrl)
                .setColor(Color.YELLOW);

        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public static void sendQuitEmbed(Player player) {
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
