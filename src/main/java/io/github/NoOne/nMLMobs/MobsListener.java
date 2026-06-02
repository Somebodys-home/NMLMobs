package io.github.NoOne.nMLMobs;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageHelper;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobsListener implements Listener {
    private NMLMobs nmlMobs;
    private MobHealthBarManager mobHealthBarManager;

    public MobsListener(NMLMobs nmlMobs) {
        this.nmlMobs = nmlMobs;
        mobHealthBarManager = nmlMobs.getMobHealthBarManager();
    }

    @EventHandler
    public void nmlMobDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player target)) return;
        if (!(event.getDamager() instanceof LivingEntity damager)) return;
        if (target.getNoDamageTicks() > 0) return;

        if (damager.hasMetadata("nml")) {
            event.setCancelled(true);

            MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml(damager.getName());

            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager, DamageHelper.convertStringIntMap2DamageTypes(mobStats.getAllDamages()), true));
        }
    }

    @EventHandler
    public void updateHealthBar(CustomDamageEvent event) {
        if (event.getTarget() instanceof LivingEntity target && !target.hasMetadata("hologram") && event.getDamager() instanceof Player player) {
            mobHealthBarManager.updateLastHitMob(player, target);
        }
    }

    @EventHandler
    public void stopSlimeSplits(SlimeSplitEvent event) {
        if (event.getEntity().hasMetadata("nml")) event.setCancelled(true);
    }

    @EventHandler
    public void clearBarCache(PlayerQuitEvent event) {
        mobHealthBarManager.clearPlayerCache(event.getPlayer());
    }
}
