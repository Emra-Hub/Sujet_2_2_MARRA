package designpatterns.observer;

public class Agence {
    public static void main(String[] args) {
        Taxi t1 = new Taxi(1,"1-QBZ-212","DIESEL",2.50);
        Taxi t2 = new Taxi(2,"1-ABC-459","ESSENCE",3.50);
        Client c1 = new Client(1,"eric.dupont@mail.com","Dupont","Eric","0498765432");
        Client c2 = new Client(2,"anne.durant@hotmail.com","Durant","Anne","0489123456");

        t1.addObserver(c1);
        t1.addObserver(c2);
        t2.addObserver(c1);

        t1.setPrixKm(1.50);
        t2.setPrixKm(4.50);
    }
}
