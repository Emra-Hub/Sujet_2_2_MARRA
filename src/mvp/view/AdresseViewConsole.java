package mvp.view;

import agence.metier.Adresse;
import mvp.presenter.AdressePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class AdresseViewConsole implements AdresseViewInterface {
    private AdressePresenter presenter;
    private List<Adresse> la;
    private Scanner sc = new Scanner(System.in);

    public AdresseViewConsole() {

    }

    @Override
    public void setPresenter(AdressePresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void setListDatas(List<Adresse> adresses) {
        this.la=adresses;
        affListe(la);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    @Override
    public Adresse selectionner(List<Adresse> la) {
        int nl = choixListe(la);
        Adresse adresse = la.get(nl-1);
        return adresse;
    }

    public void menu(){
        List options = new ArrayList<>(Arrays.asList("Ajouter","Retirer","Rechercher","Modifier","Retour"));
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
                    return;
            }
        } while (true);
    }

    private void modifier() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(la);
        Adresse adresse = la.get(nl-1);
        Integer cp = Integer.parseInt(modifyIfNotBlank("Code postal",adresse.getCp()+""));
        String localite = modifyIfNotBlank("Localité",adresse.getLocalite());
        String rue = modifyIfNotBlank("Rue",adresse.getRue());
        String num = modifyIfNotBlank("Numéro",adresse.getNum());
        try {
            presenter.update(new Adresse(adresse.getIdAdresse(),cp,localite,rue,num));
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
        la = presenter.getAll(); //rafraichissement
        affListe(la);
    }

    private void rechercher() {
        System.out.println("Id de l'adresse : ");
        int idAdresse = lireInt();
        presenter.search(idAdresse);
    }

    private void retirer() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(la);
        Adresse adresse = la.get(nl-1);
        presenter.removeAdresse(adresse);
    }

    private void ajouter() {
        System.out.print("Code postal : ");
        int cp = sc.nextInt();
        sc.skip("\n");
        System.out.print("Localité : ");
        String localite = sc.nextLine();
        System.out.print("Rue : ");
        String rue = sc.nextLine();
        System.out.print("Numéro : ");
        String num = sc.nextLine();
        try {
            presenter.addAdresse(new Adresse(0,cp,localite,rue,num));
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}
