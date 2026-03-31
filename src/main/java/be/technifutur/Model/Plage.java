package be.technifutur.Model;

import java.io.Serializable;

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

    public void setAnimateur(Participant p) {this.animateur = p;}

    @Override
    public String toString(){
        return "Plage : " + nom + " (jour:" + jour + " "+heureDebut+"h-->"+ heureFin +"h) - Animateur: " +
                (animateur != null ? animateur.getNom() + " " + animateur.getPrenom() : "Non défini");
    }


}
