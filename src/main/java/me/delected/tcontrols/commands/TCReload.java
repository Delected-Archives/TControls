package me.delected.tcontrols.commands;

import me.delected.tcontrols.TControls;
import net.kyori.adventure.platform.facet.Facet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TCReload implements CommandExecutor {
    static JavaPlugin plugin = TControls.plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("tcontrols.reload") || commandSender instanceof ConsoleCommandSender) {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "You are missing the permission " + ChatColor.ITALIC + "tcontrols.reload" + ChatColor.RED + "!");
        return true;
    }
}
