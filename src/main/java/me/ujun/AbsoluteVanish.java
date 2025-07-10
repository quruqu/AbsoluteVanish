package me.ujun;



import me.ujun.commands.AbsoluteVanishCMD;
import me.ujun.commands.CommandTabCompleter;
import me.ujun.commands.VanishCMD;
import me.ujun.config.ConfigHandler;
import me.ujun.listeners.*;
import me.ujun.saving.DataFile;
import me.ujun.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AbsoluteVanish extends JavaPlugin {
    private DataFile dataFile;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        dataFile = new DataFile(getDataFolder());
        dataFile.loadSets(VanishManager.vanishedPlayers);

        ConfigHandler.init(this);
        VanishManager.init(this);

        registerCommands();
        registerListeners();
        run();
    }

    @Override
    public void onDisable() {
        dataFile.saveSets(VanishManager.vanishedPlayers);
    }

    private void registerCommands() {
        this.getCommand("vanish").setExecutor(new VanishCMD());
        this.getCommand("vanish").setTabCompleter(new CommandTabCompleter());
        this.getCommand("absolutevanish").setExecutor(new AbsoluteVanishCMD());
        this.getCommand("absolutevanish").setTabCompleter(new CommandTabCompleter());

    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PingServerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new TargetListener(), this);
        this.getServer().getPluginManager().registerEvents(new TabCompleterListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new AdvancementListener(), this);

    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (VanishManager.isVanished(p)) {
                        p.sendActionBar("§7§l[ VANISHED ]");

                        Entity target = p.getTargetEntity(10);
                        if (target instanceof Player) {
                            Player targetPlayer = (Player) target;
                           if (VanishManager.isVanished(targetPlayer)) {
                               p.sendActionBar("§7§l[ §f§l" + targetPlayer.getName() + ":§7§l VANISHED ]");
                           }
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }
}
