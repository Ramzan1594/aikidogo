package be.technifutur.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//REGROUPEMENT DE TOUS LES DATAS
// cette classe servira a regrouper toutes les données
//avec les modeles on a toutes les donné et pour les get un par par sera plus long
//donc on met tous ici et on pourra aller chercher tous au meme endroit
public class StageData implements Serializable {
    private List<Participant> participants = new ArrayList<>();
    private List<Inscription> inscriptions = new ArrayList<>();
    private List<Plage>  plages = new ArrayList<>();
    private Tarif tarif;

    public List<Participant> getParticipants() {return participants;}
    public List<Inscription> getInscriptions() {return inscriptions;}
    public List<Plage> getPlages() {return plages;}
    public Tarif getTarif() {return tarif;}

    public void setTarif(Tarif tarif) {this.tarif = tarif;}

    public String toString(Inscription ins,double prix){
        //get the participant in the list
        Participant personne = participants.stream()
                .filter(p->p.getId().equals(ins.getParticipant().getId()))
                .findFirst().orElse(null);

        return String.format("\n ---> %s doit payer %.2f€ et il a pris %d/%d plages.\n"
                ,personne.getNom(), prix, ins.getPlages().size(), plages.size());
    }
}
