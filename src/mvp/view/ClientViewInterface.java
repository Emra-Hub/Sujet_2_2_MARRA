package mvp.view;

import agence.metier.Client;
import mvp.presenter.ClientPresenter;

import java.util.List;

public interface ClientViewInterface {
    public void setPresenter(ClientPresenter presenter);

    public void setListDatas(List<Client> clients);

    public void affMsg(String msg);
}