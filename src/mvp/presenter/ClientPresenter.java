package mvp.presenter;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import agence.metier.Taxi;
import mvp.model.ClientSpecial;
import mvp.model.DAOClient;
import mvp.view.ClientViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClientPresenter {
    private DAOClient model;
    private ClientViewInterface view;
    private static final Logger logger = LogManager.getLogger(ClientPresenter.class);

    public ClientPresenter(DAOClient model, ClientViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        view.setListDatas(getAll());
    }

    public List<Client> getAll(){
        return model.getClients();
    }

    public void addClient(Client client) {
        Client cl = model.addClient(client);
        if(cl!=null) view.affMsg("Création de : "+cl);
        else view.affMsg("Erreur de création");
        //List<Client> clients = model.getClients();
        //view.setListDatas(clients);
    }


    public void removeClient(Client client) {
        boolean ok = model.removeClient(client);
        if(ok) view.affMsg("Client effacé");
        else view.affMsg("Client non effacé");
        //List<Client> clients = model.getClients();
        //view.setListDatas(clients);
    }

    public Client selectionner() {
        logger.info("Appel de sélection client");
        Client cl = view.selectionner(model.getClients());
        return cl;
    }

    public void update(Client client) {
        Client cl = model.updateClient(client);
        if(cl==null) view.affMsg("Mise à jour infructueuse");
        else view.affMsg("Mise à jour effectuée : "+cl);
        //List<Client> clients = model.getClients();
        //view.setListDatas(model.getClients());//désactivé pour éviter appels imbriqués
    }

    public void search(int idClient) {
        Client cl = model.readClient(idClient);
        if(cl==null) view.affMsg("Recherche infructueuse");
        else view.affMsg(cl.toString());
    }

    public void taxis(Client client) {
        List<Taxi> lt = ((ClientSpecial)model).taxis(client);
        if(lt == null || lt.isEmpty()) view.affMsg("Aucun taxi trouvé");
        else view.affList(lt);
    }

    public void locations(Client client) {
        List<Location> lloc = ((ClientSpecial)model).locations(client);
        if(lloc == null || lloc.isEmpty()) view.affMsg("Aucune location trouvée");
        else view.affList(lloc);
    }

    public void destinations(Client client) {
        List<Adresse> ladr = ((ClientSpecial)model).destinations(client);
        if(ladr == null || ladr.isEmpty()) view.affMsg("Aucune adresse trouvée");
        else view.affList(ladr);
    }

    public void API_insert_client() {
        int idclient = ((ClientSpecial)model).API_insert_client();
        if(idclient==0) view.affMsg("Exception rencontrée lors de l'appel de la fonction.");
        else view.affMsg("Valeur de la pk créée : "+idclient);
    }

    public void API_get_client_locations() {
        List<String> lstr = ((ClientSpecial)model).API_get_client_locations();
        if(lstr==null) view.affMsg("Exception rencontrée lors de l'appel de la fonction.");
        else view.affList(lstr);
    }

    public void API_count_location() {
        int nloc = ((ClientSpecial)model).API_count_location();
        if(nloc==0) view.affMsg("Exception rencontrée lors de l'appel de la procédure.");
        else view.affMsg("Nombre de location(s) effectuée(s) : "+nloc);
    }
}
