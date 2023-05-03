package designpatterns.composite;

import java.util.HashSet;
import java.util.Set;

public class Categorie extends Element {
    private String nom;
    private Set<Element> elts=new HashSet<>();
    
    public Categorie(int id, String nom){
        super(id);
        this.nom=nom;
    }

    @Override
    public String toString() {
        StringBuilder aff= new StringBuilder(getId()+" - "+nom+"\n");
        for(Element e:elts){
            aff.append(e+"\n");
        }
        return aff+"=> Nombre total de véhicules dans "+nom+" = "+nbreVehicule()+"\n";
    }

    @Override
    public int nbreVehicule() {
        int total = 0;
        for(Element tc : elts){
            total += tc.nbreVehicule();
        }
        return total;
    }

    public Set<Element> getElts() {
        return elts;
    }
}
