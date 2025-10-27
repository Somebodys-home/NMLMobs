package io.github.NoOne.nMLMobs;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MobsListener implements Listener {
    private MobStatsYMLManager mobStatsYMLManager;
    private MobHealthBarManager mobHealthBarManager;

    public MobsListener(NMLMobs nmlMobs) {
        mobStatsYMLManager = nmlMobs.getMobStatsYMLManager();
        mobHealthBarManager = nmlMobs.getMobHealthBarManager();
    }

    @EventHandler
    public void onNMLEntityDamage(CustomDamageEvent event) {
        if (!(event.getDamager() instanceof LivingEntity damager)) return;
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (damager instanceof Player player) mobHealthBarManager.updateLastHitMob(player, target);

        if (damager.hasMetadata("nml")) {
            event.setCancelled(true);

            MobStats mobStats = mobStatsYMLManager.getMobStatsFromYml(damager.getName().replaceAll("§.Lv\\. §.\\d+\\s*", ""));

            if (mobStats != null) {
                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager, DamageConverter.convertStringIntMap2DamageTypes(mobStats.getAllDamages())));
            }
        }
    }

    @EventHandler
    public void stopSlimeSplits(SlimeSplitEvent event) {
        if (event.getEntity().hasMetadata("nml")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void clearBarCache(PlayerQuitEvent event) {
        mobHealthBarManager.clearPlayerCache(event.getPlayer());
    }
}
