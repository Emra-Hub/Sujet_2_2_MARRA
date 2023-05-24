package mvp.presenter;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import agence.metier.Taxi;
import mvp.model.DAOLocation;
import mvp.model.LocationSpecial;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class LocationPresenter {
    private DAOLocation model;
    private LocationViewInterface view;
    private ClientPresenter clientPresenter;
    private AdressePresenter adressePresenter;
    private TaxiPresenter taxiPresenter;
    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public void setAdressePresenter(AdressePresenter adressePresenter) {
        this.adressePresenter = adressePresenter;
    }

    public void setClientPresenter(ClientPresenter clientPresenter) {
        this.clientPresenter = clientPresenter;
    }

    public void setTaxiPresenter(TaxiPresenter taxiPresenter) {
        this.taxiPresenter = taxiPresenter;
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
        if(lo!=null) {
            addFacturation(lo);
            view.affMsg("Création de : "+lo);
        }
        else view.affMsg("Erreur de création");
        //List<Location> locations = model.getLocations();
        //view.setListDatas(locations);
    }


    public void removeLocation(Location location) {
        removeFacturation(location);
        boolean ok = model.removeLocation(location);
        if(ok) view.affMsg("Location effacée");
        else view.affMsg("Location non effacée");
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

    public void addFacturation(Location lo) {
        Boolean boucle = true;
        while(boucle) {
            Taxi tx = taxiPresenter.selectionner(lo);
            boolean ok = ((LocationSpecial)model).addFacturation(lo,tx);
            if(ok) view.affMsg("Facturation ajoutée");
            else view.affMsg("Erreur lors de l'ajout de la facturation");
            boucle = taxiPresenter.boucle(lo);
        }
    }

    public void removeFacturation(Location lo) {
        boolean ok = ((LocationSpecial)model).removeFacturation(lo);
        if(ok) view.affMsg("Facturation(s) effacée(s)");
        else view.affMsg("Facturation(s) non effacée(s)");
    }

    public void API_get_total_cost_byLocation() {
        BigDecimal totalcost = ((LocationSpecial)model).API_get_total_cost_byLocation();
        if(totalcost==null) view.affMsg("Exception rencontrée lors de l'appel de la fonction.");
        else view.affMsg("Prix total de cette location : "+totalcost+" euro(s)");
    }

    public void API_get_total_cost_byDay() {
        BigDecimal totalcost = ((LocationSpecial)model).API_get_total_cost_byDay();
        if(totalcost==null) view.affMsg("Exception rencontrée lors de l'appel de la fonction.");
        else view.affMsg("Prix total des location pour ce jour : "+totalcost+" euro(s)");
    }
}
