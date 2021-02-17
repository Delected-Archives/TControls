package me.delected.tcontrols.commands;

import me.delected.tcontrols.DisplaySpeedTask;
import me.delected.tcontrols.DriverFile;
import me.delected.tcontrols.ServerOptions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class TCDebug implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("tcontrols.debug") || commandSender instanceof ConsoleCommandSender) {
            ServerOptions so = new ServerOptions();
            commandSender.sendMessage("---------- DEBUG ----------");
            commandSender.sendMessage("- Task Debugger -");
            commandSender.sendMessage("Players in taskList: " + DriverFile.taskList.toString());
            commandSender.sendMessage("Is task running?: " + !DriverFile.taskList.isEmpty());
            commandSender.sendMessage("- Config Debugger -");
            commandSender.sendMessage("Neutral speed: " + so.getNeutralSpeed());
            commandSender.sendMessage("Action bar enabled?: " + so.getNeutralSpeed());
            commandSender.sendMessage("Max speed: " + so.getMaxSpeed());
            commandSender.sendMessage("Block distance: " + so.getBlockDistance());
            commandSender.sendMessage("---------------------------");
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "You are missing the permission " + ChatColor.ITALIC + "tcontrols.debug" + ChatColor.RED + "!");
        return true;
    }
}
