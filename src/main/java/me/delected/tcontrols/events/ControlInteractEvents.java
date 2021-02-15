package me.delected.tcontrols.events;

import me.delected.tcontrols.DriverYaml;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ControlInteractEvents implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (DriverYaml.isInDriverMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        if (DriverYaml.isInDriverMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameDestroy(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof ItemFrame)) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (DriverYaml.isInDriverMode((Player) e.getDamager())) {
                    e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameRotate(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked().getType().equals(EntityType.ITEM_FRAME))) return;
        if (DriverYaml.isInDriverMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}