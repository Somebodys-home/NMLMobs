package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class NMLMobTemplate {
    private static NMLMobs nmlMobs;

    public NMLMobTemplate(NMLMobs nmlMobs) {
        this.nmlMobs = nmlMobs;
    }

    public static LivingEntity makeNMlMob(NMLMobs nmlMobs, Location location, EntityType entityType, String name, boolean showLevel) {
        LivingEntity nmlMob = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml(name);

        if (showLevel) name = "§7Lv. " + mobStats.getLevel() + " " + name;

        nmlMob.setCustomName(name);
        nmlMob.setCustomNameVisible(true);
        nmlMob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mobStats.getMaxHealth());
        nmlMob.setHealth(mobStats.getMaxHealth());
        nmlMob.setPersistent(false);
        nmlMob.setMetadata("nml", new FixedMetadataValue(nmlMobs, true));
        
        return nmlMob;
    }

    public static void useAbility(LivingEntity livingEntity, double chargeTime, Particle particle, BukkitRunnable ability) {
        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                timer++;

                if (livingEntity.isDead()) {
                    cancel();
                    return;
                }

                double radius = 3 * (1.15 - (timer / chargeTime));
                int particleCount = 30;

                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    Location particleLocation = livingEntity.getLocation().clone().add(x, 0.15, z);

                    livingEntity.getLocation().getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0);
                }

                if (timer == chargeTime) {
                    cancel();
                    ability.runTaskLater(nmlMobs, 1L);
                }
            }
        }.runTaskTimer(nmlMobs, 0L, 1L);
    }
}
