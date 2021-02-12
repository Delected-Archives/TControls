package me.delected.tcontrols;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ControlPlaceEvent implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        // if the player is in driver mode (check custom yaml)
        // send "you cannot place blocks while in driver mode! use /tcontrols to get out!
    }
}
