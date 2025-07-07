package me.ujun.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.ujun.AbsoluteVanish;
import me.ujun.config.ConfigHandler;
import me.ujun.utils.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (VanishManager.isVanished(player)) {
            event.setQuitMessage(null);
        } else {
            if (ConfigHandler.isDiscordEnabled) {
                sendQuitEmbed(player);
            }
        }
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
