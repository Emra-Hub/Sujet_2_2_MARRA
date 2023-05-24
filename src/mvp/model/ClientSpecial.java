package mvp.model;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import agence.metier.Taxi;

import java.util.List;

public interface ClientSpecial {
    public List<Taxi> taxis(Client client);

    public List<Location> locations(Client client);

    public List<Adresse> destinations(Client client);

    public int API_insert_client();

    public List<String> API_get_client_locations();

    public int API_count_location();
}
