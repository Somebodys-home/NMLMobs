package io.github.NoOne.nMLMobs;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageHelper;
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
    private NMLMobs nmlMobs;
    private MobHealthBarManager mobHealthBarManager;

    public MobsListener(NMLMobs nmlMobs) {
        this.nmlMobs = nmlMobs;
        mobHealthBarManager = nmlMobs.getMobHealthBarManager();
    }

    @EventHandler
    public void nmlMobDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity damager) || !(event.getEntity() instanceof LivingEntity target)) return;
        if (damager.hasMetadata("nml")) {
            MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml(damager.getName());

            event.setCancelled(true);
            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager, DamageHelper.convertStringIntMap2DamageTypes(mobStats.getAllDamages()), true));
        }
    }

    @EventHandler
    public void updateHealthBar(CustomDamageEvent event) {
        if ((!event.isCancelled() || !event.getDamageSplits().isEmpty()) && // if damage was actually dealt
            (event.getTarget() instanceof LivingEntity target && !target.hasMetadata("hologram")) && // and its a valid target that isnt a hologram
            event.getDamager() instanceof Player player && !player.equals(target)) { // and the player's damages something else

            mobHealthBarManager.updateLastHitMob(player, target);
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
