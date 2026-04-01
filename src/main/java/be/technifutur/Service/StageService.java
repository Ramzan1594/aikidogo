package be.technifutur.Service;

import be.technifutur.Dsg;
import be.technifutur.Model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void menuEdit(StageData data) {
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        while(running){
            StringBuilder sb = new StringBuilder();
            sb.append("==== MODIFICATION ====*");
            sb.append("0. Quitter*");
            sb.append("1. Participant*");
            sb.append("2. Plages*");
            sb.append("3. Tafifs*");
            sb.append("4. Inscriptions*");
            System.out.print(Tabeau.displayInbox("",sb));
            sb.setLength(0);

            System.out.print("Choix :");
            String choix =  scan.nextLine();

            switch(choix){
                case "0" -> running = false;
                case "1" -> editParticipant(data.getParticipants());
                case "2" -> listerParticipant();
                case "3" -> editTarifs(data.getTarif());
                case "4" -> listerParticipant();
            }


        }

    }

    public void editParticipant(List<Participant> listP) {
        if(!listP.isEmpty()){
            boolean running = true;
            while(running){
                for(int i=0;i<listP.size();i++){
                    System.out.printf("%-4d %s\n",i+1,listP.get(i).toString());
                }
                try{
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Choix :");
                    String choix =  scan.nextLine();


                    if(!choix.matches("\\d+"))
                        throw new Exception(Dsg.re+"❌ LE CHOIX DOIT ETRE UN NUMERO!"+ Dsg.r);
                    int posListPar = Integer.parseInt(choix)-1;

                    if(posListPar+1 > listP.size())
                        throw new Exception(Dsg.re+"❌ CHOIX INVALIDE!"+ Dsg.r);

                    boolean inRunning = true;
                    while(inRunning){
                        Participant toEdit = listP.get(posListPar);
                        System.out.printf("Etat actuel du participan\n%s\n",toEdit.toString());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Que desirez vous modifier ?*");
                        sb.append("0. Quitter.*");
                        sb.append("1. Nom.*");
                        sb.append("2. Prenom.*");
                        sb.append("3. Club*");
                        sb.append("4. Type.*");

                        System.out.println(Tabeau.displayInbox("",sb));
                        sb.setLength(0);

                        Scanner scan2 = new Scanner(System.in);
                        System.out.print("Choix :");
                        int inChoix =  scan2.nextInt();
                        Scanner newInput = new Scanner(System.in);

                        switch (inChoix){
                            case 0 -> inRunning = false;
                            case 1 -> {
                                System.out.print("Nouveau nom : ");
                                listP.get(posListPar).setNom(newInput.nextLine());
                            }
                            case 2 -> {
                                System.out.print("Nouveau prenom : ");
                                listP.get(posListPar).setPrenom(newInput.nextLine());
                            }
                            case 3 -> {
                                System.out.print("Nouveau club : ");
                                listP.get(posListPar).setClubName(newInput.nextLine());
                            }
                            case 4 -> {
                                System.out.print("Nouveau type : ");
                                listP.get(posListPar).setType(ETypeParticipant.
                                        valueOf(newInput.nextLine().toUpperCase()));
                            }
                            default-> System.out.print(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }else {
            System.out.println(Dsg.re+"❌ AUCUN PARTICIPANT ENREGISTREE!"+ Dsg.r);
        }
    }

    //function qui modifie un tarif existant
    private void editTarifs(Tarif dbTarif) {
        if(dbTarif != null)
        {
            Tarif newTafif = new Tarif(dbTarif);
            boolean running = true;

            while(running){
                System.out.printf("Voici les tarifs actuels\n%s",dbTarif.toString());
                StringBuilder sb = new StringBuilder();
                sb.append("Que desirez vous modifier ?*");
                sb.append("0. Quitter.*");
                sb.append("1. Prix plage.*");
                sb.append("2. Prix souper.*");
                sb.append("3. Prix logement.*");
                sb.append("4. Prix full.*");

                System.out.print(Tabeau.displayInbox("",sb));
                sb.setLength(0);

                Scanner scan = new Scanner(System.in);
                System.out.print("Choix :");
                String choix = scan.nextLine();
                Scanner editVal = new Scanner(System.in);

                switch (choix){
                    case "0" -> running = false;
                    case "1"->{
                        System.out.print("Nouveau prix plage : ");
                        dbTarif.setpPlage(editVal.nextDouble());
                    }
                    case "2"->{
                        System.out.print("Nouveau prix souper : ");
                        dbTarif.setpSouper(editVal.nextDouble());
                    }
                    case "3"->{
                        System.out.print("Nouveau prix logement: ");
                        dbTarif.setpLogement(editVal.nextDouble());
                    }
                    case "4"->{
                        System.out.print("Nouveau prix full: ");
                        dbTarif.setpFullPlages(editVal.nextDouble());
                    }
                    default-> System.out.print(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
                }
            }
        }else
        {
            System.out.println(Dsg.re+"❌ AUCUN TARIF DEFINIE!"+ Dsg.r);
        }
    }

    private void listerParticipant() {
    }
}
