package io.github.NoOne.nMLMobs.mobstats;

import io.github.NoOne.nMLMobs.NMLMobs;
import org.bukkit.configuration.file.FileConfiguration;

public class MobStatsYMLManager {
    private FileConfiguration config;

    public MobStatsYMLManager(NMLMobs nmlMobs) {
        config = nmlMobs.getMobProfileConfig().getConfig();
    }

    public MobStats getMobStatsFromYml(String name) {
        // removing color from name
        if (name.contains("§")) {
            name = name.replaceAll("§[0-9a-fk-or]", "");
        }

        if (!config.isConfigurationSection(name)) return null;

        int level = config.getInt(name + ".level");

        double maxHealth = config.getDouble(name + ".maxHealth");
        double maxOverhealth = config.getDouble(name + ".maxOverhealth");

        int physicalDamage = config.getInt(name + ".physicalDamage");
        int fireDamage = config.getInt(name + ".fireDamage");
        int coldDamage = config.getInt(name + ".coldDamage");
        int earthDamage = config.getInt(name + ".earthDamage");
        int lightningDamage = config.getInt(name + ".lightningDamage");
        int airDamage = config.getInt(name + ".airDamage");
        int radiantDamage = config.getInt(name + ".radiantDamage");
        int necroticDamage = config.getInt(name + ".necroticDamage");
        int pureDamage = config.getInt(name + ".pureDamage");
        int critchance = config.getInt(name + ".critchance");
        int critdamage = config.getInt(name + ".critdamage");

        int evasion = config.getInt(name + ".evasion");
        int defense = config.getInt(name + ".defense");
        int physicalResist = config.getInt(name + ".physicalResist");
        int fireResist = config.getInt(name + ".fireResist");
        int coldResist = config.getInt(name + ".coldResist");
        int earthResist = config.getInt(name + ".earthResist");
        int lightningResist = config.getInt(name + ".lightningResist");
        int airResist = config.getInt(name + ".airResist");
        int radiantResist = config.getInt(name + ".radiantResist");
        int necroticResist = config.getInt(name + ".necroticResist");

        return new MobStats(level, maxHealth, maxOverhealth, physicalDamage, fireDamage, coldDamage, earthDamage,
                lightningDamage, airDamage, radiantDamage, necroticDamage, pureDamage, critchance, critdamage,
                evasion, defense, physicalResist, fireResist, coldResist, earthResist, lightningResist,
                airResist, radiantResist, necroticResist);
    }
}