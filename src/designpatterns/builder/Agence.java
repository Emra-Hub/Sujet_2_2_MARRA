package designpatterns.builder;

import agence.metier.Adresse;
import agence.metier.Client;

import java.time.LocalDate;

public class Agence {
    public static void main(String[] args) {
        Adresse a = new Adresse(1,7000,"Mons","Rue des pommes","12");
        Client c = new Client(1,"eric.dupont@mail.com","Dupont","Eric","0498765432");

        try {
            Location l1 = new Location.LocationBuilder().
                    setIdLocation(1).
                    setDateLoc(LocalDate.now()).
                    setKmtotal(5).
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l1);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        try {
            Location l2 = new Location.LocationBuilder().
                    setDateLoc(LocalDate.now()).
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l2);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        try {
            Location l3 = new Location.LocationBuilder().
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l3);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        try {
            Location l4 = new Location.LocationBuilder().
                    setIdLocation(4).
                    setDateLoc(LocalDate.of(2023,2,3)).
                    setKmtotal(10).
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l4);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        a = null;

        try {
            Location l5 = new Location.LocationBuilder().
                    setIdLocation(5).
                    setDateLoc(LocalDate.of(2023,12,31)).
                    setKmtotal(8).
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l5);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        a = new Adresse(1,7000,"Mons","Rue des pommes","12");
        c = null;

        try {
            Location l6 = new Location.LocationBuilder().
                    setIdLocation(6).
                    setDateLoc(LocalDate.of(2023,12,25)).
                    setKmtotal(5).
                    setAdrDepart(a).
                    setClient(c).
                    build();
            System.out.println(l6);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}
