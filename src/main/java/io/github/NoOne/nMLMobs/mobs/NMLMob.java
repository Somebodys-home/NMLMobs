package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobSystem;
import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class acts as the framework for every custom mob
 * as such, any class for a custom mob must inherit from this class.
 * This class IS NOT a shoo-in for an actual entity on the server,
 * rather just is a more like a helper object that will be attached to it.
 */
public class NMLMob {
    protected HashMap<UUID, Integer> noDamageTicks = new HashMap<>();
    protected String name;
    protected MobStats mobStats;

    public NMLMob(String name) {
        this.name = name;
        mobStats = NMLMobs.getInstance().getMobStatsYMLManager().getMobStatsFromYml(name);
    }

    /**
     * Method to make and spawn this nml mob
     *
     * @param nmlMobs  the nmlMobs plugin
     * @param location the location to spawn the mob in
     */
    public void spawn(NMLMobs nmlMobs, Location location) {}

    /**
     * Sets the no damage ticks of an attacker onto this nml mob
     * @param attacker the living entity that damaged this nml mob
     * @param noDamageTicks the no damage ticks that that attack did
     */
    public void addAttacker(LivingEntity attacker, Integer noDamageTicks) {
        this.noDamageTicks.put(attacker.getUniqueId(), noDamageTicks);
    }

    /**
     * Adds a given living entity, and this nml mob, to the hashmap for all nml mobs,
     * so it can be tracked
     *
     * @param livingEntity the living entity to be added
     */
    public void addToMap(LivingEntity livingEntity) {
        NMLMobSystem.addNMLMob(this, livingEntity);
    }

    /**
     * Reduces every tick count on the no damage tick map by one
     * If it would hit 0, it instead gets removed
     */
    public void tickDownMap() {
        noDamageTicks.replaceAll((_, ticks) -> ticks - 1);
        noDamageTicks.values().removeIf(ticks -> ticks <= 0);
    }

    /**
     * Returns true/false if the mob can be damaged by that attacker again
     * @param attacker the living entity that is trying to damage this nml mob
     * @return if the living entity is not on the no damage ticks map for this nml mob
     */
    public boolean canBeDamaged(LivingEntity attacker) {
        return !noDamageTicks.containsKey(attacker.getUniqueId());
    }
}
