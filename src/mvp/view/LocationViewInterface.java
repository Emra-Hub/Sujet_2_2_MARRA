package mvp.view;

import agence.metier.Location;
import mvp.presenter.LocationPresenter;

import java.util.List;

public interface LocationViewInterface {
    public void setPresenter(LocationPresenter presenter);

    public void setListDatas(List<Location> locations);

    public void affMsg(String msg);


    public Location selectionner(List<Location> lloc);
}
