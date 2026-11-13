package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLMobs.commands.NMLKillCommand;
import io.github.NoOne.nMLMobs.commands.NMLSummonCommand;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLConfig;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLMobs extends JavaPlugin {
    private static NMLMobs instance;
    private MobStatsYMLConfig mobStatsYmlConfig;
    private MobStatsYMLManager mobStatsYMLManager;
    private MobHealthBarManager mobHealthBarManager;

    @Override
    public void onEnable() {
        instance = this;
        mobStatsYmlConfig = new MobStatsYMLConfig(this, "mobstats");

        mobStatsYMLManager = new MobStatsYMLManager(this);
        mobStatsYmlConfig.loadConfig();

        mobHealthBarManager = new MobHealthBarManager(this);
        mobHealthBarManager.start();

        NMLMobSystem.startNoDamageTickTask();

        getCommand("nmlsummon").setExecutor(new NMLSummonCommand(this));
        getCommand("nmlkill").setExecutor(new NMLKillCommand());
        getServer().getPluginManager().registerEvents(new MobsListener(this), this);
    }

    @Override
    public void onDisable() {
        mobHealthBarManager.stop();
    }

    public static NMLMobs getInstance() {
        return instance;
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
}
