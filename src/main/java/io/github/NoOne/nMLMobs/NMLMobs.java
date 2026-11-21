package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLConfig.ConfigTemplate;
import io.github.NoOne.nMLMobs.commands.NMLKillCommand;
import io.github.NoOne.nMLMobs.commands.NMLSummonCommand;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NMLMobs extends JavaPlugin {
    private static NMLMobs instance;
    private ConfigTemplate mobStatsConfig;
    private MobStatsYMLManager mobStatsYMLManager;
    private MobHealthBarManager mobHealthBarManager;

    @Override
    public void onEnable() {
        instance = this;
        mobStatsConfig = new ConfigTemplate(this, "mobstats");

        mobStatsYMLManager = new MobStatsYMLManager(this);
        mobStatsConfig.loadConfig();

        mobHealthBarManager = new MobHealthBarManager(this);
        mobHealthBarManager.start();

        NMLMobSystem.startDeadCheckTask();

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

    public ConfigTemplate getMobProfileConfig() {
        return mobStatsConfig;
    }

    public MobStatsYMLManager getMobStatsYMLManager() {
        return mobStatsYMLManager;
    }

    public MobHealthBarManager getMobHealthBarManager() {
        return mobHealthBarManager;
    }
}
