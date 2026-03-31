package be.technifutur.Storage;

import be.technifutur.Design;
import be.technifutur.Model.StageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;

//READ AND WRITE DES FILES
public class DataStorage {
    private static Gson json = new GsonBuilder()
            .setPrettyPrinting() // nice formatted JSON
            .create();

    public static void save(Object obj) {
        Scanner scanner  = new Scanner(System.in);
        System.out.print("Entree le nom du ficher à enregistrée : ");
        String fileName = scanner.nextLine();

        // Create a File object
        File file = new File("./mydata/"+fileName+".json");

        // Optional: delete the file if it exists (just to be explicit)
        if (file.exists()) {
            file.delete();
        }
        // Write the new JSON
        try {
            FileWriter writer = new FileWriter(file);
            json.toJson(obj, writer);
            //System.out.printf("\n%s saved to JSON in %s\n",obj.getClass().getSimpleName(),file.getPath());
            System.out.printf(Design.ANSI_RED+"✅ DATA ENREGISTRER DANS %s"+Design.ANSI_RESET,file.getPath());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StageData load(){
//        try (FileReader reader = new FileReader(filename)) {
//            return json.fromJson(reader, StageData.class);
//        } catch (IOException e) {
//            System.out.print(Design.ANSI_RED+"❌ FICHIER NON TROUVE"+Design.ANSI_RESET);
//            return new StageData();
//        }
        Scanner  scanner  = new Scanner(System.in);
        StageData data = new StageData();
        File folder = new File("./mydata/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.print(Design.ANSI_RED+"❌ AUNCUN FICHIER TROUVE !"+Design.ANSI_RESET);
            data = new StageData();
//            return;
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
//            return;
        }

        if (choix < 1 || choix > files.length) {
            System.out.print("CHOIX INVALIDE !");
//            return;
        }

//        data = DataStorage.load(files[choix - 1].getPath());
        try (FileReader reader = new FileReader(files[choix - 1].getPath())) {
            data = json.fromJson(reader, StageData.class);
        } catch (IOException e) {
            System.out.print(Design.ANSI_RED+"❌ FICHIER NON TROUVE, UN NOUVEAU SERA CREE"+Design.ANSI_RESET);
        }

        System.out.printf(Design.ANSI_RED+"✅ FICHIER CHARGE : %s"+Design.ANSI_RESET , files[choix - 1].getName());

        return data;

    }

}
