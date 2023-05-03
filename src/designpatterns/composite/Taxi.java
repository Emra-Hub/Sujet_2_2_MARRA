package designpatterns.composite;

/**
 * classe taxi de l'agence
 * @author Flavio Marra
 * @version 1.0
 */
public class Taxi extends Element {
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
     * constructeur paramétré
     *
     * @param idTaxi identifiant unique du taxi
     * @param immatriculation immatriculation unique du taxi
     * @param carburant carburant du taxi : DIESEL-ESSENCE-ELECTRIQUE
     * @param prixKm prix au km du taxi au moment de la location
     */
    public Taxi(int idTaxi, String immatriculation, String carburant, double prixKm) {
        super(idTaxi);
        this.immatriculation = immatriculation;
        this.carburant = carburant;
        this.prixKm = prixKm;
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
    }

    @Override
    public int nbreVehicule() {
        return 1;
    }

    /**
     * méthode toString
     *
     * @return informations complètes du taxi
     */
    @Override
    public String toString() {
        return "Taxi{" +
                "idTaxi=" + getId() +
                ", immatriculation='" + immatriculation + '\'' +
                ", carburant='" + carburant + '\'' +
                ", prixKm=" + prixKm +
                '}';
    }
}
