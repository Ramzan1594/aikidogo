package be.technifutur.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inscription  implements Serializable {
    private Participant participant = new Participant();
    private List<Plage> plages = new ArrayList<>();
    private Tarif tarif = new Tarif();

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
    public void removePlage(Plage p) {
        plages.remove(p);
    }

    public Participant getParticipant() {return participant;}
    public List<Plage> getPlages() {return plages;}
    public boolean getSouper() { return souper; }
    public boolean getLogement() { return logement; }
    public Tarif getTarif() {return tarif;}

    //pour chaque inscription on inscit ou non a un souper
    public void setTarif(Tarif tarif) {this.tarif = tarif;}
    public void setSouper(boolean souper) { this.souper = souper; }
    public void setLogement(boolean logement) { this.logement = logement; }

    @Override
    public String toString() {
        return
                String.format(
                    "%-20s %-12s Souper:%-1s Logement:%-4s %-1s",
                    participant.toString(),
                    tarif.getNom(),
                    getSouper() ? "✅":"❌",
                    getLogement() ? "✅":"❌",
                    getPlagesAsString()

                );

//        sb.append(participant.toString()+" "+tarif.getNom()).append("🍔Souper :").append(getSouper() ? "✅":"❌")
//                .append(" 🏦Logement :").append(getLogement() ? "✅":"❌").append("  ➖  ");
//
//        for(Plage p : plages) {
//            sb.append(p.toString());
//        }
//        return sb.toString();
    }

    private String getPlagesAsString() {
        StringBuilder sb = new StringBuilder();

        for (Plage p : plages) {
            sb.append(p.toString()).append(" ");
        }

        return sb.toString().trim();
    }

}
