package mvp.view;

import agence.metier.Client;
import mvp.presenter.ClientPresenter;
import utilitaires.Utilitaire;

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
        Utilitaire.affListe(lc);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    @Override
    public void affList(List infos) {
        Utilitaire.affListe(infos);
    }

    public void menu(){
        do{
            System.out.println("1.Ajout\n2.Retrait\n3.Modifier\n4.Rechercher\n5.Spécial\n6.Fin");
            System.out.print("Choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch(ch){
                case 1 : ajouter();
                    break;
                case 2 : retirer();
                    break;
                case 3 : modifier();
                    break;
                case 4 : rechercher();
                    break;
                case 5 : special();
                    break;
                case 6 : System.exit(0);
            }
        }while(true);
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

    private void rechercher() {
        System.out.println("Id du client : ");
        int idClient = sc.nextInt();
        presenter.search(idClient);
    }

    private void modifier() {
        System.out.println("Numéro de ligne : ");
        int nl = Utilitaire.choixElt(lc);
        Client client = lc.get(nl-1);
        String mail = modifyIfNotBlank("Mail",client.getMail());
        String nom = modifyIfNotBlank("Nom",client.getNom());
        String prenom = modifyIfNotBlank("Prénom",client.getPrenom());
        String tel = modifyIfNotBlank("Téléphone",client.getTelephone());
        presenter.update(new Client(client.getIdClient(),mail,nom,prenom,tel));
        lc = presenter.getAll();//rafraichissement
        Utilitaire.affListe(lc);
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
        int nl =  Utilitaire.choixElt(lc);
        Client client = lc.get(nl-1);
        presenter.removeClient(client);
    }

    public String modifyIfNotBlank(String label,String oldValue){
        System.out.println(label+" : "+oldValue);
        System.out.print("Nouvelle valeur (enter si pas de changement) : ");
        String newValue= sc.nextLine();
        if(newValue.isBlank()) return oldValue;
        return newValue;
    }
}
