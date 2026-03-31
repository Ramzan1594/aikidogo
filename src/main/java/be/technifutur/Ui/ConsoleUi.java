package be.technifutur.Ui;

import be.technifutur.Design;
import be.technifutur.Model.*;
import be.technifutur.Service.StageService;
import be.technifutur.Storage.DataStorage;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ConsoleUi {

    private StageService service;
    private StageData data;
    private Scanner scanner;

    public ConsoleUi(StageService service, StageData data) {
        this.service = service;
        this.data = data;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println(Design.ANSI_PURPLE+"\n========================================\n" +
                                                  "============ MENU AIKIDOGO ============="+Design.ANSI_RESET);
            System.out.println(Design.ANSI_RED+"0. Quitter"+Design.ANSI_RESET);
            System.out.println(Design.ANSI_GREEN+"1. Charger fichier");
            System.out.println("2. ENREGISTRE LES DONNEES"+Design.ANSI_RESET);

            System.out.println("----------------------------------------\n3. Définir les tarifs");
            System.out.println("4. Ajouter un participant");
            System.out.println("5. Créer une plage");
            System.out.println("6. Inscrire un participant à une plage");
            System.out.println("7. Affecter un animateur à une plage");
            System.out.println("8. Afficher participants et inscriptions");
            System.out.println("9. Calculer le prix d'un participant");
            System.out.println("10.Voir les tarifs");
            System.out.println(Design.ANSI_PURPLE+"========================================"+Design.ANSI_RESET);


            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // vider le buffer

            switch (choix) {
                case 1 -> loadFile();
                case 2 -> saveStageData();
                case 0 -> running = false;

                case 3 -> definirTarif();
                case 4 -> ajouterParticipant();
                case 5 -> creerPlage();
                case 6 -> inscrireParticipant();
                case 7 -> affecterAnimateur();
                case 8 -> afficherInscriptions();
                case 9 -> calculerPrix();
                case 10 -> afficherTarif();
                default -> System.out.println(Design.ANSI_RED+"❌ CHOIX INVALIDE !"+Design.ANSI_RESET);
            }

        }
    }

    private void loadFile() {
        File folder = new File("./mydata/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.print(Design.ANSI_RED+"❌ AUNCUN FICHIER TROUVE !"+Design.ANSI_RESET);
            data = new StageData();
            return;
        }

        System.out.println("=== Choisissez un fichier ===");
        System.out.println("0. Nouveau fichier");

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix == 0) {
            data = new StageData();
            System.out.print(Design.ANSI_RED+"✅ NOUVEAU STAGE CREE."+Design.ANSI_RESET);
            return;
        }

        if (choix < 1 || choix > files.length) {
            System.out.print("CHOIX INVALIDE !");
            return;
        }

        data = DataStorage.load(files[choix - 1].getPath());

        System.out.printf(Design.ANSI_RED+"✅ FICHIER CHARGE : %s"+Design.ANSI_RESET , files[choix - 1].getName());
    }

    private void saveStageData() {
        DataStorage storage = new DataStorage();
        storage.save(data);
    }
    private void definirTarif() {

        System.out.println("=== Définition des tarifs ===");

        System.out.print("Prix par plage : ");
        double prixPlage = scanner.nextDouble();

        System.out.print("Prix souper : ");
        double prixSouper = scanner.nextDouble();

        System.out.print("Prix logement : ");
        double prixLogement = scanner.nextDouble();

        System.out.print("Prix full (toutes les plages) : ");
        double prixFull = scanner.nextDouble();

        scanner.nextLine(); // vider buffer

        Tarif tarif = new Tarif(prixPlage, prixSouper, prixLogement, prixFull);
        data.setTarif(tarif);

        System.out.print(Design.ANSI_RED+"✅ TARIFS ENREGISTREES !"+Design.ANSI_RESET);
    }

    private void afficherTarif() {
        if (data.getTarif() == null) {
            System.out.print(Design.ANSI_RED+"AUNCUN TARIF DEFINI."+Design.ANSI_RESET);
        } else {
            System.out.print(data.getTarif().toString());
        }
    }


    private void ajouterParticipant() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Tel : ");
        String tel = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Club : ");
        String club = scanner.nextLine();
        System.out.print("Type (MINEUR, NORMAL, ANIMATEUR) : ");
        ETypeParticipant type = ETypeParticipant.valueOf(scanner.nextLine().toUpperCase());

        Participant p = new Participant(type,club, email, tel,prenom, nom);
        data.getParticipants().add(p);
        System.out.printf(Design.ANSI_RED+"✅ PARTICIPANT AJOUTE : %s"+Design.ANSI_RESET, p.toString());
    }

    private void creerPlage() {
        System.out.print("Nom de la plage : ");
        String nom = scanner.nextLine();
        System.out.print("Jour : ");
        String jour = scanner.nextLine();
        System.out.print("Heure début : ");
        String debut = scanner.nextLine();
        System.out.print("Heure fin : ");
        String fin = scanner.nextLine();

        Plage p = new Plage(nom, jour, debut, fin);
        data.getPlages().add(p);
        System.out.printf(Design.ANSI_RED+"✅ PLAGE CREE : %s"+Design.ANSI_RESET, p.toString());
    }

    private void inscrireParticipant() {
        Participant p = choisirParticipant();
        if (p == null) return;

        Inscription insc = new Inscription(p);

        System.out.println("Choisissez les plages :");
        List<Plage> plages = data.getPlages();
        for (int i = 0; i < plages.size(); i++) {
            System.out.println((i + 1) + ". " + plages.get(i));
        }

        while (true) {
            System.out.print("Numéro plage (0 pour terminer): ");
            int num = scanner.nextInt();
            scanner.nextLine();
            if (num == 0) break;

            if (num >= 1 && num <= plages.size()) {
                try {
                    insc.addPlage(plages.get(num - 1));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.print(Design.ANSI_RED+"❌ NUMERO INVALIDE !"+Design.ANSI_RESET);
            }
        }

        System.out.print("Souper ? (1.oui/2.non) : ");
        boolean bool = scanner.nextLine().equalsIgnoreCase("1") ? true : false;
        insc.setSouper(bool);
        System.out.print("Logement ? (1.oui/2.non) : ");
        bool = scanner.nextLine().equalsIgnoreCase("1") ? true : false;
        insc.setLogement(bool);


        data.getInscriptions().add(insc);
        System.out.print(Design.ANSI_RED+"✅ INSCRIPTION AJOUTEE !"+Design.ANSI_RESET);
    }

    private void affecterAnimateur() {
        Plage p = choisirPlage();
        if (p == null) return;
        Participant animateur = choisirParticipant();
        if (animateur == null) return;
        p.setAnimateur(animateur);
        System.out.printf(Design.ANSI_RED+"✅ ANIMATEUR %s AFFECTE à %s"+Design.ANSI_RESET ,animateur.toString() , p.getNom());
    }

    private void afficherInscriptions() {
        System.out.println(Design.ANSI_RED+"\n=== Participants ==="+Design.ANSI_RESET);
        for (Participant p : data.getParticipants()) {
            System.out.println("- " + p.toString());
        }

        System.out.println(Design.ANSI_RED+"\n=== Inscriptions ==="+Design.ANSI_RESET);
        for (Inscription i : data.getInscriptions()) {
            System.out.println(i.toString());
        }
    }

    private void calculerPrix() {
        Participant p = choisirParticipant();
        if (p == null) return;

        Inscription insc = data.getInscriptions().stream()
                .filter(i -> i.getParticipant().equals(p))
                .findFirst()
                .orElse(null);

        if (insc == null) {
            System.out.print(Design.ANSI_RED+"❌ AUNCUNE INSCRIPTION TROUVE POUR CE PARTICIPANT !\n"+Design.ANSI_RESET);
            return;
        }

        double prix = service.calculerPrix(insc, data.getTarif(), data.getPlages().size());
        System.out.printf(Design.ANSI_RED+"PRIX TOTAL POUR %s : %.2f"+Design.ANSI_RESET , p.toString() , prix);
    }

    private Participant choisirParticipant() {
        List<Participant> participants = data.getParticipants();
        if (participants.isEmpty()) {
            System.out.print(Design.ANSI_RED+"AUNCUN PARTICIPANT !"+Design.ANSI_RESET);
            return null;
        }

        System.out.println("Choisissez un participant :");
        for (int i = 0; i < participants.size(); i++) {
            System.out.println((i + 1) + ". " + participants.get(i));
        }

        System.out.print("Numéro : ");
        int num = scanner.nextInt();
        scanner.nextLine();
        if (num < 1 || num > participants.size()) {
            System.out.print("❌ NUMERO INVALIDE !");
            return null;
        }
        return participants.get(num - 1);
    }

    private Plage choisirPlage() {
        List<Plage> plages = data.getPlages();
        if (plages.isEmpty()) {
            System.out.println("AUCUNE PLAGE !");
            return null;
        }

        System.out.println("Choisissez une plage :");
        for (int i = 0; i < plages.size(); i++) {
            System.out.println((i + 1) + ". " + plages.get(i));
        }

        System.out.print("Numéro : ");
        int num = scanner.nextInt();
        scanner.nextLine();
        if (num < 1 || num > plages.size()) {
            System.out.print("❌ NUMERO INVALIDE !");
            return null;
        }
        return plages.get(num - 1);
    }
}