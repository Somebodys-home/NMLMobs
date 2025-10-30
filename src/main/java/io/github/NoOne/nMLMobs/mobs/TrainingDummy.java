package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class TrainingDummy {
    public TrainingDummy(NMLMobs nmlMobs, Location location) {
        location.clone().setYaw(location.getYaw() + 180F);

        IronGolem trainingDummy = (IronGolem) NMLMobHelper.makeNMlMob(nmlMobs, location, EntityType.IRON_GOLEM, "§cTraining Dummy", false);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml("§cTraining Dummy");
        Location loc = trainingDummy.getLocation();

        loc.setYaw(loc.getYaw() + 180F); // turn it around
        trainingDummy.teleport(loc);
        trainingDummy.setAI(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                fullHeal(trainingDummy, mobStats);
            }
        }.runTaskTimer(nmlMobs, 300L, 300L);
    }

    private void fullHeal(IronGolem trainingDummy, MobStats mobStats) {
        NMLMobHelper.useAbility(trainingDummy, 30, Particle.END_ROD, new BukkitRunnable() {
            @Override
            public void run() {
                trainingDummy.setHealth(mobStats.getMaxHealth());
            }
        });
    }
}
