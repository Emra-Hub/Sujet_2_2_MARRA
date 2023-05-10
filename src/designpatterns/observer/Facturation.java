package designpatterns.observer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * classe métier de gestion d'une facturation
 * @author Flavio Marra
 * @version 1.0
 * @see Taxi
 */
public class Facturation {
    /**
     * coût de la facturation
     */
    protected BigDecimal cout;

    /**
     * taxi de la facturation
     */
    protected Taxi vehicule;

    /**
     * constructeur par défaut
     */
    public Facturation() {
    }

    /**
     * constructeur paramétré
     *
     * @param cout coût de la facturation calculé selon la formule : prixKM du taxi * kmtotal du trajet
     * @param vehicule taxi de la facturation
     */
    public Facturation(BigDecimal cout, Taxi vehicule) {
        this.cout = cout;
        this.vehicule = vehicule;
    }

    /**
     * getter cout
     *
     * @return coût de la facturation
     */
    public BigDecimal getCout() {
        return cout;
    }

    /**
     * setter cout
     *
     * @param cout coût de la facturation
     */
    public void setCout(BigDecimal cout) {
        this.cout = cout.setScale(2,RoundingMode.HALF_UP);
    }

    /**
     * getter vehicule
     *
     * @return taxi de la facturation
     */
    public Taxi getVehicule() {
        return vehicule;
    }

    /**
     * setter vehicule
     *
     * @param vehicule taxi de la facturation
     */
    public void setVehicule(Taxi vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * méthode toString
     *
     * @return informations complètes de la facturation de la location
     */
    @Override
    public String toString() {
        return "Facturation{" +
                "cout=" + cout +
                ", vehicule=" + vehicule +
                '}';
    }
}
