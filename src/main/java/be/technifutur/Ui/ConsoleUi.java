package be.technifutur.Ui;

import be.technifutur.Dsg;
import be.technifutur.Model.*;
import be.technifutur.Service.StageService;
import be.technifutur.Storage.DataStorage;

import javax.swing.*;
import java.lang.classfile.instruction.TableSwitchInstruction;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ConsoleUi {

    private StageService service;
    private StageData data;
    private Scanner scanner;
    private String stageLoaded;
    private String stroke;

    public ConsoleUi(StageService service, StageData data) {
        this.service = service;
        this.data = data;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            stageLoaded = data.getNbrTarifs() != 0 && data.getParticipants().size() != 0 && data.getPlages().size() != 0 ? Dsg.pu:Dsg.r;
//            stroke = data.getParticipants().size() > 0? "":Dsg.st;
            System.out.println(stageLoaded +"\n┌────────────────────────────────────────┐\n" +
                                         "│============  MENU AIKIDOGO  ===========│"+ Dsg.r);
            System.out.println(stageLoaded+"├────────────────────────────────────────┤"+Dsg.r);
            System.out.println(stageLoaded+"│"+ Dsg.re +"Q. Quitter"+ stageLoaded +"                              │");
            System.out.println("│"+ Dsg.gr +"1. Charger fichier"+ stageLoaded+"                      │");
            System.out.println("│"+ Dsg.gr +"2. ENREGISTRE LES DONNEES"+ stageLoaded +"               │");
            System.out.println("├────────────────────────────────────────┤");

            System.out.println("│"+Dsg.wh+"3. Définir les tarifs"+ stageLoaded+"                   │");
            System.out.println("│"+Dsg.wh+"4. Ajouter un participant"+ stageLoaded+"               │");
            System.out.println("│"+ Dsg.wh +"5. Créer une plage"+ stageLoaded+"                      │");
            System.out.println("│"+ Dsg.wh + "6. Affecter un animateur"+ stageLoaded+"                │");
            System.out.println("│"+ Dsg.wh + "7. Inscrire un participant"+ stageLoaded+"              │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println("│"+ Dsg.wh +"a. Calculer le prix d'un participant"+ stageLoaded+"    │");
            System.out.println("│"+ Dsg.wh +"z. Afficher plages"+ stageLoaded+"                      │");
            System.out.println("│"+ Dsg.wh +"e. Afficher participants/inscriptions"+ stageLoaded+"   │");
            System.out.println("│"+ Dsg.wh +"r. Afficher tarifs"+ stageLoaded+"                      │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println(stageLoaded+"│"+ Dsg.wh +"f. Menu modification"+ stageLoaded +"                    │");
            System.out.println(stageLoaded+"│"+ Dsg.wh +"d. Menu supression"+ stageLoaded +"                      │");
            System.out.println(stageLoaded +"└────────────────────────────────────────┘"+ Dsg.r);

            System.out.print("\nChoix : ");
            String choix = scanner.nextLine().toLowerCase();

            switch (choix) {
                case "q" -> running = false;
                case "1" -> loadFile();
                case "2" -> saveStageData();

                case "3" -> definirTarif();
                case "4" -> ajouterParticipant();
                case "5" -> creerPlage();
                case "6" -> affecterAnimateur();
                case "7" -> inscrireParticipant();

                case "a" -> calculerPrix();
                case "z" -> afficherPlages();
                case "e" -> afficherInscriptions();
                case "r" -> afficherTarif();

                case "f" -> service.menuEdit(data,false);
                case "d" -> service.menuEdit(data,true);
                default -> System.out.println(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
            }
        }
    }

    private void afficherPlages() {
        System.out.println(String.format("🥋Animateur%-5s Plages%-4s 🕛Horaire%-3s","", "",""));
        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < data.getPlages().size(); i++) {
            System.out.println(data.getPlages().get(i).toString());
        }
    }

    private void loadFile() {
        data = DataStorage.load();
    }

    private void saveStageData() {
        DataStorage storage = new DataStorage();
        storage.save(data);
    }

    private void definirTarif() {
        StringBuilder sb = new StringBuilder().append("=== Définition des tarifs ===*");
        System.out.print(Tableau.displayInbox("",sb));
        System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);

        System.out.print("Nom du tarif : ");
        String nom = scanner.nextLine();

        if (nom.equalsIgnoreCase("q")) return;

        System.out.print("Prix par plage : ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) return;
        double prixPlage = Double.parseDouble(input);

        System.out.print("Prix souper : ");
        input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) return;
        double prixSouper = Double.parseDouble(input);

        System.out.print("Prix logement : ");
        input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) return;
        double prixLogement = Double.parseDouble(input);

        System.out.print("Prix full (toutes les plages) : ");
        input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) return;
        double prixFull = Double.parseDouble(input);

        Tarif tarif = new Tarif(
                data.getNbrTarifs(),
                nom,
                prixPlage,
                prixSouper,
                prixLogement,
                prixFull
        );

        data.getAllTarifs().put(tarif.getId(), tarif);

        System.out.print(Dsg.re + "✅ TARIFS ENREGISTRÉS !" + Dsg.r);
    }

    private void afficherTarif() {
        if (data.getAllTarifs().size() == 0) {
            System.out.print(Dsg.re +"AUNCUN TARIF DEFINI."+ Dsg.r);
        } else {
            for (Tarif t : data.getAllTarifs().values())
            {
                System.out.println(t.toString());
            }
        }
    }


    private void ajouterParticipant() {
        StringBuilder sb = new StringBuilder().append("=== Ajout participant ===*");
        System.out.print(Tableau.displayInbox("",sb));
        System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);

        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        if (nom.equalsIgnoreCase("q")) return;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        if (prenom.equalsIgnoreCase("q")) return;

        System.out.print("Tel : ");
        String tel = scanner.nextLine();
        if (tel.equalsIgnoreCase("q")) return;

        System.out.print("Email : ");
        String email = scanner.nextLine();
        if (email.equalsIgnoreCase("q")) return;

        System.out.print("Club : ");
        String club = scanner.nextLine();
        if (club.equalsIgnoreCase("q")) return;

        Participant p = new Participant(club, email, tel,prenom, nom);
        data.getParticipants().add(p);
        System.out.printf(Dsg.re +"✅ PARTICIPANT AJOUTE : %s"+ Dsg.r, p);
    }

    private void creerPlage() {
        StringBuilder sb = new StringBuilder().append("=== Création plage ===*");
        System.out.print(Tableau.displayInbox("",sb));
        System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);

        System.out.print("Nom de la plage : ");
        String nom = scanner.nextLine();
        if (nom.equalsIgnoreCase("q")) return;

        System.out.print("Jour : ");
        String jour = scanner.nextLine();
        if (jour.equalsIgnoreCase("q")) return;

        System.out.print("Heure début : ");
        String debut = scanner.nextLine();
        if (debut.equalsIgnoreCase("q")) return;

        System.out.print("Heure fin : ");
        String fin = scanner.nextLine();
        if (fin.equalsIgnoreCase("q")) return;


        Plage p = new Plage(nom, jour, debut, fin);
        data.getPlages().add(p);
        System.out.printf(Dsg.re +"✅ PLAGE CREE : %s"+ Dsg.r, p);
    }

    private void inscrireParticipant() {
        StringBuilder sb = new StringBuilder().append("=== Inscription participant ===*");
        System.out.print(Tableau.displayInbox("",sb));
        System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);

        boolean running = true;

        while (running) {
            Participant p = choisirParticipant();
            if (p == null) return;

            Inscription ins = new Inscription(p);

            System.out.println("Choisisser un tarif :");
            for(Tarif t : data.getAllTarifs().values()){
                System.out.printf("%2d. %s\n",t.getId(),t.getNom());
            }
            System.out.print("\nChoix :");
            String input =  scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                running  = false;
                return;
            }
            int choix = Integer.parseInt(input);

            ins.setTarif(data.getAllTarifs().get(choix));

            System.out.print("Souper ? (1.oui/2.non) : ");
            input =  scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                running  = false;
                return;
            }
            boolean bool = input.equalsIgnoreCase("1") ? true : false;
            ins.setSouper(bool);

            System.out.print("Logement ? (1.oui/2.non) : ");
            input =  scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                running  = false;
                return;
            }
            bool = input.equalsIgnoreCase("1") ? true : false;
            ins.setLogement(bool);

            System.out.println("Choisissez les plages :");
            List<Plage> plages = data.getPlages();
            for (int i = 0; i < plages.size(); i++) {
                System.out.println((i + 1) + ". " + plages.get(i));
            }
            boolean inRunning = true;
            while (inRunning) {
                System.out.print("Numéro plage (0 pour terminer): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")){
                    running  = false;
                    inRunning  = false;
                    return;
                }
                int num  = Integer.parseInt(input);

                if (num == 0) {
                    inRunning = false;
                }else{
                    if (num >= 1 && num <= plages.size()) {
                        try {
                            ins.addPlage(plages.get(num - 1));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+ Dsg.r);
                    }
                }
            }

            data.getInscriptions().add(ins);
            System.out.println(Dsg.re +"✅ INSCRIPTION AJOUTEE !\n"+ Dsg.r);
        }
    }

    private void affecterAnimateur() {
        StringBuilder sb = new StringBuilder().append("=== Affectation animateur ===*");
        System.out.print(Tableau.displayInbox("",sb));
        System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);

        Plage p = choisirPlage();
        if (p == null) return;
        Participant animateur = choisirParticipant();
        if (animateur == null) return;
            p.setAnimateur(animateur);
        System.out.printf(Dsg.re +"✅ ANIMATEUR %s AFFECTE à %s"+ Dsg.r,animateur.toString() , p.getNom());

    }

    private void calculerPrix() {
        Participant p = choisirParticipant();
        if (p == null) return;

        Inscription insc = data.getInscriptions().stream()
                .filter(i -> i.getParticipant().equals(p))
                .findFirst()
                .orElse(null);

        if (insc == null) {
            System.out.print(Dsg.re+Dsg.bo+"❌ AUNCUNE INSCRIPTION TROUVE POUR CE PARTICIPANT !\n"+ Dsg.r);
            return;
        }

        double prix = service.calculerPrix(insc, insc.getTarif(), data.getPlages().size());
        System.out.printf(Dsg.re +"PRIX TOTAL POUR %s : %.2f€"+ Dsg.r, p, prix);
    }

    private Participant choisirParticipant() {
        boolean running = true;
        Participant participant = null;
        while (running) {
            if (data.getParticipants().isEmpty()) {
                System.out.print(Dsg.re +"❌ AUNCUN PARTICIPANT !"+ Dsg.r);
                return null;
            }

            System.out.println("Choisissez un participant :");
            System.out.println(Dsg.re + "Q. Quitter\n" + Dsg.r);
            for (int i = 0; i < data.getParticipants().size(); i++) {
                System.out.printf("%-3d %s\n",i+1,data.getParticipants().get(i));
            }

            System.out.print("\nChoix :");
            String input = scanner.nextLine();

            if(!input.equalsIgnoreCase("q")) {
                int num = Integer.parseInt(input);
                if (num < 1 || num > data.getParticipants().size()) {
                    System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+Dsg.r);
    //                return null;
                }else{
                    participant = data.getParticipants().get(num - 1);
                    running = false;
                }
            }else {
                running = false;
            }
        }
//        return data.getParticipants().get(num - 1);
        return participant;
    }

    private void afficherInscriptions() {
        System.out.println(Dsg.re +"\n=== Participants ==="+ Dsg.r);
        System.out.println(String.format("%-8s %-8s %-1s","Nom", "Prénom", "Club"));
        System.out.println("───────────────────────");
        for (Participant p : data.getParticipants()) {
            System.out.println(p.toString());
        }

        System.out.println(Dsg.re +"\n=== Inscriptions ==="+ Dsg.r);
        System.out.println(String.format("Nom%-5s Prénom%-2s Club%-4s Tarif%-7s 🍔Souper/🏦Logement%-5s 🥋Animateur%-4s " +
                "🕛 Plages%-1s","","", "", "", "","",""));
        System.out.println("──────────────────────────────────────────────────────────────────────────────────────" +
                "────────────────────────────────");
        for (Inscription i : data.getInscriptions()) {
            System.out.println(i.toString());
        }
    }

    private Plage choisirPlage() {
        Plage plage = null;

        if (data.getPlages().isEmpty()) {
            System.out.println("❌ AUCUNE PLAGE !");
            return plage;
        }else{
            System.out.println("Choisissez une plage :");
            for (int i = 0; i < data.getPlages().size(); i++) {
                System.out.printf("%-3d %s\n",i+1,data.getPlages().get(i));
            }

            System.out.print("\nChoix :");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")){
                return plage;
            }
            int num = Integer.parseInt(input);

            if (num < 1 || num > data.getPlages().size()) {
                System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+Dsg.r);
                return plage;
            }
            plage = data.getPlages().get(num - 1);
        }
        return plage;
    }
}