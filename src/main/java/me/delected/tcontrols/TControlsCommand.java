package me.delected.tcontrols;

import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.Objects;


public class TControlsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("tcontrols.toggle")) {
            p.sendMessage(ChatColor.RED + "You are missing the permission " + ChatColor.ITALIC + "tcontrols.toggle" + ChatColor.RED + "!");
            return true;
        }

        if(DriverYaml.isInDriverMode(p)) {
            System.out.println("yay");
        } else {
            System.out.println("aww");
        }

        if (!(p.isInsideVehicle())) {
            p.sendMessage(ChatColor.YELLOW + "You must be inside a train to run this command!");
            return true;
        }

        if (MinecartMemberStore.getFromEntity(p.getVehicle()) == null) {
            p.sendMessage(ChatColor.YELLOW + "You are not in a TrainCarts train!");
            return true;
        }


        if (!(MinecartMemberStore.getFromEntity(p.getVehicle())).getProperties().getOwners().contains(p.getName().toLowerCase())) {

            p.sendMessage(ChatColor.YELLOW + "You have to be an owner of this train to use TControls! Use " + ChatColor.ITALIC + "/train claim" + ChatColor.YELLOW + ".");
            return true;
        }

        // success
        try {
            DriverYaml.savePlayerInventory(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }

    private void displayContents() {
        // +speed small
        // +speed large
        // -speed small
        // -speed large
        // stop train
        // set speed to neutral
        // launch train (left click forward, right click backward)
    }
}
