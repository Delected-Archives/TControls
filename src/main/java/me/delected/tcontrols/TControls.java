package me.delected.tcontrols;
import me.delected.tcontrols.commands.TCDebug;
import me.delected.tcontrols.commands.TCReload;
import me.delected.tcontrols.commands.TControlsCommand;
import me.delected.tcontrols.events.ControlInteractEvents;
import me.delected.tcontrols.events.EnterVehicleEvent;
import me.delected.tcontrols.events.PickDropItemEvent;
import me.delected.tcontrols.events.PlayerJoinQuitEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class TControls extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("tcontrols").setExecutor(new TControlsCommand());
        getCommand("tcontrolsreload").setExecutor(new TCReload());
        getCommand("tcontrolsdebug").setExecutor(new TCDebug());

        getServer().getPluginManager().registerEvents(new ControlInteractEvents(), this);
        getServer().getPluginManager().registerEvents(new EnterVehicleEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitEvents(), this);
        getServer().getPluginManager().registerEvents(new PickDropItemEvent(), this);

        saveDefaultConfig();

        ServerOptions so = new ServerOptions();

        if (so.getNeutralSpeed() > so.getMaxSpeed()) {
            getLogger().info("Your config's neutral-speed value is higher than your max-speed value! Disabling TControls...");
            setEnabled(false);
        }
    }
}
