package io.github.NoOne.nMLMobs;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.UUID;

public class MobHealthBarManager {
    private NMLMobs nmlMobs;
    private BukkitTask healthBarTask;
    private HashMap<UUID, LivingEntity> lastDamagedMob = new HashMap<>();
    private HashMap<UUID, BossBar> healthBars = new HashMap<>();
    private HashMap<UUID, Integer> healthBarTimers = new HashMap<>();
    private int healthBarTimer = 100;

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
                bar.setTitle(removeLevelPrefix(mob.getName()) + " §f- §c" + shortenHealth(mob.getHealth()) + " ❤");

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
                healthBar.setTitle(removeLevelPrefix(mob.getName()) + " §f- §c" + shortenHealth(mob.getHealth()) + " ❤");
                healthBar.setProgress(mob.getHealth() / mob.getMaxHealth());
            }

            healthBarTimers.put(uuid, healthBarTimer);
        }
    }

    public void clearPlayerCache(Player player) {
        UUID uuid = player.getUniqueId();

        lastDamagedMob.remove(uuid);
        healthBars.remove(uuid);
        healthBarTimers.remove(uuid);
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

    private String shortenHealth(double health) {
        String string;

        if (health < 1000) {
            string = String.valueOf(health);
        } else if (health < 1000000) {
            string = new BigDecimal(health / 1000).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros() + "K";
        } else {
            string = new BigDecimal(health / 1000000).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros() + "M";
        }

        if (string.endsWith(".0")) {
            string = string.replace(".0", "");
        }

        return string;
    }

    private BossBar generateHealthBar(LivingEntity mob) {
        double health = mob.getHealth();
        double progress = mob.getHealth() / mob.getAttribute(Attribute.MAX_HEALTH).getValue();
        String name = mob.getName() + " §f- §c" + shortenHealth(health) + " ❤";

        BossBar newBar = Bukkit.createBossBar(removeLevelPrefix(name), BarColor.RED, BarStyle.SEGMENTED_6);
        newBar.setProgress(progress);

        return newBar;
    }
}
