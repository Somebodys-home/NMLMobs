package io.github.NoOne.nMLMobs;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MobHealthBarManager {
    private NMLMobs nmlMobs;
    private BukkitTask healthBarTask;
    private final HashMap<UUID, LivingEntity> lastDamagedMob = new HashMap<>();
    private final HashMap<UUID, BossBar> healthBars = new HashMap<>();
    private final HashMap<UUID, Integer> healthBarTimers = new HashMap<>();
    private final int healthBarTimer = 100;

    public MobHealthBarManager(NMLMobs nmlMobs) {
        this.nmlMobs = nmlMobs;
    }

    public void start() {
        healthBarTask = Bukkit.getScheduler().runTaskTimer(nmlMobs, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                int timeLeft = healthBarTimers.getOrDefault(uuid, 0);
                BossBar bar = healthBars.get(uuid);
                LivingEntity mob = lastDamagedMob.get(uuid);

                if (bar == null || mob == null || mob.isDead()) {
                    removePlayerData(uuid);
                    continue;
                }

                double progress = Math.max(0, mob.getHealth() / mob.getMaxHealth());
                bar.setProgress(progress);
                bar.setTitle(removeLevelPrefix(mob.getName()) + " §f- §c" + (int) mob.getHealth() + " ❤");

                if (timeLeft > 0) {
                    if (!bar.getPlayers().contains(player) && !bar.getPlayers().contains(player)) bar.addPlayer(player);
                    healthBarTimers.put(uuid, timeLeft - 2);
                } else {
                    removePlayerData(uuid);
                }
            }

        }, 0L, 2L); // every 2 ticks
    }

    public void stop() {
        if (healthBarTask != null) {
            healthBarTask.cancel();
        }

        healthBars.clear();
        lastDamagedMob.clear();
        healthBarTimers.clear();
    }

    public void updateLastHitMob(Player player, LivingEntity mob) {
        UUID uuid = player.getUniqueId();
        LivingEntity prevMob = lastDamagedMob.get(uuid);

        if (prevMob == null || !prevMob.equals(mob)) { // new mob
            BossBar oldBar = healthBars.get(uuid);

            if (oldBar != null) {
                oldBar.removePlayer(player);
            }

            lastDamagedMob.put(uuid, mob);
            healthBarTimers.put(uuid, healthBarTimer);
            BossBar newBar = generateHealthBar(mob);
            newBar.addPlayer(player); // optional, ensures visibility immediately
            healthBars.put(uuid, newBar);
        } else { // same mob
            BossBar healthBar = healthBars.get(uuid);
            if (healthBar != null) {
                healthBar.setTitle(removeLevelPrefix(mob.getName()) + " §f- §c" + (int) mob.getHealth() + " ❤");
                healthBar.setProgress(mob.getHealth() / mob.getMaxHealth());
            }
            healthBarTimers.put(uuid, healthBarTimer);
        }
    }

    private BossBar generateHealthBar(LivingEntity mob) {
        int health = (int) mob.getHealth();
        String name = mob.getName() + " §f- §c" + health + " ❤";

        BossBar newBar = Bukkit.createBossBar(removeLevelPrefix(name), BarColor.RED, BarStyle.SEGMENTED_6);
        newBar.setProgress(health / mob.getMaxHealth());

        return newBar;
    }

    private void removePlayerData(UUID uuid) {
        BossBar bar = healthBars.remove(uuid);
        if (bar != null) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) bar.removePlayer(player);
        }
        lastDamagedMob.remove(uuid);
        healthBarTimers.remove(uuid);
    }

    private String removeLevelPrefix(String string) {
        return string.replaceAll("§.Lv\\. §.\\d+\\s*", "");
    }

    public void clearPlayerCache(Player player) {
        UUID uuid = player.getUniqueId();

        lastDamagedMob.remove(uuid);
        healthBars.remove(uuid);
        healthBarTimers.remove(uuid);
    }

    public LivingEntity getLastDamagedMob(Player player) {
        return lastDamagedMob.get(player.getUniqueId());
    }
}
