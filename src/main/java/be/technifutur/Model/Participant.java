package be.technifutur.Model;

import java.io.Serializable;
import java.util.UUID;

public class Participant implements Serializable {
    private final String id = UUID.randomUUID().toString();
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private String clubName;
    private ETypeParticipant type;

    public String getId() {return id;}
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getTel() {return tel;}
    public String getMail() {return mail;}
    public String getClubName() {return clubName;}
    public ETypeParticipant getType() {return type;}


    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setTel(String tel) {this.tel = tel;}
    public void setMail(String mail) {this.mail = mail;}
    public void setClubName(String clubName) {this.clubName = clubName;}
    public void setType(ETypeParticipant type) {this.type = type;}

    public Participant(ETypeParticipant type, String clubName, String mail, String tel, String prenom, String nom) {
        this.type = type;
        this.clubName = clubName;
        this.mail = mail;
        this.tel = tel;
        this.prenom = prenom;
        this.nom = nom;
    }

    @Override
    public String toString() {
//        return nom + " " + prenom + " (club:" +  clubName +" status:" + type.toString() + ")";
        StringBuilder sb = new StringBuilder();//
        sb.append(String.format("%-15s %-15s %-15s %-10s",nom, prenom, clubName, type));
        return sb.toString();
    }
}
