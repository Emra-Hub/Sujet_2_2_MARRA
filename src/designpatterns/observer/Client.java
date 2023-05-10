package designpatterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe client de l'agence
 * @author Flavio Marra
 * @version 1.0
 * @see Location
 */
public class Client extends Observer {
    /**
     * identifiant unique du client
     */
    protected int idClient;

    /**
     * mail unique du client
     * nom du client
     * prénom du client
     * téléphone du client
     */
    protected String mail, nom, prenom, telephone;

    /**
     * ensemble des locations du client
     */
    protected List<Location> locations = new ArrayList<>();

    /**
     * constructeur par défaut
     */
    public Client() {
    }

    /**
     * constructeur paramétré
     *
     * @param idClient identifiant unique du client, affecté par la base de données
     * @param mail mail unique du client
     * @param nom nom du client
     * @param prenom prénom du client
     * @param telephone téléphone du client
     */
    public Client(int idClient, String mail, String nom, String prenom, String telephone) {
        this.idClient = idClient;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    /**
     * getter idClient
     *
     * @return identifiant du client
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * setter idClient
     *
     * @param idClient identifiant du client
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * getter mail
     *
     * @return mail du client
     */
    public String getMail() {
        return mail;
    }

    /**
     * setter mail
     *
     * @param mail mail du client
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * getter nom
     *
     * @return nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     *
     * @param nom nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter prenom
     *
     * @return prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setter prenom
     *
     * @param prenom prénom du client
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter telephone
     *
     * @return téléphone du client
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * setter telephone
     *
     * @param telephone téléphone du client
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * getter locations
     *
     * @return liste des locations du client
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * setter locations
     *
     * @param locations liste des locations du client
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    /**
     * égalité de deux clients basée sur l'id
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient == client.idClient;
    }

    /**
     * calcul du hashcode du client basé sur l'id
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idClient);
    }

    /**
     * méthode toString
     *
     * @return informations complètes du client
     */
    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    @Override
    public void update(String msg) {
        System.out.println(prenom+" "+nom+" a été informé"+msg);
    }
}
