package be.technifutur.Ui;

import be.technifutur.AppInfo;
import be.technifutur.Dsg;
import be.technifutur.Model.*;
import be.technifutur.Service.StageService;
import be.technifutur.Storage.DataStorage;

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
            System.out.println(Dsg.pu +"\n┌────────────────────────────────────────┐\n" +
                                         "│============  MENU AIKIDOGO  ===========│"+ Dsg.r);
            System.out.println(Dsg.pu+"├────────────────────────────────────────┤"+Dsg.r);
            System.out.println(Dsg.pu+"│"+ Dsg.re +"0. Quitter"+ Dsg.pu +"                              │");
            System.out.println("│"+ Dsg.gr +"1. Charger fichier"+ Dsg.pu+"                      │");
            System.out.println("│"+ Dsg.gr +"2. ENREGISTRE LES DONNEES"+ Dsg.pu +"               │");
            System.out.println("├────────────────────────────────────────┤");

            System.out.println("│"+Dsg.wh+"3. Définir les tarifs"+ Dsg.pu+"                   │");
            System.out.println("│"+Dsg.wh+"4. Ajouter un participant"+ Dsg.pu+"               │");
            System.out.println("│"+ Dsg.wh +"5. Créer une plage"+ Dsg.pu+"                      │");
            System.out.println("│"+ Dsg.wh +"6. Inscrire un participant à une plage"+ Dsg.pu+"  │");
            System.out.println("│"+ Dsg.wh +"7. Affecter un animateur à une plage"+ Dsg.pu+"    │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println("│"+ Dsg.wh +"c. Calculer le prix d'un participant"+ Dsg.pu+"    │");
            System.out.println("│"+ Dsg.wh +"a. Afficher participants et inscriptions"+ Dsg.pu+"│");
            System.out.println("│"+ Dsg.wh +"r. Voir les tarifs"+ Dsg.pu+"                      │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println(Dsg.pu+"│"+ Dsg.wh +"f. Menu modification"+ Dsg.pu +"                    │");
            System.out.println(Dsg.pu +"└────────────────────────────────────────┘"+ Dsg.r);

            System.out.print("Choix : ");
            String choix = scanner.nextLine().toLowerCase();

            switch (choix) {
                case "0" -> running = false;
                case "1" -> loadFile();
                case "2" -> saveStageData();

                case "3" -> definirTarif();
                case "4" -> ajouterParticipant();
                case "5" -> creerPlage();
                case "6" -> inscrireParticipant();
                case "7" -> affecterAnimateur();

                case "c" -> calculerPrix();
                case "a" -> afficherInscriptions();
                case "r" -> afficherTarif();

                case "f" -> service.menuEdit(data);
                default -> System.out.println(Dsg.re+Dsg.bo+"❌ CHOIX INVALIDE !"+ Dsg.r);
            }
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

        System.out.print(Dsg.re +"✅ TARIFS ENREGISTREES !"+ Dsg.r);
    }

    private void afficherTarif() {
        if (data.getTarif() == null) {
            System.out.print(Dsg.re +"AUNCUN TARIF DEFINI."+ Dsg.r);
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
        System.out.printf(Dsg.re +"✅ PARTICIPANT AJOUTE : %s"+ Dsg.r, p.toString());
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
        System.out.printf(Dsg.re +"✅ PLAGE CREE : %s"+ Dsg.r, p.toString());
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
                System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+ Dsg.r);
            }
        }

        System.out.print("Souper ? (1.oui/2.non) : ");
        boolean bool = scanner.nextLine().equalsIgnoreCase("1") ? true : false;
        insc.setSouper(bool);
        System.out.print("Logement ? (1.oui/2.non) : ");
        bool = scanner.nextLine().equalsIgnoreCase("1") ? true : false;
        insc.setLogement(bool);


        data.getInscriptions().add(insc);
        System.out.print(Dsg.re +"✅ INSCRIPTION AJOUTEE !"+ Dsg.r);
    }

    private void affecterAnimateur() {
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

        double prix = service.calculerPrix(insc, data.getTarif(), data.getPlages().size());
        System.out.printf(Dsg.re +"PRIX TOTAL POUR %s : %.2f"+ Dsg.r, p.toString() , prix);
    }

    private Participant choisirParticipant() {
        List<Participant> participants = data.getParticipants();
        if (participants.isEmpty()) {
            System.out.print(Dsg.re +"❌ AUNCUN PARTICIPANT !"+ Dsg.r);
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
            System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+Dsg.r);
            return null;
        }
        return participants.get(num - 1);
    }

    private void afficherInscriptions() {
        System.out.println(Dsg.re +"\n=== Participants ==="+ Dsg.r);
        System.out.println(String.format("%-14s %-15s %-15s %-10s","Nom", "Prénom", "Club", "Type"));
        System.out.println("──────────────────────────────────────────────────────────");
        for (Participant p : data.getParticipants()) {
            System.out.println(p.toString());
        }

        System.out.println(Dsg.re +"\n=== Inscriptions ==="+ Dsg.r);
        System.out.println(String.format("%-15s %-15s %-15s %-13s %-33s %-45s %-10s","Nom", "Prénom", "Club", "Type", "🍔Souper/🏦Logement","🕛 Plages","🥋Animateur"));
        System.out.println("──────────────────────────────────────────────────────────────────────────────────────" +
                "───────────────────────────────────────────────────────────────────────");
        for (Inscription i : data.getInscriptions()) {
            System.out.println(i.toString());
        }
    }

    private Plage choisirPlage() {
        List<Plage> plages = data.getPlages();
        if (plages.isEmpty()) {
            System.out.println("❌ AUCUNE PLAGE !");
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
            System.out.print(Dsg.re+Dsg.bo+"❌ NUMERO INVALIDE !"+Dsg.r);
            return null;
        }
        return plages.get(num - 1);
    }
    public void test(){
        System.out.println("┌──────┬──────┐");
        System.out.println("│      │      │");
        System.out.println("├──────┼──────┤");
        System.out.println("│      │      │");
        System.out.println("│      │      │");
        System.out.println("└──────┴──────┘");
    }
}