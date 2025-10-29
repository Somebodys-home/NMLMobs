package io.github.NoOne.nMLMobs.commands;

import io.github.NoOne.nMLMobs.NMLMobs;
import io.github.NoOne.nMLMobs.mobs.Nob;
import io.github.NoOne.nMLMobs.mobs.TrainingDummy;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NMLSummonCommand implements CommandExecutor, TabCompleter {
    private NMLMobs nmlMobs;

    public NMLSummonCommand(NMLMobs nmlMobs) {
        this.nmlMobs = nmlMobs;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Location baseLocation = player.getLocation();
            Vector forward = baseLocation.getDirection().setY(0).normalize().multiply(3);
            Location spawnLocation = baseLocation.clone().add(forward);

            switch (args[0]) {
                case "nob" -> new Nob(nmlMobs, spawnLocation);
                case "dummy" -> new TrainingDummy(nmlMobs, spawnLocation);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(List.of("nob", "dummy")).stream()
                    .filter(string -> string.toLowerCase().startsWith(strings[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}
