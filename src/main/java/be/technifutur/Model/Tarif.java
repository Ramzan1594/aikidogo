package be.technifutur.Model;

import java.io.Serializable;

public class Tarif implements Serializable {
    private int id;
    private String nom;
    private double pPlage;
    private double pSouper;
    private double pLogement;
    private double pFullPlages;

    public Tarif(){}
    public Tarif(Tarif t){
        this.id = t.id;
        this.nom = t.nom;
        this.pPlage = t.pPlage;
        this.pSouper = t.pSouper;
        this.pLogement = t.pLogement;
        this.pFullPlages = t.pFullPlages;
    }
    public Tarif(int id, String nom, double pPlage, double pLogement, double pSouper, double pFull) {
        this.id = id;
        this.nom = nom;
        this.pPlage = pPlage;
        this.pSouper = pSouper;
        this.pLogement = pLogement;
        this.pFullPlages = pFull;
    }

    //liste de getters
    public int getId() {return id;}
    public String getNom() {return nom;}
    public double getpFullPlages() {return pFullPlages;}
    public double getpLogement() {return pLogement;}
    public double getpSouper() {return pSouper;}
    public double getpPlage() {return pPlage;}

    //liste de setters
    public void setId(int id) {this.id = id;}
    public void setNom(String nom) {this.nom = nom;}
    public void setpPlage(double pPlage) {this.pPlage = pPlage;}
    public void setpSouper(double pSouper) {this.pSouper = pSouper;}
    public void setpLogement(double pLogement) {this.pLogement = pLogement;}
    public void setpFullPlages(double pFull) {this.pFullPlages = pFull;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("======= %d.TARIFS %s =======*",id,nom)).append(String.format("Plages : %.2f€*", this.pPlage))
        .append(String.format("Souper : %.2f€*", this.pSouper)).append(String.format("Logement : %.2f€*", this.pLogement))
        .append(String.format("Full : %.2f€*", this.pFullPlages));

        return Tableau.displayInbox("", sb);
    }
}
