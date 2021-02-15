package me.delected.tcontrols;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerOptions {
    JavaPlugin plugin = TControls.plugin;

    public double getNeutralSpeed() {
        try {
            return plugin.getConfig().getDouble("neutral_speed");
        } catch (Exception e) {
            System.out.println("There is an error in your config's 'neutral_speed' value, so we've set it to 4.0 for you.");
            return 4.0;
        }
    }
    public boolean getActionBarEnabled() {
        return plugin.getConfig().getBoolean("action_bar_enabled", true);
    }

}
