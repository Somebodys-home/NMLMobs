package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobSystem;
import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.scheduler.BukkitRunnable;

public class TrainingDummy extends NMLMob {
    public TrainingDummy() {
        super("§cTraining Dummy");
    }

    @Override
    public void spawn(NMLMobs nmlMobs, Location location) {
        Location clone = location.clone();

        clone.setPitch(0);

        IronGolem trainingDummy = (IronGolem) NMLMobSystem.makeNMlMob(nmlMobs, clone, EntityType.IRON_GOLEM, name, false);

        trainingDummy.setAI(false);
        addToMap(trainingDummy);

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
                trainingDummy.setHealth(mobStats.getStatValue(PlayerStat.MAX_HEALTH));

                if (trainingDummy.getLocation().toVector().subtract(location.toVector()).length() > .5) {
                    trainingDummy.teleport(location);
                }
            }
        });
    }
}
