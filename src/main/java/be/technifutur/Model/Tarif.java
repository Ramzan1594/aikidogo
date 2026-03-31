package be.technifutur.Model;

import java.io.Serializable;

public class Tarif implements Serializable {
    private double pPlage;
    private double pSouper;
    private double pLogement;
    private double pFullPlages;

    public Tarif(double pPlage, double pLogement, double pSouper, double pFull) {
        this.pPlage = pPlage;
        this.pSouper = pSouper;
        this.pLogement = pLogement;
        this.pFullPlages = pFull;
    }

    //liste de getters
    public double getpFullPlages() {return pFullPlages;}
    public double getpLogement() {return pLogement;}
    public double getpSouper() {return pSouper;}
    public double getpPlage() {return pPlage;}

    //liste de setters
    public void setpPlage(double pPlage) {this.pPlage = pPlage;}
    public void setpSouper(double pSouper) {this.pSouper = pSouper;}
    public void setpLogement(double pLogement) {this.pLogement = pLogement;}
    public void setpFullPlages(double pFull) {this.pFullPlages = pFull;}

    @Override
    public String toString() {
        return "TARIF \n---------\n" +
                "Plage=" + pPlage +
                "€\nSouper=" + pSouper +
                "€\nLogement=" + pLogement +
                "€\nFull=" + pFullPlages;
    }


}
