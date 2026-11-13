package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLDamages.CustomDamageEvent;
import io.github.NoOne.nMLDamages.DamageHelper;
import io.github.NoOne.nMLMobs.NMLMobSystem;
import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Nob extends NMLMob {
    private boolean imGonnaJump;

    public Nob() {
        super("§aNob the Glob");
    }

    @Override
    public void spawn(NMLMobs nmlMobs, Location location) {
        this.imGonnaJump = false;
        Slime nob = (Slime) NMLMobSystem.makeNMlMob(nmlMobs, location, EntityType.SLIME, name, false);

        // make nob tiny while still able to do damage
        nob.setSize(2);
        nob.getAttribute(Attribute.SCALE).setBaseValue(.5);
        nob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mobStats.getMaxHealth());
        nob.setHealth(mobStats.getMaxHealth());
        addToMap(nob);

        // custom ai for doing le big jumps
        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity target = nob.getTarget();
                double bigJumpChance = 20;
                double bigJumpMultiplier = 0;

                if (nob.isDead()) {
                    cancel();
                    return;
                }

                // distance multiplier
                if (target != null) {
                    double distance = target.getLocation().distance(nob.getLocation());

                    distance = Math.min(distance, 10);
                    bigJumpMultiplier += (distance * .2); // + x2 chance to do a big jump if over 10 blocks away, scaling linearly
                }

                // health multiplier
                double healthRatio = nob.getHealth() / mobStats.getMaxHealth();
                bigJumpMultiplier += 4 - (4 * healthRatio); // + x4 chance to do a big jump based on low health, scaling linearly

                bigJumpChance = Math.clamp(bigJumpMultiplier * bigJumpChance, 20, 100);

                if (!imGonnaJump && Math.random() * 100 < bigJumpChance) {
                    imGonnaJump = true;

                    // whole runnable is to make sure that nob is always on the ground whenever he tries to big jump
                    // basically, nob has to stay on the ground for at least 3 ticks before he can start to charge a jump
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
                                bigJump(nmlMobs, nob, mobStats);
                                cancel();
                            }
                        }
                    }.runTaskTimer(nmlMobs, 0, 1);
                }
            }
        }.runTaskTimer(nmlMobs, 20, 20);
    }

    private void bigJump(NMLMobs nmlMobs, Slime nob, MobStats mobStats) {
        nob.setAI(false);

        NMLMobSystem.useAbility(nob, 20, Particle.ITEM_SLIME, new BukkitRunnable() {
            @Override
            public void run() {
                nob.getWorld().playSound(nob.getLocation(), Sound.BLOCK_SLIME_BLOCK_BREAK, 1f, 1f);
                nob.setAI(true);
                nob.setMetadata("no_fall_damage", new FixedMetadataValue(nmlMobs, true));
                nob.setMetadata("no_knockback", new FixedMetadataValue(nmlMobs, true));
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

                            for (int i = 0; i < particleCount; i++) {
                                double r = radius * Math.sqrt(Math.random());
                                double angle = Math.random() * 2 * Math.PI;
                                double x = r * Math.cos(angle);
                                double z = r * Math.sin(angle);

                                Location loc = base.clone().add(x, 0, z);
                                nob.getWorld().spawnParticle(Particle.ITEM_SLIME, loc, 1, 0, 0, 0, 0);
                            }

                            for (LivingEntity livingEntity : nob.getWorld().getNearbyLivingEntities(nob.getLocation(), 3, 1, 3)) {
                                if (!livingEntity.equals(nob)) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, nob,
                                            DamageHelper.multiplyDamageMap(DamageHelper.convertStringIntMap2DamageTypes(mobStats.getAllDamages()), 2), true));
                                }
                            }

                            nob.getWorld().playSound(nob.getLocation(), Sound.BLOCK_SLIME_BLOCK_BREAK, 2f, .5f);
                            nob.removeMetadata("no_fall_damage", nmlMobs);
                            nob.removeMetadata("no_knockback", nmlMobs);
                            imGonnaJump = false;
                            cancel();
                        }
                    }
                }.runTaskTimer(nmlMobs, 5L, 1L);

                cancel();
            }
        });
    }
}
