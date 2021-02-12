package me.delected.tcontrols;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public final class TControls extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("tcontrols")).setExecutor(new TControlsCommand());
        getServer().getPluginManager().registerEvents(new ControlPlaceEvent(), this);

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
