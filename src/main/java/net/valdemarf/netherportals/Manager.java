package net.valdemarf.netherportals;

import com.google.common.collect.HashBiMap;
import com.google.gson.*;
import net.valdemarf.netherportals.data.Config;
import net.valdemarf.netherportals.data.PortalLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class Manager {
    private final NetherPortals netherPortals;
    private Config config;

    private HashBiMap<Integer, Integer> linkedBi;

    public static Gson GSON = createGsonInstance().newBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    public Manager(NetherPortals netherPortals, Config config) {
        this.netherPortals = netherPortals;
        this.config = config;
    }

    /**
     * Sets up the data folder for parkour checkpoints if it's missing.
     */
    public void setupDataFolder() {
        try {
            netherPortals.getDataFolder().mkdir();
            File file = new File(netherPortals.getDataFolder(), "portals.json");

            if (file.createNewFile()) {
                LOGGER.info("Data File for NetherPortals has been generated");
            }

            Reader reader = new FileReader(file);
            config = GSON.fromJson(reader, Config.class);

            linkedBi = HashBiMap.create(config.linkedPortals());

        } catch(IOException e) {
            LOGGER.error("DataFolder could not be created", e);
        }
    }

    /**
     *
     * @return the instance of gson that should be used everywhere in the plugin
     */
    public static Gson createGsonInstance() {
        return new GsonBuilder().create();
    }

    public void updateDataFolder() throws IOException {
        Path path = Paths.get(netherPortals.getDataFolder() +  "/portals.json");

        // Update locations from dataList
        config.linkedPortals().putAll(linkedBi);
        Files.write(path, GSON.toJson(config).getBytes());
    }

    public void updateLocation(Location prevLoc, Location newLoc) {
        // Update a previous location in json file

        for (PortalLocation portalLocation : config.portalLocations()) {
            Location loc = portalLocation.bukkitLocation();
            if(loc == null) {
                return;
            }
            if(loc.equals(prevLoc)) {
                LOGGER.warn("Previous location has been found");
                portalLocation.setLocation(newLoc);
            }
        }
    }

    public void printPortals(Level level) {
        for (PortalLocation portalLocation : config.portalLocations()) {
            Location loc = portalLocation.bukkitLocation();
            if(loc == null) {
                Bukkit.getLogger().log(level, (portalLocation + " Location in this data object is null"));
                return;
            }

            LOGGER.warn(loc.toString());
        }
    }

    public Config getConfig() {
        return config;
    }

    /**
     * Updates the link between two locations
     * @param locOne One of the locations for the link
     * @param locTwo The other location for the link
     */
    public void updateLink(int locOne, int locTwo) {
        linkedBi.forcePut(locOne, locTwo);
    }

    public HashBiMap<Integer, Integer> getLinkedBi() {
        return linkedBi;
    }

    public Location getLocationFromID(Integer id) {
        for (PortalLocation portalLocation : config.portalLocations()) {
            if (portalLocation.getId() == id) {
                return portalLocation.bukkitLocation();
            }
        }

        return null;
    }
}