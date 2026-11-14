package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLMobs.mobs.NMLMob;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class NMLMobSystem {
    private static NMLMobs nmlMobs = NMLMobs.getInstance();
    private static HashMap<NMLMob, LivingEntity> nmlMobsMap = new HashMap<>(); // tracks every nml mob and the object tied to it on the server

    public static void startNoDamageTickTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // ticks down the no damage map for every nml mob and removes any mob that's dead in there
                nmlMobsMap.entrySet().removeIf(nmlMobEntityEntry -> nmlMobEntityEntry.getValue().isDead());
                nmlMobsMap.forEach((key, _) -> key.tickDownMap());
            }
        }.runTaskTimer(nmlMobs, 0L, 1L);
    }

    public static LivingEntity makeNMlMob(NMLMobs nmlMobs, Location location, EntityType entityType, String name, boolean showLevel) {
        LivingEntity nmlMob = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml(name);

        if (showLevel) name = "§7Lv. " + mobStats.getLevel() + " " + name;

        nmlMob.setCustomName(name);
        nmlMob.setCustomNameVisible(true);
        nmlMob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mobStats.getStatValue(PlayerStat.MAX_HEALTH));
        nmlMob.setHealth(mobStats.getStatValue(PlayerStat.MAX_HEALTH));
        nmlMob.setPersistent(false);

        return nmlMob;
    }

    public static void useAbility(Entity entity, double chargeTime, Particle particle, BukkitRunnable ability) {
        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                timer++;

                if (entity.isDead()) {
                    cancel();
                    return;
                }

                double radius = 3 * (1.15 - (timer / chargeTime));
                int particleCount = 30;

                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    Location particleLocation = entity.getLocation().clone().add(x, 0.15, z);

                    entity.getLocation().getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0);
                }

                if (timer == chargeTime) {
                    cancel();
                    ability.runTaskLater(nmlMobs, 1L);
                }
            }
        }.runTaskTimer(nmlMobs, 0L, 1L);
    }

    public static void addNMLMob(NMLMob nmlMob, LivingEntity livingEntity) {
        nmlMobsMap.put(nmlMob, livingEntity);
    }

    public static boolean isNMLMob(LivingEntity livingEntity) {
        return nmlMobsMap.containsKey(livingEntity);
    }

    public static NMLMob getNmlMob(LivingEntity livingEntity) {
        for (HashMap.Entry<NMLMob, LivingEntity> entry : nmlMobsMap.entrySet()) {
            if (entry.getValue().equals(livingEntity)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static Entity getEntity(NMLMobs nmlMobs) {
        return nmlMobsMap.get(nmlMobs);
    }
}
