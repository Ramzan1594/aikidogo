package be.technifutur.Model;

import java.io.Serializable;
import java.util.Objects;

public class Plage implements Serializable {
    private String nom;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private Participant animateur;


    public Plage(String nom, String jour, String heureDebut, String heureFin) {
        this.nom = nom;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Participant getAnimateur() {return animateur;}
    public String getNom() {return nom;}
    public String getJour() {return jour;}
    public String getHeureDebut() {return heureDebut;}
    public String getHeureFin() {return heureFin;}

    public void setNom(String nom) {this.nom = nom;}
    public void setJour(String jour) {this.jour = jour;}
    public void setHeureDebut(String heureDebut) {this.heureDebut = heureDebut;}
    public void setHeureFin(String heureFin) {this.heureFin = heureFin;}
    public void setAnimateur(Participant p) {this.animateur = p;}

    @Override
    public boolean equals(Object o) {
        boolean ret = false;
        Plage p = (Plage)o;
        return Objects.equals(nom, p.nom) && Objects.equals(jour, p.jour)
                &&  Objects.equals(heureDebut, p.heureDebut)
                &&  Objects.equals(heureFin, p.heureFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, jour, heureDebut, heureFin);
    }

    @Override
    public String toString(){
        return "▫️️Plage : " + nom + " (jour:" + jour + " "+heureDebut+"h-->"+ heureFin +"h) 🥋" +
                (animateur != null ? animateur.getNom() + " " + animateur.getPrenom() : "Non défini");
    }


}
