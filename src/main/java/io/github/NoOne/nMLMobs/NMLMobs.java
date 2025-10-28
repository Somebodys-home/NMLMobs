package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLMobs.mobs.NMLMobTemplate;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLMobs extends JavaPlugin {
    private MobStatsYMLConfig mobStatsYmlConfig;
    private MobStatsYMLManager mobStatsYMLManager;
    private MobHealthBarManager mobHealthBarManager;
    private NMLMobTemplate nmlMobTemplate;

    @Override
    public void onEnable() {
        mobStatsYmlConfig = new MobStatsYMLConfig(this, "mobstats");

        mobStatsYMLManager = new MobStatsYMLManager(this);
        mobStatsYmlConfig.loadConfig();

        mobHealthBarManager = new MobHealthBarManager(this);
        mobHealthBarManager.start();

        nmlMobTemplate = new NMLMobTemplate(this);

        getCommand("nmlsummon").setExecutor(new NMLSummonCommand(this));
        getServer().getPluginManager().registerEvents(new MobsListener(this), this);
    }

    @Override
    public void onDisable() {
        mobHealthBarManager.stop();
    }

    public MobStatsYMLConfig getMobProfileConfig() {
        return mobStatsYmlConfig;
    }

    public MobStatsYMLManager getMobStatsYMLManager() {
        return mobStatsYMLManager;
    }

    public MobHealthBarManager getMobHealthBarManager() {
        return mobHealthBarManager;
    }

    public NMLMobTemplate getNmlMobTemplate() {
        return nmlMobTemplate;
    }
}
