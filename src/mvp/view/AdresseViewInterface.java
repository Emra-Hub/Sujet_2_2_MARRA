package mvp.view;

import agence.metier.Adresse;
import mvp.presenter.AdressePresenter;

import java.util.List;

public interface AdresseViewInterface {
    public void setPresenter(AdressePresenter presenter);

    public void setListDatas(List<Adresse> adresses);

    public void affMsg(String msg);

    public Adresse selectionner(List<Adresse> la);
}
