package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLDamages.DamageHelper;
import io.github.NoOne.nMLDamages.event.CustomDamageEvent;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobsListener implements Listener {
    private MobHealthBarManager mobHealthBarManager;

    public MobsListener(NMLMobs nmlMobs) {
        mobHealthBarManager = nmlMobs.getMobHealthBarManager();
    }

    @EventHandler
    public void nmlMobDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity damager) || !(event.getEntity() instanceof LivingEntity target)) return;
        if (NMLMobSystem.isNMLMob(damager)) {
            MobStats mobStats = NMLMobSystem.getNmlMob(damager).getMobStats();

            event.setCancelled(true);
            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager, mobStats));
        }
    }

    @EventHandler
    public void updateHealthBar(CustomDamageEvent event) {
        LivingEntity damaged = event.getDamaged();

        if ((!event.isCancelled() || !event.getDamage().isEmpty() || DamageHelper.getTotalDamage(event.getDamage()) > 0) && // if damage was actually dealt
            (!damaged.hasMetadata("hologram")) && // and if the damaged isnt a hologram
            event.getDamager() instanceof Player player && !player.equals(damaged)) { // and the player doesn't damage themselves

            mobHealthBarManager.updateLastHitMob(player, damaged);
        }
    }

    @EventHandler
    public void stopSlimeSplits(SlimeSplitEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void clearBarCache(PlayerQuitEvent event) {
        mobHealthBarManager.clearPlayerCache(event.getPlayer());
    }

    @EventHandler
    public void noKnockback(EntityKnockbackEvent event) {
        if (event.getEntity().hasMetadata("no_knockback")) {
            event.setCancelled(true);
        }
    }
}
