package mvp.view;

import agence.metier.Client;
import mvp.presenter.ClientPresenter;

import java.util.List;

public interface ClientViewInterface {
    public void setPresenter(ClientPresenter presenter);

    public void setListDatas(List<Client> clients);

    public void affMsg(String msg);

    public void affList(List infos);

    public Client selectionner(List<Client> lc);
}
