package me.delected.tcontrols.events;

import me.delected.tcontrols.DriverFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PickDropItemEvent implements Listener {
    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (DriverFile.isInDriverMode((Player) e.getEntity())) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (DriverFile.isInDriverMode(e.getPlayer())) e.setCancelled(true);
    }
}
