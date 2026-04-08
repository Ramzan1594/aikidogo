package be.technifutur.Model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Participant implements Serializable {
    private final String id = UUID.randomUUID().toString();
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private String clubName;

    public String getId() {return id;}
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getTel() {return tel;}
    public String getMail() {return mail;}
    public String getClubName() {return clubName;}


    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setTel(String tel) {this.tel = tel;}
    public void setMail(String mail) {this.mail = mail;}
    public void setClubName(String clubName) {this.clubName = clubName;}

    public Participant(){}
    public Participant(String clubName, String mail, String tel, String prenom, String nom) {
        this.clubName = clubName;
        this.mail = mail;
        this.tel = tel;
        this.prenom = prenom;
        this.nom = nom;
    }

    @Override
    public String toString() {
//        return nom + " " + prenom + " (club:" +  clubName +" status:" + type.toString() + ")";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s %-8s %-8s",nom, prenom, clubName));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        boolean ret = false;
//        if (o == null || getClass() != o.getClass()) return false;
        Participant p = (Participant) o;
        return Objects.equals(nom, p.nom) && Objects.equals(prenom, p.prenom) && Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        int result = nom.hashCode();
        result = 31 * result + prenom.hashCode();
        result = 31 * result + id.hashCode();
        return result;
//        return Objects.hash(name, prenom, naissance, taille);
    }

}
