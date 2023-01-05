package net.valdemarf.netherportals.listeners;

import net.valdemarf.netherportals.Manager;
import net.valdemarf.netherportals.data.Config;
import net.valdemarf.netherportals.data.PortalLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class OnPortalTP implements Listener {
    private final Manager manager;
    private final Config config;

    public OnPortalTP(Manager manager, Config config) {
        this.manager = manager;
        this.config = config;
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();

        int radius = 3;
        for(int x = radius; x >= -radius; x--) {
            for (int y = radius; y >= -radius; y--) {
                for(int z = radius; z >= -radius; z--) {

                    // Within radius of portal


                    for (PortalLocation loc : config.portalLocations()) {
                        if(location.equals(loc.bukkitLocation())) { // loc is stored in json file

                            // Check if the location is currently linked to another location
                            if(manager.getLinkedBi().containsKey(loc.getId())) {
                                player.teleport(manager.getLocationFromID(manager.getLinkedBi().get(loc.getId())));
                            } else if(manager.getLinkedBi().containsValue(loc.getId())) {
                                player.teleport(manager.getLocationFromID(manager.getLinkedBi().inverse().get(loc.getId())));
                            }
                        }
                    }
                }
            }
        }
    }
}
