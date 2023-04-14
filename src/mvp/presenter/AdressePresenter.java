package mvp.presenter;

import agence.metier.Adresse;
import mvp.model.DAOAdresse;
import mvp.view.AdresseViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AdressePresenter {
    private DAOAdresse model;
    private AdresseViewInterface view;
    private static final Logger logger = LogManager.getLogger(AdressePresenter.class);

    public AdressePresenter(DAOAdresse model, AdresseViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        view.setListDatas(getAll());
    }

    public List<Adresse> getAll(){
        return model.getAdresses();
    }

    public void addAdresse(Adresse adresse) {
        Adresse adr = model.addAdresse(adresse);
        if(adr!=null) view.affMsg("Création de : "+adr);
        else view.affMsg("Erreur de création");
        //List<Adresse> adresses = model.getAdresses();
        //view.setListDatas(adresses);
    }

    public void removeAdresse(Adresse adresse) {
        boolean ok = model.removeAdresse(adresse);
        if(ok) view.affMsg("Adresse effacée");
        else view.affMsg("Adresse non effacée");
        //List<Adresse> adresses = model.getAdresses();
        //view.setListDatas(adresses);
    }

    public Adresse selectionner() {
        logger.info("Appel de sélection adresse");
        Adresse adr = view.selectionner(model.getAdresses());
        return adr;
    }

    public void update(Adresse adresse) {
        Adresse adr = model.updateAdresse(adresse);
        if(adr==null) view.affMsg("Mise à jour infrucueuse");
        else view.affMsg("Mise à jour effectuée : "+adr);
        //List<Adresse> adresses = model.getAdresses();
        //view.setListDatas(adresses);
    }

    public void search(int idAdresse) {
        Adresse adr = model.readAdresse(idAdresse);
        if(adr==null) view.affMsg("Recherche infructueuse");
        else view.affMsg(adr.toString());
    }
}
