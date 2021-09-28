package me.pastelrobots.easyunicode.commands;

import me.pastelrobots.easyunicode.EasyUnicode;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class UCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if (args.length >= 3) {
                if (args[0].equals("unicode")) {
                    if (p.hasPermission("easyunicode.unicode")) {
                        String letter = args[1].toLowerCase();

                        int number = Integer.parseInt(args[2]);

                        String url = "https://charbase.com/" + letter;

                        if (number <= 9) {
                            url = url + "00" + number;
                        } else if (number <= 99) {
                            url = url + "0" + number;
                        } else if (number <= 999) {
                            url = url + number;
                        }
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Elements rows = doc.select("tr");
                        Element columns = rows.select("td").first();
                        assert columns != null;
                        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(EasyUnicode.plugin.getConfig().getString("modules.unicode-grabber.message")).replace("%unicode%", columns.text()))));
                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click me to put this unicode in your chat box!")));
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, columns.text()));
                        message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                        p.spigot().sendMessage(message);

                    } else {
                        p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                        return true;
                    }
                }
            } else if (args.length == 2) {
                if (p.hasPermission("easyunicode.unicode")) {
                    String letter = args[1].toLowerCase();

                    String url = "https://charbase.com/" + letter;

                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements rows = doc.select("tr");
                    Element columns = rows.select("td").first();
                    assert columns != null;
                    TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', EasyUnicode.plugin.getConfig().getString("modules.unicode-grabber.message")).replace("%unicode%", columns.text()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click me to put this unicode in your chat box!")));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, columns.text()));
                    message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                    p.spigot().sendMessage(message);

                } else {
                    p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                    return true;
                }
            }
        }
        return false;
    }
}
