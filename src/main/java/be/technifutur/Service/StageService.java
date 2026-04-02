package be.technifutur.Service;

import be.technifutur.Dsg;
import be.technifutur.Model.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//LOGIQUE METIER
// classe metier , c'est ici qu'on va faire les calcules pour ne pas les faires
//dans les classe model
public class StageService implements Serializable {
    private Scanner scan = new Scanner(System.in);


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

            if(ins.getSouper())
                total += t.getpSouper();
            if(ins.getLogement())
                total += t.getpLogement();

//            total *= ins.getParticipant().getType().getCoefficient();
        }
        return total;
    }

    public void menuEdit(StageData data) {
        boolean running = true;
        while(running){
            StringBuilder sb = new StringBuilder();
            sb.append("==== MODIFICATION ====*").append("0. Quitter*").append("1. Participant*")
                .append("2. Plages*").append("3. Tafifs*").append("4. Inscriptions*");

            System.out.print(Tabeau.displayInbox("",sb));
            sb.setLength(0);

            System.out.print("Choix :");
            String choix =  scan.nextLine();

            switch(choix){
                case "0" -> running = false;
                case "1" -> editParticipant(data.getParticipants());
                case "2" -> editPlages(data.getPlages(),data.getParticipants());
                case "3" -> editTarifs(data.getTarifs());
                case "4" -> editInscription(data.getInscriptions(), data.getPlages());
            }
        }

    }

    //ici on doit pouvoir modifier  plages, souper logement
    private void editInscription(List<Inscription> ins, List<Plage> listPl) {
        if(!ins.isEmpty()){
            boolean running = true;
            while(running){
                for (int i = 0; i < ins.size(); i++) {
                    System.out.printf("%-4d %s\n",i+1,ins.get(i).toString());
                }
                try{
                    System.out.print("Choix :");
                    String choix = scan.nextLine();

                    if(!choix.matches("\\d+"))
                        throw new Exception(Dsg.re+"❌ LE CHOIX DOIT ETRE UN NUMERO!"+ Dsg.r);
                    int posIns = Integer.parseInt(choix)-1;

                    if(posIns+1 > ins.size())
                        throw new Exception(Dsg.re+"❌ CHOIX INVALIDE!"+ Dsg.r);
                    else if(choix.equals("0"))
                        running = false;

                    boolean inRunning = true;
                    while(inRunning && running){
                        System.out.printf("Etat actuel de l'inscription\n%s\n",ins.get(posIns).toString());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Que desirez vous modifier ?*")
                        .append("0. Quitter.*").append("1. Ajouter plages.*").append("2. Supprimer plages.*")
                        .append("3. Souper.*").append("4. Logement*");

                        System.out.print(Tabeau.displayInbox("",sb));
                        sb.setLength(0);

                        System.out.print("Choix :");
                        String inChoix =  scan.nextLine();
                        int choixMod = Integer.parseInt(inChoix);

                        switch (choixMod){
                            case 0 -> inRunning = false;
                            case 1 -> {
                                listPl = listPl.stream()
                                        .filter(p->!ins.get(posIns).getPlages().contains(p))
                                        .toList();

                                if(listPl.isEmpty())
                                    throw new Exception(Dsg.re+Dsg.bo+String.format("❌ Aucune autre plage disponible pour %s!",ins.get(posIns).getParticipant().getNom())+ Dsg.r);

                                System.out.println("Selectionner la plage a ajouté : ");

                                for (int i = 0; i < listPl.size(); i++) {
                                    System.out.printf("%-4d %s\n",i+1,listPl.get(i).toString());
                                }
                                System.out.print("Choix :");
                                int posPl = scan.nextInt();
                                ins.get(posIns).addPlage(listPl.get(posIns));
                            }
                            case 2 -> {
                                System.out.println("Selectionner la plage a supprimer : ");
                                for (int i = 0; i < ins.get(posIns).getPlages().size(); i++) {
                                    System.out.printf("%-4d %s\n",i+1,ins.get(posIns).getPlages().get(i).toString());
                                }
                                System.out.print("Choix :");
                                int posPl = scan.nextInt()-1;
                                ins.get(posIns).removePlage(ins.get(posIns).getPlages().get(posPl));
                            }
                            case 3 -> {
                                System.out.println("Changer le souper : 1.oui - 2.non");
                                System.out.print("Choix :");
                                boolean bool = scan.nextInt() == 1 ? true : false;
                                ins.get(posIns).setSouper(bool);
                            }
                            case 4 -> {
                                System.out.println("Changer le logement : 1.oui - 2.non");
                                System.out.print("Choix :");
                                boolean bool = scan.nextInt() == 1 ? true : false;
                                ins.get(posIns).setSouper(bool);
                            }
                            default-> System.out.print(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }else{
            System.out.println(Dsg.re+"❌ AUCUNE INSCRIPTION ENREGISTREE!"+ Dsg.r);
        }
    }

    private void editPlages(List<Plage> listPl, List<Participant> listP) {
        if(!listPl.isEmpty()){
            boolean running = true;
            while(running){
                for(int i=0;i<listPl.size();i++){
                    System.out.printf("%-4d %s\n",i+1,listPl.get(i).toString());
                }
                try{
                    System.out.print("Choix :");
                    String choix =  scan.nextLine();

                    if(!choix.matches("\\d+"))
                        throw new Exception(Dsg.re+"❌ LE CHOIX DOIT ETRE UN NUMERO!"+ Dsg.r);
                    int posListPl = Integer.parseInt(choix)-1;

                    if(posListPl+1 > listPl.size())
                        throw new Exception(Dsg.re+"❌ CHOIX INVALIDE!"+ Dsg.r);
                    else if(choix.equals("0"))
                        running = false;

                    boolean inRunning = true;
                    while(inRunning && running){
                        System.out.printf("Etat actuel de la plage\n%s\n",listPl.get(posListPl).toString());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Que desirez vous modifier ?*").append("0. Quitter.*").append("1. Nom.*").append("2. Jour.*")
                                .append("3. Heure de début*").append("4. Heure de fin.*").append("4. Animateur.*");

                        System.out.print(Tabeau.displayInbox("",sb));
                        sb.setLength(0);

                        System.out.print("Choix :");
                        int inChoix =  scan.nextInt();
                        Scanner newInput = new Scanner(System.in);

                        switch (inChoix){
                            case 0 -> inRunning = false;
                            case 1 -> {
                                System.out.print("Nouveau nom : ");
                                listPl.get(posListPl).setNom(newInput.nextLine());
                            }
                            case 2 -> {
                                System.out.print("Nouveau Jour : ");
                                listPl.get(posListPl).setJour(newInput.nextLine());
                            }
                            case 3 -> {
                                System.out.print("Nouveau heure de début : ");
                                listPl.get(posListPl).setHeureDebut(newInput.nextLine());
                            }
                            case 4 -> {
                                System.out.print("Nouveau heure de fin : ");
                                listPl.get(posListPl).setHeureFin(newInput.nextLine());
                            }
                            case 5 -> {
                                System.out.print("Nouveau animateur : ");
                                for (int i = 0; i < listP.size(); i++) {
                                    System.out.printf("%-4d %s\n",i+1,listP.get(i).toString());
                                }
                                System.out.println("Choix :");
                                int posParti = scan.nextInt();
                                listPl.get(posListPl).setAnimateur(listP.get(posParti));
                            }
                            default-> System.out.print(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }else {
            System.out.println(Dsg.re+"❌ AUCUNE PLAGES ENREGISTREE!"+ Dsg.r);
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
                    System.out.print("Choix :");
                    String choix =  scan.nextLine();

                    if(!choix.matches("\\d+"))
                        throw new Exception(Dsg.re+"❌ LE CHOIX DOIT ETRE UN NUMERO!"+ Dsg.r);
                    int posListPar = Integer.parseInt(choix)-1;

                    if(posListPar+1 > listP.size())
                        throw new Exception(Dsg.re+"❌ CHOIX INVALIDE!"+ Dsg.r);
                    else if(choix.equals("0"))
                        running = false;

                    boolean inRunning = true;
                    while(inRunning && running){
                        System.out.printf("Etat actuel du participan\n%s\n",listP.get(posListPar).toString());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Que desirez vous modifier ?*").append("0. Quitter.*").append("1. Nom.*")
                        .append("2. Prenom.*").append("3. Club*").append("4. Type.*");

                        System.out.println(Tabeau.displayInbox("",sb));
                        sb.setLength(0);

                        System.out.print("Choix :");
                        int inChoix =  scan.nextInt();
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
//                                listP.get(posListPar).setType(ETypeParticipant.
//                                        valueOf(newInput.nextLine().toUpperCase()));
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
    private void editTarifs(HashMap<Integer,Tarif> dbTarifs) {
        if(dbTarifs != null)
        {
            System.out.println("Selectionner un tarif");
            for(Tarif tarif : dbTarifs.values()){
                System.out.println(tarif.getId()+"."+tarif.toString());
            }

            System.out.println("Choix :");
            String choix = scan.nextLine();
            int ch = Integer.parseInt(choix);

            Tarif tarif = dbTarifs.get(ch);
            boolean running = true;

            while(running){
                System.out.printf("Voici les tarifs actuels\n%s",tarif.toString());
                StringBuilder sb = new StringBuilder();
                sb.append("Que desirez vous modifier ?*").append("0. Quitter.*").append("1. Prix plage.*")
                .append("2. Prix souper.*").append("3. Prix logement.*").append("4. Prix full.*");

                System.out.print(Tabeau.displayInbox("",sb));
                sb.setLength(0);

                System.out.print("Choix :");
                choix = scan.nextLine();

                if(choix.equals("0"))
                    running = false;

                switch (choix){
                    case "0" -> running = false;
                    case "1"->{
                        System.out.print("Nouveau prix plage : ");
                        tarif.setpPlage(scan.nextDouble());
                    }
                    case "2"->{
                        System.out.print("Nouveau prix souper : ");
                        tarif.setpSouper(scan.nextDouble());
                    }
                    case "3"->{
                        System.out.print("Nouveau prix logement: ");
                        tarif.setpLogement(scan.nextDouble());
                    }
                    case "4"->{
                        System.out.print("Nouveau prix full: ");
                        tarif.setpFullPlages(scan.nextDouble());
                    }
                    default-> System.out.print(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
                }
            }
        }else
        {
            System.out.println(Dsg.re+"❌ AUCUN TARIF DEFINIE!"+ Dsg.r);
        }
    }


}
