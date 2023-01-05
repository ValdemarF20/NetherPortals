package net.valdemarf.netherportals.data;

import java.util.Map;
import java.util.Set;

public final class Config {
    private Set<PortalLocation> portalLocations;
    private Map<Integer, Integer> linkedPortals;

    public Set<PortalLocation> portalLocations() {
        return portalLocations;
    }

    public Map<Integer, Integer> linkedPortals() {
        return linkedPortals;
    }
}
