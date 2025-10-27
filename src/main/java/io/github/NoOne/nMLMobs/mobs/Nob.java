package io.github.NoOne.nMLMobs.mobs;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobstats.MobStats;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;

public class Nob {
    public Nob(NMLMobs nmlMobs, Location location) {
        Slime nob = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        MobStats mobStats = nmlMobs.getMobStatsYMLManager().getMobStatsFromYml("§aNob the Glob");

        nob.setCustomName("§fLv. §b" + mobStats.getLevel() + " §aNob the Glob");
        nob.setCustomNameVisible(true);
        nob.setSize(2);
        nob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mobStats.getMaxHealth());
        nob.getAttribute(Attribute.SCALE).setBaseValue(.5);
        nob.setHealth(mobStats.getMaxHealth());
        nob.setMetadata("nml", new FixedMetadataValue(nmlMobs, true));
    }
}
