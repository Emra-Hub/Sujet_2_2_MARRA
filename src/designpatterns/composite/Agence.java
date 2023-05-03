package designpatterns.composite;

public class Agence {
    public static void main(String[] args) {
        Taxi t1 = new Taxi(1,"1-QBZ-212","DIESEL",2.50);
        Taxi t2 = new Taxi(2,"1-ABC-459","ESSENCE",3.50);
        Taxi t3 = new Taxi(3,"2-HFZ-818","DIESEL",1);
        Taxi t4 = new Taxi(4,"2-TRB-123","ELECTRIQUE",6);
        Categorie c1 = new Categorie(1, "Camionette");
        Categorie c2 = new Categorie(2, "Auto");
        Categorie c3 = new Categorie(3, "MotorHome");
        c1.getElts().add(t1);
        c1.getElts().add(c2);
        c1.getElts().add(c3);
        c2.getElts().add(t2);
        c2.getElts().add(t3);
        c3.getElts().add(t4);
        System.out.println(c1);
    }
}
