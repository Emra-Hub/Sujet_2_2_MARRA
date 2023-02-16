package agence.metier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * classe métier de gestion d'une location
 * @author Flavio Marra
 * @version 1.0
 * @see Client
 * @see Adresse
 * @see Facturation
 */
public class Location {
    /**
     * identifiant unique de la location
     */
    private int idLocation;

    /**
     * date de la location
     */
    private LocalDate dateLoc;

    /**
     * kilomètre(s) total du trajet de la location
     */
    private int kmtotal;

    /**
     * client de la location
     */
    private Client client;

    /**
     * adresse de départ de la location
     */
    private Adresse adrDepart;

    /**
     * liste des facturations de la location
     */
    private List<Facturation> facturations = new ArrayList<>();

    /**
     * constructeur par défaut
     */
    public Location() {
    }

    /**
     * constructeur paramétré
     *
     * @param idLocation identifiant unique de la location, affecté par la base de données
     * @param dateLoc date de la location
     * @param kmtotal kilomètre(s) total du trajet de la location
     * @param client client de la location
     * @param adrDepart adresse de départ de la location
     */
    public Location(int idLocation, LocalDate dateLoc, int kmtotal, Client client, Adresse adrDepart) {
        this.idLocation = idLocation;
        this.dateLoc = dateLoc;
        this.kmtotal = kmtotal;
        this.client = client;
        this.adrDepart = adrDepart;
    }

    /**
     * getter idLocation
     *
     * @return identifiant de la location
     */
    public int getIdLocation() {
        return idLocation;
    }

    /**
     * setter idLocation
     *
     * @param idLocation identifiant de la location
     */
    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    /**
     * getter dateLoc
     *
     * @return date de la location
     */
    public LocalDate getDateLoc() {
        return dateLoc;
    }

    /**
     * setter dateLoc
     *
     * @param dateLoc date de la location
     */
    public void setDateLoc(LocalDate dateLoc) {
        this.dateLoc = dateLoc;
    }

    /**
     * getter kmtotal
     *
     * @return kilomètre(s) total du trajet de la location
     */
    public int getKmtotal() {
        return kmtotal;
    }

    /**
     * setter kmtotal
     *
     * @param kmtotal kilomètre(s) total du trajet de la location
     */
    public void setKmtotal(int kmtotal) {
        this.kmtotal = kmtotal;
    }

    /**
     * getter client
     *
     * @return client de la location
     */
    public Client getClient() {
        return client;
    }

    /**
     * setter client
     *
     * @param client client de la location
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * getter adrDepart
     *
     * @return adresse de départ de la location
     */
    public Adresse getAdrDepart() {
        return adrDepart;
    }

    /**
     * setter adrDepart
     *
     * @param adrDepart adresse de départ de la location
     */
    public void setAdrDepart(Adresse adrDepart) {
        this.adrDepart = adrDepart;
    }

    /**
     * getter facturations
     *
     * @return liste des facturations de la location
     */
    public List<Facturation> getFacturations() {
        return facturations;
    }

    /**
     * setter facturations
     *
     * @param facturations liste des facturations de la location
     */
    public void setFacturations(List<Facturation> facturations) {
        this.facturations = facturations;
    }

    /**
     * méthode toString
     *
     * @return informations complètes de la location
     */
    @Override
    public String toString() {
        return "Location{" +
                "idLocation=" + idLocation +
                ", dateLoc=" + dateLoc +
                ", kmtotal=" + kmtotal +
                ", client=" + client +
                ", adrDepart=" + adrDepart +
                '}';
    }
}
