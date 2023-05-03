package mvp.model;

import agence.metier.Location;
import agence.metier.Taxi;

public interface LocationSpecial {
    public boolean addFacturation(Location lo, Taxi tx);

    public boolean removeFacturation(Location lo);
}
