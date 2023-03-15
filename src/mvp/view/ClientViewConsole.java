package mvp.view;

import agence.metier.Client;
import mvp.presenter.ClientPresenter;

import java.util.List;
import java.util.Scanner;

public class ClientViewConsole implements ClientViewInterface {
    private ClientPresenter presenter;
    private List<Client> lc;
    private Scanner sc = new Scanner(System.in);

    public ClientViewConsole() {

    }

    @Override
    public void setPresenter(ClientPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void setListDatas(List<Client> clients) {
        this.lc=clients;
        int i=1;
        for(Client cl : lc){
            System.out.println((i++)+"."+cl);
        }
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    public void menu(){
        do{
            System.out.println("1.Ajout 2.Retrait 3.Fin");

            int ch = sc.nextInt();
            sc.skip("\n");
            switch(ch){
                case 1: ajouter();
                    break;
                case 2 : retirer();
                    break;
                case 3 : System.exit(0);
            }
        }while(true);
    }

    private void ajouter() {
        System.out.print("Mail : ");
        String mail = sc.nextLine();
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Téléphone : ");
        String tel = sc.nextLine();
        presenter.addClient(new Client(0,mail,nom,prenom,tel));
    }

    private void retirer() {
        System.out.println("Numéro de ligne : ");
        int nl =  sc.nextInt()-1;
        sc.skip("\n");
        if (nl >= 0) {
            Client client = lc.get(nl);
            presenter.removeClient(client);
        }
    }
}
