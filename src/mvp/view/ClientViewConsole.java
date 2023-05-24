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
        List options = new ArrayList<>(Arrays.asList("Ajouter","Retirer","Rechercher","Modifier","Special","Special SGBD","Retour"));
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
                    specialSGBD();
                    break;
                case 7:
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
        try {
            presenter.update(new Client(client.getIdClient(),mail,nom,prenom,tel));
        }
        catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
        lc = presenter.getAll();//rafraichissement
        affListe(lc);
    }

    private void rechercher() {
        System.out.println("Id du client : ");
        int idClient = lireInt();
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
        try {
            presenter.addClient(new Client(0,mail,nom,prenom,tel));
        }
        catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    private void special() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(lc);
        Client client = lc.get(nl-1);
        if (nl >= 0) {
            do {
                List options = new ArrayList<>(Arrays.asList("Taxis utilisés","Locations effectuées","Destination","Menu principal"));
                int ch = choixListe(options);
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
                    case 4:
                        return;
                }
            } while (true);
        }
    }

    private void specialSGBD() {
        do {
            List options = new ArrayList<>(Arrays.asList("Insertion client et récupération de la valeur de la pk créée","Toutes les locations ainsi que leur coût total d'un client donné","Nombre de locations déjà effectuées par un client selon son mail","Menu principal"));
            int ch = choixListe(options);
            switch (ch) {
                case 1:
                    presenter.API_insert_client();
                    break;
                case 2:
                    presenter.API_get_client_locations();
                    break;
                case 3:
                    presenter.API_count_location();
                case 4:
                    return;
            }
        } while (true);
    }
}
