package me.delected.tcontrols.events;

import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import me.delected.tcontrols.ServerOptions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ControlUseEvents implements Listener {
    @EventHandler
    public void onUseControlEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ArrayList<String> blocks = new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED + "Brake (-)", ChatColor.YELLOW + "Neutralize (=)",
                ChatColor.GREEN + "Power (+)", ChatColor.BLUE + "Launch", ChatColor.DARK_RED + "Force Stop"));
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand == null || hand.getType() == Material.AIR) return;
        if (!blocks.contains(Objects.requireNonNull(hand.getItemMeta()).getDisplayName())) return;

        if (!p.isInsideVehicle()) return;

        MinecartMember mm = MinecartMemberStore.getFromEntity(p.getVehicle());

        ServerOptions so = new ServerOptions();

        mm.setIgnoreCollisions(true);


        if (hand.getType() == Material.RED_CONCRETE) {
            if (mm.getForwardForce() > 0.2) {
                mm.setForwardForce(mm.getForwardForce() - 0.2);
                p.sendMessage("poop xd");
            } else {
                mm.setForwardForce(0.0);
                p.sendMessage("lol 0");
            }
            displaySpeed(p, mm);
        } else if (hand.getType() == Material.YELLOW_CONCRETE) {
            mm.setForwardForce(so.getNeutralSpeed());
            displaySpeed(p, mm);
        } else if (hand.getType() == Material.LIME_CONCRETE) {
            if (mm.getForwardForce() < so.getMaxSpeed()) {
                mm.setForwardForce(mm.getForwardForce() + 0.2);
            }
            else {
                mm.setForwardForce(so.getMaxSpeed());
            }
            displaySpeed(p, mm);
        } else if (hand.getType() == Material.LEVER) {
            mm.setDirectionForward();
            if (mm.getForwardForce() < so.getMaxSpeed() - 0.1) {
                mm.setForwardForce(mm.getForwardForce() + 0.1);
            }
            displaySpeed(p, mm);
        } else if (hand.getType() == Material.BARRIER) {
            mm.setForwardForce(0.0);
            displaySpeed(p, mm);
        }
    }
    private void displaySpeed(Player p, MinecartMember mm) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: " + mm.getRealSpeed()));
    }
}
