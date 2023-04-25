package mvp.presenter;

import agence.metier.Location;
import agence.metier.Taxi;
import mvp.model.DAOTaxi;
import mvp.view.TaxiViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TaxiPresenter {
    private DAOTaxi model;
    private TaxiViewInterface view;
    private static final Logger logger = LogManager.getLogger(TaxiPresenter.class);

    public TaxiPresenter(DAOTaxi model, TaxiViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        view.setListDatas(getAll());
    }

    public List<Taxi> getAll(){
        return model.getTaxis();
    }

    public void addTaxi(Taxi taxi) {
        Taxi tx = model.addTaxi(taxi);
        if(tx!=null) view.affMsg("Création de : "+tx);
        else view.affMsg("Erreur de création");
        //List<Taxi> taxis = model.getTaxis();
        //view.setListDatas(taxis);
    }


    public void removeTaxi(Taxi taxi) {
        boolean ok = model.removeTaxi(taxi);
        if(ok) view.affMsg("Taxi effacé");
        else view.affMsg("Taxi non effacé");
        //List<Taxi> taxis = model.getTaxis();
        //view.setListDatas(taxis);
    }

    public Taxi selectionner(Location lo) {
        logger.info("Appel de sélection taxi");
        Taxi tx = view.selectionner(model.getTaxisNotUsed(lo));
        return tx;
    }

    public boolean boucle(Location lo) {
        logger.info("Appel de la boucle");
        Boolean ok = view.boucle(model.getTaxisNotUsed(lo));
        return ok;
    }

    public void update(Taxi taxi) {
        Taxi tx = model.updateTaxi(taxi);
        if(tx==null) view.affMsg("Mise à jour infructueuse");
        else view.affMsg("Mise à jour effectuée : "+tx);
        //List<Taxi> taxis = model.getTaxis();
        //view.setListDatas(model.getTaxis());//désactivé pour éviter appels imbriqués
    }

    public void search(int idTaxi) {
        Taxi tx = model.readTaxi(idTaxi);
        if(tx==null) view.affMsg("Recherche infructueuse");
        else view.affMsg(tx.toString());
    }
}
