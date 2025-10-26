package io.github.NoOne.nMLMobs;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class MobsListener implements Listener {
    private MobStatsYMLManager mobStatsYMLManager;

    public MobsListener(NMLMobs nmlMobs) {
        mobStatsYMLManager = nmlMobs.getMobStatsYMLManager();
    }

    @EventHandler
    public void onNMLEntityDamage(EntityDamageByEntityEvent event) {
        LivingEntity damager = (LivingEntity) event.getDamager();
        LivingEntity target = (LivingEntity) event.getEntity();

        if (damager.hasMetadata("nml")) {
            event.setCancelled(true);

            MobStats mobStats = mobStatsYMLManager.getMobStatsFromYml(damager.getName());

            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(target, damager, DamageConverter.convertStringIntMap2DamageTypes(mobStats.getAllDamages())));
        }
    }

    @EventHandler
    public void stopSlimeSplits(SlimeSplitEvent event) {
        if (event.getEntity().hasMetadata("nml")) {
            event.setCancelled(true);
        }
    }
}
