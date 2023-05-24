package mvp.model;

import agence.metier.Location;
import agence.metier.Taxi;

import java.math.BigDecimal;

public interface LocationSpecial {
    public boolean addFacturation(Location lo, Taxi tx);

    public boolean removeFacturation(Location lo);

    public BigDecimal API_get_total_cost_byLocation();

    public BigDecimal API_get_total_cost_byDay();
}
