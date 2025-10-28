package io.github.NoOne.nMLMobs;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobsListener implements Listener {
    private MobHealthBarManager mobHealthBarManager;

    public MobsListener(NMLMobs nmlMobs) {
        mobHealthBarManager = nmlMobs.getMobHealthBarManager();
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
