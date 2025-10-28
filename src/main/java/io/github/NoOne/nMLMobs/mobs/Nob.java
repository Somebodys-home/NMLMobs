package io.github.NoOne.nMLMobs.mobs;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Nob {
    private NMLMobs nmlMobs;

    public Nob(NMLMobs nmlMobs, Location location) {
        this.nmlMobs = nmlMobs;
        Slime nob = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml("§aNob the Glob");

        nob.setCustomName("§fLv. §b" + mobStats.getLevel() + " §aNob the Glob");
        nob.setCustomNameVisible(true);
        nob.setSize(2);
        nob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mobStats.getMaxHealth());
        nob.getAttribute(Attribute.SCALE).setBaseValue(.5);
        nob.setHealth(mobStats.getMaxHealth());
        nob.setPersistent(true);
        nob.setMetadata("nml", new FixedMetadataValue(nmlMobs, true));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (nob.isDead()) {
                    cancel();
                    return;
                }

                if (nob.getTarget() != null) {
                    new BukkitRunnable() {
                        int groundedTicks = 0;

                        @Override
                        public void run() {
                            if (nob.isDead()) {
                                cancel();
                                return;
                            }

                            if (nob.isOnGround()) {
                                groundedTicks++;
                            } else {
                                groundedTicks = 0;
                            }

                            if (groundedTicks >= 3) {
                                bigJump(nob, mobStats);
                                cancel();
                            }
                        }
                    }.runTaskTimer(nmlMobs, 0L, 1L);
                } else if (nob.isDead()) {
                    cancel();
                }
            }
        }.runTaskTimer(nmlMobs, 0L, 150L);
    }

    private void bigJump(Slime nob, MobStats mobStats) {
        nob.setAI(false);

        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                timer++;

                double radius = 3 * (1 - (timer / 35.0));
                int particleCount = 75;
                Location base = nob.getLocation().clone();

                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    Location particleLocation = base.clone().add(x, 0, z);

                    nob.getWorld().spawnParticle(Particle.ITEM_SLIME, particleLocation, 1, 0, 0, 0, 0);
                }

                if (timer == 30) {
                    nob.getWorld().playSound(nob.getLocation(), Sound.BLOCK_SLIME_BLOCK_BREAK, 1f, 1f);
                    nob.setAI(true);
                    nob.setMetadata("falling", new FixedMetadataValue(nmlMobs, true));
                    nob.setVelocity(new Vector());

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            nob.setVelocity(nob.getLocation().getDirection().multiply(.5).setY(1));
                        }
                    }.runTaskLater(nmlMobs, 1L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (nob.isDead()) {
                                cancel();
                                return;
                            }

                            if (nob.isOnGround()) {
                                double radius = 3;
                                int particleCount = 200;
                                Location base = nob.getLocation().clone().add(0, 0.5, 0);
                                HashSet<UUID> hitEntityUUIDs = new HashSet<>();

                                for (int i = 0; i < particleCount; i++) {
                                    double r = radius * Math.sqrt(Math.random());
                                    double angle = Math.random() * 2 * Math.PI;
                                    double x = r * Math.cos(angle);
                                    double z = r * Math.sin(angle);

                                    Location loc = base.clone().add(x, 0, z);
                                    nob.getWorld().spawnParticle(Particle.ITEM_SLIME, loc, 1, 0, 0, 0, 0);
                                }

                                for (Entity entity : nob.getWorld().getNearbyEntities(nob.getLocation(), 3, 1, 3)) {
                                    if (!entity.equals(nob)) {
                                        hitEntityUUIDs.add(entity.getUniqueId());
                                    }
                                }

                                for (UUID uuid : hitEntityUUIDs) {
                                    if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, nob,
                                        DamageConverter.multiplyDamageMap(DamageConverter.convertStringIntMap2DamageTypes(mobStats.getAllDamages()), 2), true));
                                    }
                                }

                                nob.getWorld().playSound(nob.getLocation(), Sound.BLOCK_SLIME_BLOCK_BREAK, 2f, .5f);
                                nob.removeMetadata("falling", nmlMobs);
                                cancel();
                            }
                        }
                    }.runTaskTimer(nmlMobs, 5L, 1L);

                    cancel();
                }
            }
        }.runTaskTimer(nmlMobs, 0L, 1L);
    }
}
