package agence.metier;

/**
 * classe adresse de l'agence
 * @author Flavio Marra
 * @version 1.0
 */
public class Adresse {
    /**
     * identifiant unique de l'adresse
     * code postal de l'adresse
     */
    private int idAdresse, cp;

    /**
     * localité de l'adresse
     * rue de l'adresse
     * numéro de l'adresse
     */
    private String localite, rue, num;

    /**
     * constructeur par défaut
     */
    public Adresse() {
    }

    /**
     * constructeur paramétré
     *
     * @param idAdresse identifiant unique de l'adresse, affecté par la base de données
     * @param cp code postal de l'adresse
     * @param localite localité de l'adresse
     * @param rue rue de l'adresse
     * @param num numéro de l'adresse
     */
    public Adresse(int idAdresse, int cp, String localite, String rue, String num) {
        this.idAdresse = idAdresse;
        this.cp = cp;
        this.localite = localite;
        this.rue = rue;
        this.num = num;
    }

    /**
     * getter idAdresse
     *
     * @return identifiant de l'adresse
     */
    public int getIdAdresse() {
        return idAdresse;
    }

    /**
     * setter idAdresse
     *
     * @param idAdresse identifiant de l'adresse
     */
    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    /**
     * getter cp
     *
     * @return code postal de l'adresse
     */
    public int getCp() {
        return cp;
    }

    /**
     * setter cp
     *
     * @param cp code postal de l'adresse
     */
    public void setCp(int cp) {
        this.cp = cp;
    }

    /**
     * getter localite
     *
     * @return localité de l'adresse
     */
    public String getLocalite() {
        return localite;
    }

    /**
     * setter localite
     *
     * @param localite localité de l'adresse
     */
    public void setLocalite(String localite) {
        this.localite = localite;
    }

    /**
     * getter rue
     *
     * @return rue de l'adresse
     */
    public String getRue() {
        return rue;
    }

    /**
     * setter rue
     *
     * @param rue rue de l'adresse
     */
    public void setRue(String rue) {
        this.rue = rue;
    }

    /**
     * getter num
     *
     * @return numéro de l'adresse
     */
    public String getNum() {
        return num;
    }

    /**
     * setter num
     *
     * @param num numéro de l'adresse
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * methode toString
     *
     * @return informations complètes sur l'adresse de départ de la location
     */
    @Override
    public String toString() {
        return "Adresse{" +
                "idAdresse=" + idAdresse +
                ", cp=" + cp +
                ", localite='" + localite + '\'' +
                ", rue='" + rue + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
