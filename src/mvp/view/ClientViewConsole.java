package mvp.view;

import agence.metier.Client;
import mvp.presenter.ClientPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

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
        affListe(lc);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    @Override
    public void affList(List infos) {
        affListe(infos);
    }

    @Override
    public Client selectionner(List<Client> lc) {
        int nl = choixListe(lc);
        Client client = lc.get(nl-1);
        return client;
    }

    public void menu(){
        List options = new ArrayList<>(Arrays.asList("Ajouter","Retirer","Rechercher","Modifier","Special","Retour"));
        do {
            int ch = choixListe(options);

            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    rechercher();
                    break;
                case 4:
                    modifier();
                    break;
                case 5:
                    special();
                    break;
                case 6:
                    return;
            }
        } while (true);
    }

    private void modifier() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(lc);
        Client client = lc.get(nl-1);
        String mail = modifyIfNotBlank("Mail",client.getMail());
        String nom = modifyIfNotBlank("Nom",client.getNom());
        String prenom = modifyIfNotBlank("Prénom",client.getPrenom());
        String tel = modifyIfNotBlank("Téléphone",client.getTelephone());
        presenter.update(new Client(client.getIdClient(),mail,nom,prenom,tel));
        lc = presenter.getAll();//rafraichissement
        affListe(lc);
    }

    private void rechercher() {
        System.out.println("Id du client : ");
        int idClient = sc.nextInt();
        presenter.search(idClient);
    }

    private void retirer() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(lc);
        Client client = lc.get(nl-1);
        presenter.removeClient(client);
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

    private void special() {
        System.out.println("Numéro de ligne : ");
        int nl =  sc.nextInt()-1;
        sc.skip("\n");
        if (nl >= 0) {
            Client client = lc.get(nl);
            do {
                System.out.println("1.Taxis utilisés\n2.Locations effectuées\n3.Destination\n4.Menu principal");
                System.out.println("Choix : ");
                int ch = sc.nextInt();
                sc.skip("\n");
                switch (ch) {
                    case 1:
                        presenter.taxis(client);
                        break;
                    case 2:
                        presenter.locations(client);
                        break;
                    case 3:
                        presenter.destinations(client);
                        break;
                    case 4: return;
                    default:
                        System.out.println("Choix invalide recommencez");
                }
            } while (true);
        }
    }
}
