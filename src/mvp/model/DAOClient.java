package mvp.model;

import agence.metier.Client;

import java.util.List;

public interface DAOClient {
    Client addClient(Client client);

    boolean removeClient(Client client);

    Client updateClient(Client client);

    Client readClient(int idClient);

    List<Client> getClients();
}
