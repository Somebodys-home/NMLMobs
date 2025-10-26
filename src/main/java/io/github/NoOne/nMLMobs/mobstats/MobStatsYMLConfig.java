package io.github.NoOne.nMLMobs.mobstats;

import io.github.NoOne.nMLMobs.NMLMobs;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class MobStatsYMLConfig {
    private NMLMobs nmlMobs;
    private File file;
    private String fileName;
    private FileConfiguration config = new YamlConfiguration();

    public MobStatsYMLConfig(NMLMobs nmlMobs, String filename) {
        this.nmlMobs = nmlMobs;
        this.fileName = filename;
        file = new File(nmlMobs.getDataFolder(), filename + ".yml");
    }

    public void loadConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            nmlMobs.saveResource(fileName + ".yml", false);
        } try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
