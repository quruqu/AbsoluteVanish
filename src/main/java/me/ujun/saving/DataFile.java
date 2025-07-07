package me.ujun.saving;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DataFile {
    private final File file;
    private final FileConfiguration config;

    public DataFile(File dataFolder) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs(); // 폴더 먼저 생성
        }

        this.file = new File(dataFolder, "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveSets(Set<UUID> vanished) {
        List<String> vanishedList = vanished.stream().map(UUID::toString).toList();


        config.set("vanished", vanishedList);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSets(Set<UUID> vanished) {
        List<String> vanishedList = config.getStringList("vanished");

        vanished.clear();

        vanishedList.forEach(s -> {
            try {
                vanished.add(UUID.fromString(s));
            } catch (IllegalArgumentException ignored) {}
        });

    }
}
