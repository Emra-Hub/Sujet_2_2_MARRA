package mvp.view;

import agence.metier.Taxi;
import mvp.presenter.TaxiPresenter;

import java.util.List;

public interface TaxiViewInterface {
    public void setPresenter(TaxiPresenter presenter);

    public void setListDatas(List<Taxi> taxis);

    public void affMsg(String msg);

    public Taxi selectionner(List<Taxi> lt);

    public boolean boucle();
}
