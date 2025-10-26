package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLMobs extends JavaPlugin {
    private MobStatsYMLConfig mobStatsYmlConfig;
    private MobStatsYMLManager mobStatsYMLManager;

    @Override
    public void onEnable() {
        mobStatsYmlConfig = new MobStatsYMLConfig(this, "mobstats");
        mobStatsYMLManager = new MobStatsYMLManager(this);
        mobStatsYmlConfig.loadConfig();

        getCommand("nmlsummon").setExecutor(new NMLSummonCommand(this));
        getServer().getPluginManager().registerEvents(new MobsListener(this), this);
    }

    public MobStatsYMLConfig getMobProfileConfig() {
        return mobStatsYmlConfig;
    }

    public MobStatsYMLManager getMobProfileManager() {
        return mobStatsYMLManager;
    }
}
