package me.delected.tcontrols;

import com.bergerkiller.bukkit.common.utils.MathUtil;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DisplaySpeedTask extends BukkitRunnable {

    JavaPlugin plugin = TControls.plugin;

    ServerOptions so = new ServerOptions();
    @Override
    public void run() {
        if (DriverFile.taskList.isEmpty() || !so.getActionBarEnabled()) {
            this.cancel();
        }

        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (!DriverFile.taskList.contains(plr)) continue;
            if (!canDisplay(plr)) {
                plr.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: N/A"));
            } else {
                // src taken from:
                // https://github.com/bergerhealer/TrainCarts/blob/dbe980091801499934bd9702a866d2e39bcd8e0f/src/main/java/com/bergerkiller/bukkit/tc/commands/TrainCommands.java
                MinecartMember mm =  MinecartMemberStore.getFromEntity(plr.getVehicle());
                double speedUnclipped = mm.getProperties().getTrainProperties().getHolder().getAverageForce();
                double speedClipped = Math.min(speedUnclipped, mm.getProperties().getTrainProperties().getSpeedLimit());
                double speedMomentum = (speedUnclipped - speedClipped);
                double speed = MathUtil.round(speedClipped, 3) < 0 ? 0.0 : MathUtil.round(speedClipped, 3);
                plr.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: " + speed));
            }
        }
    }

    // returns true if the player is inside a tc vehicle, otherwise returning false.
    private static boolean canDisplay(Player p) {
        if (!(p.isInsideVehicle())) return false;
        return MinecartMemberStore.getFromEntity(p.getVehicle()) != null;
    }
}
