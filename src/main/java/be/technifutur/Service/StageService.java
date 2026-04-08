package be.technifutur.Service;

import be.technifutur.Dsg;
import be.technifutur.Model.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//LOGIQUE METIER
//classe metier , c'est ici qu'on va faire les calcules pour ne pas les faires
//dans les classe model
public class StageService implements Serializable {
    private Scanner scan = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();

    public double calculerPrix(Inscription ins, Tarif t, int totalPlages) {
        int nbPlage = ins.getPlages().size();
        double total;
        if (t == null) {
            System.out.println("⚠️ Aucun tarif défini !");
            total = 0;
        } else {
            if (nbPlage == totalPlages)
                total = t.getpFullPlages();
            else
                total = nbPlage * t.getpPlage();

            if (ins.getSouper())
                total += t.getpSouper();
            if (ins.getLogement())
                total += t.getpLogement();

//            total *= ins.getParticipant().getType().getCoefficient();
        }
        return total;
    }

    public void menuEdit(StageData data, boolean delete) {
        boolean running = true;
        while (running) {

            sb.append(delete ? "==== SUPPRESSION ====*" : "==== MODIFICATION ====*").append("Q. Quitter*").append("1. Participant*")
                    .append("2. Plages*").append("3. Tafifs*").append("4. Inscriptions*");

            System.out.print(Tableau.displayInbox("", sb));
            sb.setLength(0);

            System.out.print("\nChoix :");
            String choix = scan.nextLine().toLowerCase();

            //si on veut le menu edit
            if (!delete) {
                switch (choix) {
                    case "q" -> running = false;
                    case "1" -> editParticipant(data.getParticipants());
                    case "2" -> editPlages(data.getPlages(), data.getParticipants());
                    case "3" -> editTarifs(data.getAllTarifs());
                    case "4" -> editInscription(data.getInscriptions(), data.getPlages());
                    default -> System.out.println(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);
                }
            } else {//pour le menu delete
                switch (choix) {
                    case "q" -> running = false;
                    case "1" -> deleteParticipant(data.getParticipants());
                    case "2" -> deletePlages(data.getPlages());
                    case "3" -> deleteTarif(data.getAllTarifs());
                    case "4" -> deleteInscription(data.getInscriptions());
                    default -> System.out.println(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);
                }
            }
        }
    }

    private void deleteInscription(List<Inscription> listInsc) {
        if (!listInsc.isEmpty()) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner l'inscription a supprimer");
                for (int i = 0; i < listInsc.size(); i++) {
                    System.out.printf("%-3d %s\n", i + 1, listInsc.get(i));
                }
                try {
                    System.out.print("Choix :");
                    String choix = scan.nextLine();

                    if (choix.equals("q"))
                        running = false;
                    else {
                        if (!choix.matches("\\d+"))
                            throw new Exception(Dsg.re + "❌ LE CHOIX DOIT ETRE UN NUMERO!" + Dsg.r);
                        int posIns = Integer.parseInt(choix) - 1;

                        if (posIns < 0 || posIns > listInsc.size())
                            throw new Exception(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);


                        System.out.printf("Supprimer l'inscription ? 1.oui 2.non \n%s\n",
                                listInsc.get(posIns));

                        System.out.print("\nChoix :");
                        choix = scan.nextLine().toLowerCase();

                        if(choix.equalsIgnoreCase("1")){
                            System.out.printf(Dsg.re + "Inscription %s %s SUPPRIMEE\n\n" + Dsg.r,
                                    listInsc.get(posIns).getParticipant().getNom(),
                                    listInsc.get(posIns).getParticipant().getPrenom());
                            listInsc.remove(posIns);
                        }



                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }else{
            System.out.println(Dsg.re + "❌ AUCUNE INSCRIPTION ENREGISTREE!" + Dsg.r);
        }
    }

    private void deleteTarif(HashMap<Integer, Tarif> dbTarifs) {
        boolean running = true;
        while (running) {
            System.out.println("Selectionner le tarif a supprimer");
            for (Tarif tarif : dbTarifs.values()) {
                System.out.println(tarif.toString());
            }

            System.out.print("Choix :");
            String choix = scan.nextLine().toLowerCase();

            if (choix.equalsIgnoreCase("q") || !choix.matches("\\d+"))
                running = false;
            else {
                int posTar =  Integer.parseInt(choix);

                if(posTar > 0 && posTar <= dbTarifs.size()) {
                    System.out.printf("Supprimer le tarif '%s' ? 1.oui 2.non\n", dbTarifs.get(posTar).getNom());
                    System.out.print(dbTarifs.get(posTar));

                    System.out.print("\nChoix :");
                    choix = scan.nextLine().toLowerCase();

                    if(choix.equalsIgnoreCase("1")){
                        System.out.printf(Dsg.re + "%s SUPPRIMER\n\n" + Dsg.r, dbTarifs.get(posTar));
                        dbTarifs.remove(posTar);
                    }

                }else{
                    System.out.println(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);
                }


            }

        }
    }

    private void deletePlages(List<Plage> listPl) {
        boolean running = true;
        while (running) {
            System.out.println("Selectionner la plage a supprimer");
            for (int i = 0; i < listPl.size(); i++) {
                System.out.printf("%-3d %s\n", i + 1, listPl.get(i));
            }
            System.out.print("\nChoix :");
            String choix = scan.nextLine().toLowerCase();

            if(choix.equalsIgnoreCase("q") || !choix.matches("\\d+")) {
                running = false;
            }else{
                int posPl =  Integer.parseInt(choix)-1;

                if(posPl >= 0 && posPl < listPl.size()) {
                    System.out.println("Supprimer la plage ? 1.oui 2.non");
                    System.out.printf("%-3d %s\n", posPl+1, listPl.get(posPl));

                    System.out.print("\nChoix :");
                    choix = scan.nextLine().toLowerCase();

                    if(choix.equalsIgnoreCase("1")){
                        System.out.printf(Dsg.re + "%s SUPPRIMER\n\n" + Dsg.r, listPl.get(posPl));
                        listPl.remove(posPl);
                    }

                }else{
                    System.out.println(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);
                }
            }
        }
    }

    private void deleteParticipant(List<Participant> listP) {
        if (!listP.isEmpty()) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner le participant a supprimer");
                for (int i = 0; i < listP.size(); i++) {
                    System.out.printf("%-3d %s\n", i + 1, listP.get(i));
                }
                try {
                    System.out.print("\nChoix :");
                    String choix = scan.nextLine();

                    if (choix.equalsIgnoreCase("q"))
                        running = false;
                    else {
                        if (!choix.matches("\\d+"))
                            throw new Exception(Dsg.re + "❌ LE CHOIX DOIT ETRE UN NUMERO!" + Dsg.r);
                        int posListPar = Integer.parseInt(choix) - 1;

                        if (posListPar < 0 || posListPar + 1 > listP.size())
                            throw new Exception(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);

                        boolean inRunning = true;
                        while (inRunning && running) {
                            System.out.println("Supprimer ce participant ? 1.oui 2.non");
                            System.out.println(listP.get(posListPar));
                            System.out.print("\nChoix :");
                            String rep = scan.nextLine();
                            if (rep.equalsIgnoreCase("1")) {
                                System.out.printf(Dsg.re + "%s SUPPRIMER\n" + Dsg.r, listP.get(posListPar));
                                listP.remove(posListPar);
                            }
                            inRunning = false;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    //ici on doit pouvoir modifier  plages, souper logement
    private void editInscription(List<Inscription> ins, List<Plage> listPl) {
        if (!ins.isEmpty()) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner une inscription");
                for (int i = 0; i < ins.size(); i++) {
                    System.out.printf("%-3d %s\n", i + 1, ins.get(i));
                }
                try {
                    System.out.print("\nChoix :");
                    String choix = scan.nextLine();

                    if (choix.equals("q"))
                        running = false;
                    else {
                        if (!choix.matches("\\d+"))
                            throw new Exception(Dsg.re + "❌ LE CHOIX DOIT ETRE UN NUMERO!" + Dsg.r);
                        int posIns = Integer.parseInt(choix) - 1;

                        if (posIns < 0 || posIns >= ins.size())
                            throw new Exception(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);

                        boolean inRunning = true;
                        while (inRunning && running) {
                            System.out.printf("Etat actuel de l'inscription\n%s\n", ins.get(posIns));

                            sb.append("Que desirez vous modifier ?*")
                                    .append("Q. Quitter.*").append("1. Ajouter plages.*").append("2. Supprimer plages.*")
                                    .append("3. Souper.*").append("4. Logement*");

                            System.out.print(Tableau.displayInbox("", sb));
                            sb.setLength(0);

                            System.out.print("\nChoix :");
                            String inChoix = scan.nextLine().toLowerCase();

                            switch (inChoix) {
                                case "q" -> inRunning = false;
                                case "qq" ->{inRunning = false; running = false;}
                                case "1" -> {
                                    listPl = listPl.stream()
                                            .filter(p -> !ins.get(posIns).getPlages().contains(p))
                                            .toList();

                                    if (listPl.isEmpty())
                                        throw new Exception(Dsg.re + Dsg.bo + String.format("❌ Aucune autre plage disponible pour %s!", ins.get(posIns).getParticipant().getNom()) + Dsg.r);

                                    System.out.println("Selectionner la plage a ajouté : ");

                                    for (int i = 0; i < listPl.size(); i++) {
                                        System.out.printf("%-4d %s\n", i + 1, listPl.get(i));
                                    }
                                    System.out.print("\nChoix :");
                                    int posPl = scan.nextInt();
                                    ins.get(posIns).addPlage(listPl.get(posIns));
                                }
                                case "2" -> {
                                    System.out.println("Selectionner la plage a supprimer : ");
                                    for (int i = 0; i < ins.get(posIns).getPlages().size(); i++) {
                                        System.out.printf("%-4d %s\n", i + 1, ins.get(posIns).getPlages().get(i));
                                    }
                                    System.out.print("\nChoix :");
                                    int posPl = scan.nextInt() - 1;
                                    ins.get(posIns).removePlage(ins.get(posIns).getPlages().get(posPl));
                                }
                                case "3" -> {
                                    System.out.println("Changer le souper : 1.oui - 2.non");
                                    System.out.print("\nChoix :");
                                    boolean bool = scan.nextInt() == 1 ? true : false;
                                    ins.get(posIns).setSouper(bool);
                                }
                                case "4" -> {
                                    System.out.println("Changer le logement : 1.oui - 2.non");
                                    System.out.print("Choix :");
                                    boolean bool = scan.nextInt() == 1 ? true : false;
                                    ins.get(posIns).setSouper(bool);
                                }
                                default -> System.out.print(Dsg.re + Dsg.bo + "❌ CHOIX INVALIDE !" + Dsg.r);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println(Dsg.re + "❌ AUCUNE INSCRIPTION ENREGISTREE!" + Dsg.r);
        }
    }

    private void editPlages(List<Plage> listPl, List<Participant> listP) {
        if (!listPl.isEmpty()) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner la plage a modifier");
                for (int i = 0; i < listPl.size(); i++) {
                    System.out.printf("%-3d %s\n", i + 1, listPl.get(i));
                }
                try {
                    System.out.print("\nChoix :");
                    String choix = scan.nextLine().toLowerCase();

                    if (choix.equals("q"))
                        running = false;
                    else {
                        if (!choix.matches("\\d+"))
                            throw new Exception(Dsg.re + "❌ LE CHOIX DOIT ETRE UN NUMERO!" + Dsg.r);
                        int posListPl = Integer.parseInt(choix) - 1;

                        if (posListPl < 0 || posListPl >= listPl.size())
                            throw new Exception(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);


                        boolean inRunning = true;
                        while (inRunning && running) {
                            Plage p = listPl.get(posListPl);

                            sb.append("Que desirez vous modifier ?*").append("Q. Quitter.*")
                                    .append(String.format("1. Nom : %s*", p.getNom()))
                                    .append(String.format("2. Jour : %s*", p.getJour()))
                                    .append(String.format("3. Heure de début : %s*", p.getHeureDebut()))
                                    .append(String.format("4. Heure de fin : %s*", p.getHeureFin()))
                                    .append(String.format("4. Animateur : %s*", p.getAnimateur()));

                            System.out.print(Tableau.displayInbox("", sb));
                            sb.setLength(0);

                            System.out.print("\nChoix :");
                            String inChoix = scan.nextLine().toLowerCase();


                            switch (inChoix) {
                                case "q" -> inRunning = false;
                                case "qq" -> {inRunning = false;running = false;}
                                case "1" -> {
                                    System.out.print("Nouveau nom : ");
                                    String nom = scan.nextLine();
                                    if (!nom.equalsIgnoreCase("q"))
                                        listPl.get(posListPl).setNom(nom);
                                }
                                case "2" -> {
                                    System.out.print("Nouveau Jour : ");
                                    String jour = scan.nextLine();
                                    if (!jour.equalsIgnoreCase("q"))
                                        listPl.get(posListPl).setJour(jour);
                                }
                                case "3" -> {
                                    System.out.print("Nouveau heure de début : ");
                                    String hd = scan.nextLine();
                                    if (!hd.equalsIgnoreCase("q"))
                                        listPl.get(posListPl).setHeureDebut(hd);
                                }
                                case "4" -> {
                                    System.out.print("Nouveau heure de fin : ");
                                    String hf = scan.nextLine();
                                    if (!hf.equalsIgnoreCase("q"))
                                        listPl.get(posListPl).setHeureFin(hf);
                                }
                                case "5" -> {
                                    System.out.print("Nouveau animateur : ");
                                    for (int i = 0; i < listP.size(); i++) {
                                        System.out.printf("%-4d %s\n", i + 1, listP.get(i));
                                    }
                                    System.out.println("\nChoix :");
                                    String posParti = scan.nextLine();
                                    if (!posParti.equalsIgnoreCase("q"))
                                        listPl.get(posListPl).setAnimateur(
                                                listP.get(Integer.parseInt(posParti)));
                                }
                                default -> System.out.print(Dsg.re + Dsg.bo + "❌ CHOIX INVALIDE !" + Dsg.r);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println(Dsg.re + "❌ AUCUNE PLAGES ENREGISTREE!" + Dsg.r);
        }
    }

    public void editParticipant(List<Participant> listP) {
        if (!listP.isEmpty()) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner le participant a modifier");
                for (int i = 0; i < listP.size(); i++) {
                    System.out.printf("%-3d %s\n", i + 1, listP.get(i));
                }
                try {
                    System.out.print("\nChoix :");
                    String choix = scan.nextLine();

                    if (choix.equalsIgnoreCase("q") || !choix.matches("\\d+"))
                        running = false;
                    else {
                        if (!choix.matches("\\d+"))
                            throw new Exception(Dsg.re + "❌ LE CHOIX DOIT ETRE UN NUMERO!" + Dsg.r);
                        int posListPar = Integer.parseInt(choix) - 1;

                        if (posListPar < 0 || posListPar >= listP.size())
                            throw new Exception(Dsg.re + "❌ CHOIX INVALIDE!" + Dsg.r);

                        boolean inRunning = true;
                        while (inRunning && running) {
                            Participant p = listP.get(posListPar);

                            sb.append("Que desirez vous modifier ?*").append("Q. Quitter.*")
                                    .append(String.format("1. Nom: %s*", p.getNom()))
                                    .append(String.format("2. Prénom: %s*", p.getPrenom()))
                                    .append(String.format("3. Club: %s*", p.getClubName()))
                                    .append(String.format("4. Type: %s*",""));

                            System.out.println(Tableau.displayInbox("", sb));
                            sb.setLength(0);

                            System.out.print("Choix :");
                            choix = scan.nextLine();

                            switch (choix) {
                                case "q" -> inRunning = false;
                                case "qq" -> {running = false; inRunning = false;}
                                case "1" -> {
                                    System.out.print("Nouveau nom : ");
                                    listP.get(posListPar).setNom(scan.nextLine());
                                }
                                case "2" -> {
                                    System.out.print("Nouveau prenom : ");
                                    listP.get(posListPar).setPrenom(scan.nextLine());
                                }
                                case "3" -> {
                                    System.out.print("Nouveau club : ");
                                    listP.get(posListPar).setClubName(scan.nextLine());
                                }
                                case "4" -> {
                                    System.out.print("Nouveau type : ");
                                    //                                listP.get(posListPar).setType(ETypeParticipant.
                                    //                                        valueOf(scan.nextLine().toUpperCase()));
                                }
                                default -> System.out.print(Dsg.re + Dsg.bo + "❌ CHOIX INVALIDE !" + Dsg.r);
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println(Dsg.re + "❌ AUCUN PARTICIPANT ENREGISTREE!" + Dsg.r);
        }
    }

    //function qui modifie un tarif existant
    private void editTarifs(HashMap<Integer, Tarif> dbTarifs) {
        if (dbTarifs != null) {
            boolean running = true;
            while (running) {
                System.out.println("Selectionner le tarif a modifier");
                for (Tarif tarif : dbTarifs.values()) {
                    System.out.println(tarif.toString());
                }

                System.out.print("Choix :");
                String choix = scan.nextLine().toLowerCase();

                if (choix.equalsIgnoreCase("q") || !choix.matches("\\d+"))
                    running = false;
                else {
                    int posTar = Integer.parseInt(choix);
                    Tarif selectedT = dbTarifs.get(posTar);
                    boolean inRunning = true;
                    while (inRunning && running) {

                        if (selectedT != null) {
                            System.out.printf("Tarif selectionné\n%s", selectedT);
                            sb.append("Que desirez vous modifier ?*").append("Q. Quitter.*")
                                    .append("1. Nom").append("2. Prix plage.*")
                                    .append("3. Prix souper.*").append("4. Prix logement.*").append("5. Prix full.*");
                            System.out.print(Tableau.displayInbox("", sb));
                            sb.setLength(0);

                            System.out.print("\nChoix :");
                            choix = scan.nextLine().toLowerCase();

                            if (choix.equalsIgnoreCase("q") || !choix.matches("\\d+"))
                                inRunning = false;
                            else {
                                switch (choix) {
                                    case "1" -> {
                                        System.out.print("Nouveau nom: ");
                                        String input = scan.nextLine();
                                        if (input.equalsIgnoreCase("q"))
                                            inRunning = false;
                                        else
                                            selectedT.setNom(input);
                                    }
                                    case "2" -> {
                                        System.out.print("Nouveau prix plage : ");
                                        String input = scan.nextLine();
                                        if (input.equalsIgnoreCase("q"))
                                            inRunning = false;
                                        else
                                            selectedT.setpPlage(Double.parseDouble(input));
                                    }
                                    case "3" -> {
                                        System.out.print("Nouveau prix souper : ");
                                        String input = scan.nextLine();
                                        if (input.equalsIgnoreCase("q"))
                                            inRunning = false;
                                        else
                                            selectedT.setpSouper(Double.parseDouble(input));
                                    }
                                    case "4" -> {
                                        System.out.print("Nouveau prix logement: ");
                                        String input = scan.nextLine();
                                        if (input.equalsIgnoreCase("q"))
                                            inRunning = false;
                                        else
                                            selectedT.setpLogement(Double.parseDouble(input));
                                    }
                                    case "5" -> {
                                        System.out.print("Nouveau prix full: ");
                                        String input = scan.nextLine();
                                        if (input.equalsIgnoreCase("q"))
                                            inRunning = false;
                                        else
                                            selectedT.setpFullPlages(Double.parseDouble(input));
                                    }
                                    default -> System.out.print(Dsg.re + Dsg.bo + "❌ CHOIX INVALIDE !" + Dsg.r);
                                }
                            }
                        } else {
                            System.out.println(Dsg.re + Dsg.bo + "❌ CHOIX INVALIDE !" + Dsg.r);
                            inRunning = false;
                        }

                    }
                }
            }
        } else {
            System.out.println(Dsg.re + "❌ AUCUN TARIF DEFINIE!" + Dsg.r);
        }
    }

}
