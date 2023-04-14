package mvp.presenter;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import mvp.model.DAOLocation;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LocationPresenter {
    private DAOLocation model;
    private LocationViewInterface view;
    private ClientPresenter clientPresenter;
    private AdressePresenter adressePresenter;
    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public void setAdressePresenter(AdressePresenter adressePresenter) {
        this.adressePresenter = adressePresenter;
    }

    public void setClientPresenter(ClientPresenter clientPresenter) {
        this.clientPresenter = clientPresenter;
    }

    public LocationPresenter(DAOLocation model, LocationViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        view.setListDatas(getAll());
    }

    public List<Location> getAll(){
        return model.getLocations();
    }

    public void addLocation(Location location) {
        Client cl = clientPresenter.selectionner();
        Adresse adr = adressePresenter.selectionner();
        location.setClient(cl);
        location.setAdrDepart(adr);
        Location lo = model.addLocation(location);
        if(lo!=null) view.affMsg("Création de : "+lo);
        else view.affMsg("Erreur de création");
        //List<Location> locations = model.getLocations();
        //view.setListDatas(locations);
    }


    public void removeLocation(Location location) {
        boolean ok = model.removeLocation(location);
        if(ok) view.affMsg("Location effacé");
        else view.affMsg("Location non effacé");
        //List<Location> locations = model.getLocations();
        //view.setListDatas(locations);
    }

    public Location selectionner() {
        logger.info("Appel de sélection location");
        Location lo = view.selectionner(model.getLocations());
        return lo;
    }

    public void update(Location location) {
        Location lo = model.updateLocation(location);
        if(lo==null) view.affMsg("Mise à jour infructueuse");
        else view.affMsg("Mise à jour effectuée : "+lo);
        //List<Location> locations = model.getLocations();
        //view.setListDatas(model.getLocations());//désactivé pour éviter appels imbriqués
    }

    public void search(int idLocation) {
        Location lo = model.readLocation(idLocation);
        if(lo==null) view.affMsg("Recherche infructueuse");
        else view.affMsg(lo.toString());
    }
}
