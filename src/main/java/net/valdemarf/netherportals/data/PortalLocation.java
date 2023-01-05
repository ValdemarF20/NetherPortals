package net.valdemarf.netherportals.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Class is used to store the locations given in JSON file
 */
public final class PortalLocation {
    private String worldName;
    private int x;
    private int y;
    private int z;
    private int id;

    public Location bukkitLocation() {
        World world = Bukkit.getWorld(worldName);
        if(world == null) {
            Bukkit.getLogger().severe("Portal world in json is null");
            return null;
        }

        return new Location(world, x, y, z);
    }

    public void setLocation(Location location) {
        worldName = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "worldName: " + worldName + " x: " + x + " y: " + y + " z: " + z;
    }
}
