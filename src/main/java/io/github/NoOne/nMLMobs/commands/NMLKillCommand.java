package io.github.NoOne.nMLMobs.commands;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobs.Nob;
import io.github.NoOne.nMLMobs.mobs.TrainingDummy;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NMLKillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int range = Integer.parseInt(args[0]);

            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
                if (entity != player && entity.hasMetadata("nml")) {
                    entity.remove();
                }
            }
        }

        return true;
    }
}
