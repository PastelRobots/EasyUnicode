package me.pastelrobots.easyunicode;

import com.tchristofferson.configupdater.ConfigUpdater;
import me.pastelrobots.easyunicode.commands.CommandHandler;
import me.pastelrobots.easyunicode.tabcomplete.TabComplete;
import org.bukkit.ChatColor;
import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class EasyUnicode extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;
    public static Plugin plugin;
    private Set<String> commands = this.getDescription().getCommands().keySet();

    @Override
    public void onEnable() {
        plugin = this;
        createCustomConfig();
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        reloadConfig();

        Objects.requireNonNull(getCommand("easyunicode")).setExecutor(new CommandHandler());
        Objects.requireNonNull(getCommand("easyunicode")).setTabCompleter(new TabComplete());

        plugin.getLogger().info(ChatColor.BLUE + "EasyUnicode has been turned on!");
        plugin.getLogger().info(ChatColor.BLUE + "If you need help or the plugin isn't working as intended, let me know!");
    }

    @Override
    public void onDisable() {
        plugin.getLogger().info(ChatColor.RED + "EasyUnicode has been turned off!");
        plugin.getLogger().info(ChatColor.RED + "If you need help or the plugin isn't working as intended, let me know!");

    }

    private void createCustomConfig () {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
