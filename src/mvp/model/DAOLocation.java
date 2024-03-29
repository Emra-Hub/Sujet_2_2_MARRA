package mvp.model;

import agence.metier.Location;

import java.util.List;

public interface DAOLocation {
    Location addLocation(Location location);

    boolean removeLocation(Location location);

    Location updateLocation(Location location);

    Location readLocation(int idLocation);

    List<Location> getLocations();
}
