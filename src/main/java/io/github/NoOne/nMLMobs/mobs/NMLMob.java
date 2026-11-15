package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobSystem;
import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * This class acts as the framework for every custom mob
 * as such, any class for a custom mob must inherit from this class.
 * This class IS NOT a shoo-in for an actual entity on the server,
 * rather just is a more like a helper object that will be attached to it.
 */
public class NMLMob {
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
     * Adds a given living entity, and this nml mob, to the hashmap for all nml mobs,
     * so it can be tracked
     *
     * @param livingEntity the living entity to be added
     */
    public void addToMap(LivingEntity livingEntity) {
        NMLMobSystem.addNMLMob(this, livingEntity);
    }

    public MobStats getMobStats() {
        return mobStats;
    }
}
