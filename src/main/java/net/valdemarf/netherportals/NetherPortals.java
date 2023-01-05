package net.valdemarf.netherportals;

import net.valdemarf.netherportals.data.Config;
import net.valdemarf.netherportals.data.PortalLocation;
import net.valdemarf.netherportals.listeners.OnPortalTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;

public final class NetherPortals extends JavaPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetherPortals.class);
    private Manager manager;
    private Config config;

    @Override
    public void onEnable() {
        LOGGER.info("NetherPortals plugin has been enabled!");

        // Objects
        config = new Config();

        manager = new Manager(this, config);
        manager.setupDataFolder();

        this.getServer().getPluginManager().registerEvents(new OnPortalTP(manager, config), this);
    }

    @Override
    public void onDisable() {
        if(!manager.getConfig().portalLocations().isEmpty()) {
            try {
                manager.updateDataFolder();
            } catch (IOException e) {
                LOGGER.error("DataFolder could not be updated" + e);
            }
        }
    }
}
