package io.github.NoOne.nMLMobs.mobstats;

import io.github.NoOne.nMLPlayerStats.statSystem.Stats;

import java.util.LinkedHashMap;

import static io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat.*;

public class MobStats extends Stats {
    private int level;

    public MobStats(int level, double maxHealth, double maxOverhealth,
                 int physicalDamage, int fireDamage, int coldDamage, int earthDamage, int lightningDamage, int airDamage, int radiantDamage, int necroticDamage, int pureDamage,
                 int critChance, int critDamage,
                 int evasion, int defense, int physicalResist, int fireResist, int coldResist, int earthResist, int lightningResist, int airResist, int radiantResist, int necroticResist) {
        super(new LinkedHashMap<>(){{
            put(MAX_HEALTH, maxHealth);
            put(MAX_OVERHEALTH, maxOverhealth);

            put(PHYSICAL_DAMAGE, (double) physicalDamage);
            put(FIRE_DAMAGE, (double) fireDamage);
            put(COLD_DAMAGE, (double) coldDamage);
            put(EARTH_DAMAGE, (double) earthDamage);
            put(LIGHTNING_DAMAGE, (double) lightningDamage);
            put(AIR_DAMAGE, (double) airDamage);
            put(RADIANT_DAMAGE, (double) radiantDamage);
            put(NECROTIC_DAMAGE, (double) necroticDamage);
            put(PURE_DAMAGE, (double) pureDamage);
            put(CRIT_CHANCE, (double) critChance);
            put(CRIT_DAMAGE, (double) critDamage);

            put(EVASION, (double) evasion);
            put(DEFENSE, (double) defense);
            put(PHYSICAL_RESIST, (double) physicalResist);
            put(FIRE_RESIST, (double) fireResist);
            put(COLD_RESIST, (double) coldResist);
            put(EARTH_RESIST, (double) earthResist);
            put(LIGHTNING_RESIST, (double) lightningResist);
            put(AIR_RESIST, (double) airResist);
            put(RADIANT_RESIST, (double) radiantResist);
            put(NECROTIC_RESIST, (double) necroticResist);
        }});

        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}