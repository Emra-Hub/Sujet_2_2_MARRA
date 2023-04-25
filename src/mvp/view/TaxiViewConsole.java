package mvp.view;

import agence.metier.Taxi;
import mvp.presenter.TaxiPresenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class TaxiViewConsole implements TaxiViewInterface {
    private TaxiPresenter presenter;
    private List<Taxi> lt;
    private Scanner sc = new Scanner(System.in);

    public TaxiViewConsole() {
    }

    @Override
    public void setPresenter(TaxiPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void setListDatas(List<Taxi> taxis) {
        this.lt=taxis;
        affListe(lt);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }

    @Override
    public Taxi selectionner(List<Taxi> lt) {
        int nl = choixListe(lt);
        Taxi taxi = lt.get(nl-1);
        return taxi;
    }

    @Override
    public boolean boucle(List<Taxi> lt) {
        if(!lt.isEmpty()) {
            List options = new ArrayList<>(Arrays.asList("oui","non"));
            System.out.println("Autre taxi ?");
            int choix = choixListe(options);
            if (choix == 1) {
                return true;
            }
            return false;
        }
        else {
            System.out.println("Plus aucun taxis disponibles.");
            return false;
        }
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
        int nl = choixElt(lt);
        Taxi taxi = lt.get(nl-1);
        String immatriculation = modifyIfNotBlank("Immatriculation", taxi.getImmatriculation());
        String carburant = modifyIfNotBlank("Carburant",taxi.getCarburant()); //Pouvoir faire le choix entre "DIESEL","ESSENCE","ELECTRIQUE"
        BigDecimal prixkm = new BigDecimal(modifyIfNotBlank("Prix km",taxi.getPrixKm()+""));
        presenter.update(new Taxi(taxi.getIdTaxi(),immatriculation,carburant,prixkm));
        lt = presenter.getAll();//rafraichissement
        affListe(lt);
    }

    private void rechercher() {
        System.out.println("Id du taxi : ");
        int idTaxi = sc.nextInt();
        presenter.search(idTaxi);
    }

    private void retirer() {
        System.out.println("Numéro de ligne : ");
        int nl = choixElt(lt);
        Taxi tx = lt.get(nl-1);
        presenter.removeTaxi(tx);
    }

    private void ajouter() {
        System.out.print("Immatriculation : ");
        String immatriculation = sc.nextLine();
        System.out.println("Carburant : ");
        List<String> carburant = new ArrayList<>(Arrays.asList("DIESEL","ESSENCE","ELECTRIQUE"));
        int ch = choixListe(carburant);
        System.out.print("Prix km : ");
        BigDecimal prixkm = sc.nextBigDecimal();
        sc.skip("\n");
        presenter.addTaxi(new Taxi(0,immatriculation,carburant.get(ch-1),prixkm));
    }
}
