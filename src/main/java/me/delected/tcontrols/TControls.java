package me.delected.tcontrols;

import me.delected.tcontrols.events.ControlInteractEvents;
import me.delected.tcontrols.events.EnterVehicleEvent;
import me.delected.tcontrols.events.LeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class TControls extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Objects.requireNonNull(getCommand("tcontrols")).setExecutor(new TControlsCommand());
        getServer().getPluginManager().registerEvents(new ControlInteractEvents(), this);
        getServer().getPluginManager().registerEvents(new EnterVehicleEvent(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);

        saveDefaultConfig();
        try {
            DriverYaml.setup();
        } catch (IOException e) {
            System.out.println("There was an error creating the drivers.yml file!");
            e.printStackTrace();
        }
        DriverYaml.get().options().copyHeader(true).header("DO NOT EDIT OR DELETE THIS FILE! DOING SO WILL RESET ALL YOUR DRIVERS AND THEIR INVENTORIES!");
        DriverYaml.get().options().copyDefaults(true);
        DriverYaml.save();

    }

}
