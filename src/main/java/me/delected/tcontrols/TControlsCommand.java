package me.delected.tcontrols;

import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

public class TControlsCommand implements CommandExecutor {

    ServerOptions so = new ServerOptions();

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

        if (DriverYaml.isInDriverMode(p)) {
            File f = new File(Bukkit.getServer().getPluginManager().getPlugin("TControls").getDataFolder(), p.getUniqueId().toString() + ".txt");

            try {

                p.getInventory().clear();
                p.getInventory().setContents(DriverYaml.getSavedPlayerInventory(DriverYaml.readFile(f, StandardCharsets.US_ASCII)));
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
                if (f.delete()) {
                    p.sendMessage(ChatColor.GREEN + "Removed you from the driver mode! Here are your items back!");
                } else {
                    p.sendMessage(ChatColor.RED + "There was an error deleting your file, please contact a staff member.");
                }
                return true;
            } catch (IOException e) {
                p.sendMessage(ChatColor.RED + "There was an error removing you from the driver mode. Check your console for more info.");
                e.printStackTrace();
                return true;
            }

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
            displayContents(p);
            if (so.getActionBarEnabled()) {
                displayActionBar(p);
                p.sendMessage("debug line 85 tcontrolscmd");
            }
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return true;
        }

    }

    private void displayContents(Player p) {
        // clear inv
        p.getInventory().clear();

        // brake
        ItemStack minusSpeed = new ItemStack(Material.RED_CONCRETE);
        ItemMeta minusSpeedMeta = minusSpeed.getItemMeta();
        minusSpeedMeta.setDisplayName(ChatColor.DARK_RED + "Brake (-)");
        minusSpeedMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to slow down (apply brakes)"));
        minusSpeed.setItemMeta(minusSpeedMeta);
        p.getInventory().setItem(0, minusSpeed);

        // neutralize
        ItemStack neutralSpeed = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta neutralSpeedMeta = neutralSpeed.getItemMeta();
        neutralSpeedMeta.setDisplayName(ChatColor.YELLOW + "Neutralize (=)");
        neutralSpeedMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Sets the train speed to " + so.getNeutralSpeed()));
        neutralSpeed.setItemMeta(neutralSpeedMeta);
        p.getInventory().setItem(1, neutralSpeed);

        // power
        ItemStack plusSpeed = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta plusSpeedMeta = plusSpeed.getItemMeta();
        plusSpeedMeta.setDisplayName(ChatColor.GREEN + "Power (+)");
        plusSpeedMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to speed up (apply power)"));
        plusSpeed.setItemMeta(plusSpeedMeta);
        p.getInventory().setItem(2, plusSpeed);


        // launch
        ItemStack launch = new ItemStack(Material.LEVER);
        ItemMeta launchMeta = launch.getItemMeta();
        launchMeta.setDisplayName(ChatColor.BLUE + "Launch");
        launchMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to launch the train"));
        launch.setItemMeta(launchMeta);
        p.getInventory().setItem(7, launch);

        // force stop
        ItemStack stop = new ItemStack(Material.BARRIER);
        ItemMeta stopMeta = stop.getItemMeta();
        stopMeta.setDisplayName(ChatColor.DARK_RED + "Force Stop");
        stopMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to force stop the train", ChatColor.GRAY + "(for emergency uses only)"));
        stop.setItemMeta(stopMeta);
        p.getInventory().setItem(8, stop);
    }

    private void displayActionBar(Player p) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: N/A"));
    }
}