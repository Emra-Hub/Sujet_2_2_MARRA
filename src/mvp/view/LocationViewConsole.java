package mvp.view;

import agence.metier.Location;
import mvp.presenter.LocationPresenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class LocationViewConsole implements LocationViewInterface {
    private LocationPresenter presenter;
    private List<Location> lloc;
    private Scanner sc = new Scanner(System.in);
    
    public LocationViewConsole() {
    }

    @Override
    public void setPresenter(LocationPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void setListDatas(List<Location> locations) {
        this.lloc=locations;
        affListe(lloc);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    @Override
    public Location selectionner(List<Location> lloc) {
        int nl = choixListe(lloc);
        Location location = lloc.get(nl-1);
        return location;
    }

    public void menu(){
        List options = new ArrayList<>(Arrays.asList("Ajouter","Retirer","Rechercher","Modifier","Special SGBD","Retour"));
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
        int nl = choixElt(lloc);
        Location location = lloc.get(nl-1);
        LocalDate dateloc = LocalDate.parse(modifyIfNotBlank("Date location",location.getDateLoc()+"")); //Il faudrait voir pour utiliser la fonction getDateFrench
        Integer kmtotal = Integer.parseInt(modifyIfNotBlank("Km total",location.getKmtotal()+""));
        try {
            presenter.update(new Location(location.getIdLocation(),dateloc,kmtotal));
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
        lloc = presenter.getAll();
        affListe(lloc);
    }

    private void rechercher() {
        System.out.println("Id de la location : ");
        int idLocation = lireInt();
        presenter.search(idLocation);
    }

    private void retirer() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(lloc);
        Location location = lloc.get(nl-1);
        presenter.removeLocation(location);
    }

    private void ajouter() {
        System.out.print("Date location : ");
        int j = sc.nextInt();
        int m = sc.nextInt();
        int a = sc.nextInt();
        LocalDate dateloc = LocalDate.of(a, m, j);
        System.out.print("Km total : ");
        int kmtotal = sc.nextInt();
        sc.skip("\n");
        try {
            presenter.addLocation(new Location(0,dateloc,kmtotal));
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    private void special() {
        do {
            List options = new ArrayList<>(Arrays.asList("Prix total d’une location donnée","Prix total des locations d'un jour donné","Menu principal"));
            int ch = choixListe(options);
            switch (ch) {
                case 1:
                    presenter.API_get_total_cost_byLocation();
                    break;
                case 2:
                    presenter.API_get_total_cost_byDay();
                case 3:
                    return;
            }
        } while (true);
    }
}
