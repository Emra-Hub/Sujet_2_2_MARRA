package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private int id;
    private LocalDate dateLoc;
    private int kmtotal;
    private Client client;
    private Adresse adrDepart;
    private List<Facturation> facturations = new ArrayList<>();

    public Location(int id, LocalDate dateLoc, int kmtotal, Client client, Adresse adrDepart) {
        this.id = id;
        this.dateLoc = dateLoc;
        this.kmtotal = kmtotal;
        this.client = client;
        this.adrDepart = adrDepart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateLoc() {
        return dateLoc;
    }

    public void setDateLoc(LocalDate dateLoc) {
        this.dateLoc = dateLoc;
    }

    public int getKmtotal() {
        return kmtotal;
    }

    public void setKmtotal(int kmtotal) {
        this.kmtotal = kmtotal;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Adresse getAdrDepart() {
        return adrDepart;
    }

    public void setAdrDepart(Adresse adrDepart) {
        this.adrDepart = adrDepart;
    }

    public List<Facturation> getFacturations() {
        return facturations;
    }

    public void setFacturations(List<Facturation> facturations) {
        this.facturations = facturations;
    }
}
