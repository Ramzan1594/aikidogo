package be.technifutur.Service;

import be.technifutur.Model.Inscription;
import be.technifutur.Model.Participant;
import be.technifutur.Model.Plage;
import be.technifutur.Model.Tarif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//LOGIQUE METIER
// classe metier , c'est ici qu'on va faire les calcules pour ne pas les faires
//dans les classe model
public class StageService implements Serializable {
    public double calculerPrix(Inscription ins, Tarif t, int totalPlages) {
        int nbPlage = ins.getPlages().size();
        double total;
        if (t == null) {
            System.out.println("⚠️ Aucun tarif défini !");
            total = 0;
        }else{
            if(nbPlage == totalPlages)
                total = t.getpFullPlages();
            else
                total = nbPlage * t.getpPlage();

            if(ins.isSouper())
                total += t.getpSouper();
            if(ins.isLogement())
                total += t.getpLogement();

            total *= ins.getParticipant().getType().getCoefficient();
        }
        return total;
    }
}
