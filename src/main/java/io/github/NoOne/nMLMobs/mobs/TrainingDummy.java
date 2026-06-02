package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class TrainingDummy {
    public TrainingDummy(NMLMobs nmlMobs, Location location) {
        IronGolem trainingDummy = (IronGolem) NMLMobSystem.makeNMlMob(nmlMobs, location, EntityType.IRON_GOLEM, "§cTraining Dummy", false);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml("§cTraining Dummy");

        trainingDummy.setAI(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (trainingDummy.getHealth() < trainingDummy.getMaxHealth()) {
                    fullHeal(trainingDummy, mobStats, location);
                }

                if (trainingDummy.isDead()) {
                    cancel();
                }
            }
        }.runTaskTimer(nmlMobs, 300L, 300L);
    }

    private void fullHeal(IronGolem trainingDummy, MobStats mobStats, Location location) {
        NMLMobSystem.useAbility(trainingDummy, 30, Particle.END_ROD, new BukkitRunnable() {
            @Override
            public void run() {
                trainingDummy.setHealth(mobStats.getMaxHealth());

                if (trainingDummy.getLocation().toVector().subtract(location.toVector()).length() > .5) {
                    trainingDummy.teleport(location);
                }
            }
        });
    }
}
