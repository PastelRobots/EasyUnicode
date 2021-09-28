package me.pastelrobots.easyunicode.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements org.bukkit.command.TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
            if (command.getName().equalsIgnoreCase("easyunicode")) {
                if (args.length == 1) {
                    ArrayList<String> l = new ArrayList<>();
                    l.add("reload");
                    l.add("unicode");
                    return l;
                }
            }
            return null;
        }
    }
