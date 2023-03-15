package mvp;

import mvp.model.ClientModelDB;
import mvp.model.DAOClient;
import mvp.presenter.ClientPresenter;
import mvp.view.ClientViewConsole;
import mvp.view.ClientViewInterface;

public class GestClient {
    public static void main(String[] args) {
        //DAOClient cm = new ClientModel();
        DAOClient cm = new ClientModelDB();
        ClientViewInterface cv = new ClientViewConsole();
        //ClientViewInterface cv = new ClientViewGraph();
        ClientPresenter cp = new ClientPresenter(cm,cv);//création et injection de dépendance
        cp.start();
    }
}