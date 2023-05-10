package designpatterns.builder;

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
     * constructeur paramétré
     * @param lb objet de la classe LocationBuilder contenant les informations d'initialisation
     * */
    private Location(LocationBuilder lb) {
        this.idLocation=lb.idLocation;
        this.dateLoc=lb.dateLoc;
        this.kmtotal=lb.kmtotal;
        this.client=lb.client;
        this.adrDepart=lb.adrDepart;
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
     * getter dateLoc
     *
     * @return date de la location
     */
    public LocalDate getDateLoc() {
        return dateLoc;
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
     * getter client
     *
     * @return client de la location
     */
    public Client getClient() {
        return client;
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
     * getter facturations
     *
     * @return liste des facturations de la location
     */
    public List<Facturation> getFacturations() {
        return facturations;
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

    public static class LocationBuilder {
        /**
         * identifiant unique de la location
         */
        protected int idLocation;

        /**
         * date de la location
         */
        protected LocalDate dateLoc;

        /**
         * kilomètre(s) total du trajet de la location
         */
        protected int kmtotal;

        /**
         * client de la location
         */
        protected Client client;

        /**
         * adresse de départ de la location
         */
        protected Adresse adrDepart;

        public LocationBuilder setIdLocation(int idLocation) {
            this.idLocation = idLocation;
            return this;
        }

        public LocationBuilder setDateLoc(LocalDate dateLoc) {
            this.dateLoc = dateLoc;
            return this;
        }

        public LocationBuilder setKmtotal(int kmtotal) {
            this.kmtotal = kmtotal;
            return this;
        }

        public LocationBuilder setClient(Client client) {
            this.client = client;
            return this;
        }

        public LocationBuilder setAdrDepart(Adresse adrDepart) {
            this.adrDepart = adrDepart;
            return this;
        }

        public Location build() throws Exception {
            if (idLocation < 0) {
                throw new Exception("L'ID de la location doit être positif.");
            }

            if (dateLoc == null) {
                throw new Exception("La date de location ne doit pas être nulle.");
            }

            if (dateLoc.isBefore(LocalDate.now())) {
                throw new Exception("La date de location doit être égale ou supérieure à la date actuelle.");
            }

            if (kmtotal < 0) {
                throw new Exception("Le nombre de kilomètres totaux doit être positif.");
            }

            if (adrDepart == null) {
                throw new Exception("L'adresse de départ ne doit pas être nulle.");
            }

            if (client == null) {
                throw new Exception("Le client ne doit pas être nul.");
            }
            return new Location(this);
        }
    }
}
