package me.delected.tcontrols.events;

import com.bergerkiller.bukkit.tc.TCConfig;
import com.bergerkiller.bukkit.tc.Util;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import com.bergerkiller.bukkit.tc.properties.TrainProperties;
import me.delected.tcontrols.DriverFile;
import me.delected.tcontrols.ServerOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ControlInteractEvents implements Listener {

    ServerOptions so = new ServerOptions();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        // player p
        Player p = e.getPlayer();

        // if player isn't in driver mode, return
        if (!DriverFile.isInDriverMode(e.getPlayer())) return;

        // compare by blocks, just assume that a player in driver mode using one of these items is a control
        ArrayList<Material> blocks = new ArrayList<Material>(Arrays.asList(Material.RED_CONCRETE, Material.YELLOW_CONCRETE,
                Material.GREEN_CONCRETE, Material.LEVER, Material.BARRIER));

        if (!p.isInsideVehicle()) e.setCancelled(true);

        if (MinecartMemberStore.getFromEntity(p.getVehicle()) == null) e.setCancelled(true);

        MinecartMember mm = MinecartMemberStore.getFromEntity(p.getVehicle());
        TrainProperties prop = mm.getProperties().getTrainProperties();

        doTrainAction(e.getBlock().getType(), mm, prop, p);

        e.setCancelled(true);

    }



    @EventHandler
    public void onUseControlEvent(PlayerInteractEvent e) {
        // player p
        Player p = e.getPlayer();

        // if player isn't in driver mode, return
        if (!DriverFile.isInDriverMode(e.getPlayer())) return;

        // just extra check to compare by names
        ArrayList<String> blocks = new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED + "Brake (-)", ChatColor.YELLOW + "Neutralize (=)",
                ChatColor.GREEN + "Power (+)", ChatColor.BLUE + "Launch", ChatColor.DARK_RED + "Force Stop"));

        // itemstack hand
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();

        // prevents npes
        if (hand == null || hand.getType() == Material.AIR || hand.getItemMeta() == null || hand.getItemMeta().getDisplayName() == null) return;

        // if blocks doesn't contain the item name, return, it is not a control
        if (!blocks.contains(Objects.requireNonNull(hand.getItemMeta()).getDisplayName())) return;



        MinecartMember mm = MinecartMemberStore.getFromEntity(p.getVehicle());
        TrainProperties prop = mm.getProperties().getTrainProperties();

        // do action
        doTrainAction(hand.getType(), mm, prop, p);
    }

    private void doTrainAction(Material mat, MinecartMember mm, TrainProperties prop, Player p) {

        if (!p.isInsideVehicle()) return;

        if (MinecartMemberStore.getFromEntity(p.getVehicle()) == null) return;

        mm.setIgnoreCollisions(true);
        mm.getProperties().getTrainProperties().setSlowingDown(false);
        mm.getProperties().getTrainProperties().setSpeedLimit(so.getMaxSpeed());

        double velo;

        switch (mat.toString()) {
            case "RED_CONCRETE":
                velo = mm.getForce() - 0.1;
                if (velo < 0.005) {
                    velo = 0;
                    mm.getActions().addActionLaunch(mm.getDirection(), 0, velo);
                    break;
                }
                mm.getActions().clear();
                mm.getGroup().getActions().clear();

                mm.getActions().addActionLaunch(mm.getDirection(), velo * so.getBlockDistance(), velo);
                break;
            case "YELLOW_CONCRETE":
                velo = so.getNeutralSpeed();
                  mm.getActions().clear();
                  mm.getGroup().getActions().clear();

                mm.getActions().addActionLaunch(mm.getDirection(), velo * so.getBlockDistance(), velo);
                break;
            case "LIME_CONCRETE":
                velo = mm.getForce() + 0.15;
                if (velo > so.getMaxSpeed()) {
                    velo = so.getMaxSpeed();
                }
                mm.getActions().clear();
                mm.getGroup().getActions().clear();
                mm.getActions().addActionLaunch(mm.getDirection(), velo * so.getBlockDistance(), velo);
                break;
            case "LEVER":
                mm.getActions().clear();
                mm.getGroup().getActions().clear();
                mm.getActions().addActionLaunch(Util.vecToFace(p.getEyeLocation().getDirection(), false), so.getBlockDistance(), so.getNeutralSpeed());
                break;
            case "BARRIER":
                prop.setSpeedLimit(0);
                break;
            default:
                p.sendMessage(ChatColor.RED + "This is not a control!");
                break;
        }
    }
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        if (DriverFile.isInDriverMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameDestroy(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof ItemFrame)) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (DriverFile.isInDriverMode((Player) e.getDamager())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameRotate(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked().getType().equals(EntityType.ITEM_FRAME))) return;
        if (DriverFile.isInDriverMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}