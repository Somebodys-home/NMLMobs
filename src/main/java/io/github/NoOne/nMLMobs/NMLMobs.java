package io.github.NoOne.nMLMobs;

import io.github.NoOne.nMLMobs.commands.NMLKillCommand;
import io.github.NoOne.nMLMobs.commands.NMLSummonCommand;
import io.github.NoOne.nMLMobs.mobs.NMLMobHelper;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLManager;
import io.github.NoOne.nMLMobs.mobstats.MobStatsYMLConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLMobs extends JavaPlugin {
    private MobStatsYMLConfig mobStatsYmlConfig;
    private MobStatsYMLManager mobStatsYMLManager;
    private MobHealthBarManager mobHealthBarManager;
    private NMLMobHelper nmlMobHelper;

    @Override
    public void onEnable() {
        mobStatsYmlConfig = new MobStatsYMLConfig(this, "mobstats");

        mobStatsYMLManager = new MobStatsYMLManager(this);
        mobStatsYmlConfig.loadConfig();

        mobHealthBarManager = new MobHealthBarManager(this);
        mobHealthBarManager.start();

        nmlMobHelper = new NMLMobHelper(this);

        getCommand("nmlsummon").setExecutor(new NMLSummonCommand(this));
        getCommand("nmlkill").setExecutor(new NMLKillCommand());
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

    public NMLMobHelper getNmlMobTemplate() {
        return nmlMobHelper;
    }
}
