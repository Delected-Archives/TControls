package me.delected.tcontrols.events;

import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import me.delected.tcontrols.DriverFile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class EnterVehicleEvent implements Listener {
    @EventHandler
    public void onPlayerEnterMinecart(VehicleEnterEvent e) {
        if (!(e.getEntered() instanceof Player)) return;

        Player p = (Player) e.getEntered();

        if (!(DriverFile.isInDriverMode(p))) return;

        if (MinecartMemberStore.getFromEntity(e.getVehicle()) == null) return;


        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: N/A"));
    }
}
