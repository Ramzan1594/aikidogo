package be.technifutur.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inscription  implements Serializable {
    private Participant participant;
    private List<Plage> plages = new ArrayList<>();

    private boolean souper;
    private boolean logement;

    public Inscription(Participant participant) {
        this.participant = participant;
    }

    //function pour ajouter une plage
    public void addPlage(Plage p) {
//        if(p.getAnimateur() != null && p.getAnimateur().equals(participant)) {
//            throw new IllegalArgumentException("Un animateur ne peut pas participer à sa propre plage");
//        }
        plages.add(p);
    }

    public Participant getParticipant() {return participant;}
    public List<Plage> getPlages() {return plages;}

    //pour chaque inscription on inscit ou non a un souper
    public boolean isSouper() { return souper; }
    public void setSouper(boolean souper) { this.souper = souper; }

    //pour chaque inscription on inscit ou non pour un logement
    public boolean isLogement() { return logement; }
    public void setLogement(boolean logement) { this.logement = logement; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(participant.toString());
        sb.append("     🍔Souper :");
        sb.append(isSouper() ? "✅":"❌");
        sb.append("   🏦Logement :");
        sb.append(isLogement() ? "✅":"❌");

        sb.append("    ➖   🕛 ");
        for(Plage p : plages) {
            sb.append(p.toString());
        }

        return sb.toString();
    }

}
