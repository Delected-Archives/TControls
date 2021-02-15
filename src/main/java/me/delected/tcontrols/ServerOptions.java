package me.delected.tcontrols;

import org.bukkit.configuration.Configuration;

public class ServerOptions {
    Configuration config = TControls.plugin.getConfig();
    public double getNeutralSpeed() {
        try {
            return config.getDouble("neutral-speed");
        } catch (Exception e) {
            System.out.println("There is an error in your config's 'neutral-speed' value, so we've set it to 0.4 for you.");
            return 0.4;
        }
    }
    public boolean getActionBarEnabled() {
        return config.getBoolean("action-bar-enabled", true);
    }
    public double getMaxSpeed() {
        try {
            return config.getDouble("max-speed");
        } catch (Exception e) {
            System.out.println("There is an error in your config's 'max-speed' value, so we've set it to 3.0 for you.");
            return 3.0;
        }
    }
}
