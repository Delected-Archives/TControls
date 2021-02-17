package me.delected.tcontrols.events;

import me.delected.tcontrols.DisplaySpeedTask;
import me.delected.tcontrols.DriverFile;
import me.delected.tcontrols.ServerOptions;
import me.delected.tcontrols.TControls;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerJoinQuitEvents implements Listener {

    ServerOptions so = new ServerOptions();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!DriverFile.taskList.contains(p)) return;
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
        DriverFile.taskList.remove(p);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!DriverFile.isInDriverMode(e.getPlayer())) return;
        if (DriverFile.taskList.isEmpty() && so.getActionBarEnabled()) {
            BukkitTask task = new DisplaySpeedTask().runTaskTimer(TControls.plugin, 0L, 5L);
        }
        DriverFile.taskList.add(e.getPlayer());
    }
}
