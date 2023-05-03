package designpatterns.observer;

import java.util.Objects;

/**
 * classe taxi de l'agence
 * @author Flavio Marra
 * @version 1.0
 */
public class Taxi extends Subject {
    /**
     * identifiant unique du taxi
     */
    protected int idTaxi;

    /**
     * immatriculation unique du taxi
     * carburant du taxi : DIESEL-ESSENCE-ELECTRIQUE
     */
    protected String immatriculation, carburant;

    /**
     * prix au km du taxi au moment de la location
     */
    protected double prixKm;

    /**
     * constructeur par défaut
     */
    public Taxi() {
    }

    /**
     * constructeur paramétré
     *
     * @param idTaxi identifiant unique du taxi, affecté par la base de données
     * @param immatriculation immatriculation unique du taxi
     * @param carburant carburant du taxi : DIESEL-ESSENCE-ELECTRIQUE
     * @param prixKm prix au km du taxi au moment de la location
     */
    public Taxi(int idTaxi, String immatriculation, String carburant, double prixKm) {
        this.idTaxi = idTaxi;
        this.immatriculation = immatriculation;
        this.carburant = carburant;
        this.prixKm = prixKm;
    }

    /**
     * getter idTaxi
     *
     * @return identifiant du taxi
     */
    public int getIdTaxi() {
        return idTaxi;
    }

    /**
     * setter idTaxi
     *
     * @param idTaxi identifiant du taxi
     */
    public void setIdTaxi(int idTaxi) {
        this.idTaxi = idTaxi;
    }

    /**
     * getter immatriculation
     *
     * @return immatriculation du taxi
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * setter immatriculation
     *
     * @param immatriculation immatriculation du taxi
     */
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    /**
     * getter carburant
     *
     * @return carburant du taxi : DIESEL-ESSENCE-ELECTRIQUE
     */
    public String getCarburant() {
        return carburant;
    }

    /**
     * setter carburant
     *
     * @param carburant carburant du taxi : DIESEL-ESSENCE-ELECTRIQUE
     */
    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    /**
     * getter prixKm
     *
     * @return prix au kilomètre du taxi
     */
    public double getPrixKm() {
        return prixKm;
    }

    /**
     * setter prixKm
     *
     * @param prixKm prix au kilomètre du taxi
     */
    public void setPrixKm(double prixKm) {
        this.prixKm = prixKm;
        notifyObservers();
    }

    /**
     * égalité de deux taxis basée sur l'id
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return idTaxi == taxi.idTaxi;
    }

    /**
     * calcul du hashcode du taxi basé sur l'id
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTaxi);
    }

    /**
     * méthode toString
     *
     * @return informations complètes du taxi
     */
    @Override
    public String toString() {
        return "Taxi{" +
                "idTaxi=" + idTaxi +
                ", immatriculation='" + immatriculation + '\'' +
                ", carburant='" + carburant + '\'' +
                ", prixKm=" + prixKm +
                '}';
    }

    @Override
    public String getNotification() {
        return " du changement du prix au km du taxi => ID : "+idTaxi+" - Immatriculation : "+immatriculation+" au prix de : "+prixKm+"€";
    }
}
