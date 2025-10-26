package io.github.NoOne.nMLMobs;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class MobsListener implements Listener {
    private MobStatsYMLManager mobStatsYMLManager;

    public MobsListener(NMLMobs nmlMobs) {
        mobStatsYMLManager = nmlMobs.getMobProfileManager();
    }

    @EventHandler
    public void onNMLEntityDamage(EntityDamageByEntityEvent event) {
        LivingEntity damager = (LivingEntity) event.getDamager();
        LivingEntity target = (LivingEntity) event.getEntity();

        if (damager.hasMetadata("nml")) {
            event.setCancelled(true);

            String name = damager.getName();
            if (name.contains("§")) {
                StringBuilder stringBuilder = new StringBuilder(name);
                int index = name.indexOf("§");

                stringBuilder.deleteCharAt(index + 1);
                stringBuilder.deleteCharAt(index);
                name = stringBuilder.toString();

                MobStats mobStats = mobStatsYMLManager.getMobStatsFromYml(name);

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
}
