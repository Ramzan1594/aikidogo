package be.technifutur.Storage;

import be.technifutur.Dsg;
import be.technifutur.Model.StageData;
import be.technifutur.Model.Tabeau;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

//READ AND WRITE DES FILES
public class DataStorage {
    private static StringBuilder sb;
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
            System.out.printf(Dsg.re +"✅ DATA ENREGISTRER DANS %s"+ Dsg.r,file.getPath());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StageData load(){
        sb = new StringBuilder();
        boolean running = true;
        Scanner  scanner  = new Scanner(System.in);
        StageData data = new StageData();
        File folder = new File("./mydata/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.print(Dsg.re +"❌ AUNCUN FICHIER TROUVE !"+ Dsg.r);
            data = new StageData();
        }

        while(running)
        {
            String title = "=== Choisissez un fichier ===*";
            sb.append("=== Choisissez un fichier ===*");
            sb.append("0. Quitter*");
            sb.append("1. Nouveau fichier*");
            for (int i = 0; i < files.length; i++) {
                sb.append((i + 2) + ". " + files[i].getName()+"*");
            }
            System.out.print(Tabeau.displayInbox("",sb));
            sb.setLength(0);

            System.out.print("Choix : ");
            int choix = 0;
            String input = scanner.nextLine();
            //test si l entree est un digit
            if (input.matches("\\d+")) {
                choix = Integer.parseInt(input);
            } else {
    //            System.out.println(Dsg.re+Dsg.bo+"⛔⛔⛔ LE CHOIX DOIT ETRE UN NUMERO ⛔⛔⛔"+Dsg.r);
                sb.append(" LE CHOIX DOIT ETRE UN NUMERO "+"*");
            }

            if (choix == 1) {
                data = new StageData();
                System.out.print(Dsg.re +"✅ NOUVEAU STAGE CREE.\n"+ Dsg.r);
                running = false;
            }else if(choix == 0)
            {
                running = false;
            }else{
                if (choix < 1 || choix > files.length) {
                    sb.append(" CHOIX INVALIDE! "+"*");
                    System.out.print(Tabeau.displayInbox(Dsg.re+Dsg.bo,sb));
                }else{
                    try (FileReader reader = new FileReader(files[choix - 2].getPath())) {
                        data = json.fromJson(reader, StageData.class);
                    } catch (IOException e) {
                        sb.append(" FICHIER NON TROUVE, UN NOUVEAU SERA CREE "+"*");
                        System.out.println(Tabeau.displayInbox(Dsg.re+Dsg.bo,sb));
                    }

                    System.out.printf(Dsg.re +"✅ FICHIER CHARGE : %s"+ Dsg.r, files[choix - 2].getName());
                    running = false;
                }
            }
        }
        return data;
    }

}
