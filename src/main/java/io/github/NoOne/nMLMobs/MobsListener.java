package io.github.NoOne.nMLMobs;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
    public void nmlMobDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        if (!(event.getDamager() instanceof LivingEntity damager)) return;
        if (target.hasMetadata("punched")) { // recursion block
            target.removeMetadata("punched", nmlMobs.getDamagePlugin());
            return;
        }

        if (damager.hasMetadata("nml")) {
            event.setCancelled(true);

            if (target.getNoDamageTicks() == 0) {
                MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml(damager.getName());

                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager,
                        DamageConverter.convertStringIntMap2DamageTypes(mobStats.getAllDamages()), true));
            }
        }
    }

    @EventHandler
    public void updateHealthBar(CustomDamageEvent event) {
        if (!(event.getDamager() instanceof LivingEntity damager)) return;
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (damager instanceof Player player) mobHealthBarManager.updateLastHitMob(player, target);
    }

    @EventHandler
    public void stopSlimeSplits(SlimeSplitEvent event) {
        if (event.getEntity().hasMetadata("nml")) event.setCancelled(true);
    }

    @EventHandler
    public void clearBarCache(PlayerQuitEvent event) {
        mobHealthBarManager.clearPlayerCache(event.getPlayer());
    }

    @EventHandler
    public void fallingFromAbilityUse(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity.hasMetadata("nml") && entity.hasMetadata("falling") && event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
    }
}
