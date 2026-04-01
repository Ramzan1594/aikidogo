package be.technifutur.Model;

import be.technifutur.Dsg;

import java.io.Serializable;
import java.lang.classfile.instruction.TableSwitchInstruction;

public class Tarif implements Serializable {
    private double pPlage;
    private double pSouper;
    private double pLogement;
    private double pFullPlages;

    public Tarif(Tarif t){
        this.pPlage = t.pPlage;
        this.pSouper = t.pSouper;
        this.pLogement = t.pLogement;
        this.pFullPlages = t.pFullPlages;
    }
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
        StringBuilder sb = new StringBuilder();
        sb.append("======= TARIFS =======*");
        sb.append(String.format("Plages : %.2f€*", this.pPlage));
        sb.append(String.format("Souper : %.2f€*", this.pSouper));
        sb.append(String.format("Logement : %.2f€*", this.pLogement));
        sb.append(String.format("Full : %.2f€*", this.pFullPlages));

        return Tabeau.displayInbox("", sb);
    }


}
