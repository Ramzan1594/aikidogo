package be.technifutur.Storage;

import be.technifutur.Model.StageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

//READ AND WRITE DES FILES
public class DataStorage {
//    public static void save(StageData data, String file){
//        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
//        {
//            oos.writeObject(data);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static StageData load(String file){
//        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
//        {
//            return (StageData)ois.readObject();
//        }catch (Exception e){
//            return new StageData();
//        }
//    }
    private static Gson json = new GsonBuilder()
            .setPrettyPrinting() // nice formatted JSON
            .create();

    public static void save(Object obj) {
        // Create a File object
        File file = new File("./mydata/"+obj.getClass().getSimpleName()+".json");

        // Optional: delete the file if it exists (just to be explicit)
        if (file.exists()) {
            file.delete();
        }
        // Write the new JSON
        try {
            FileWriter writer = new FileWriter(file);
            json.toJson(obj, writer);
            System.out.printf("\n%s saved to JSON in %s",obj.getClass().getSimpleName(),file.getPath());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StageData load(String filename){
        try (FileReader reader = new FileReader(filename)) {
            return json.fromJson(reader, StageData.class);
        } catch (IOException e) {
            System.out.println("File not found, returning new StageData");
            return new StageData();
        }
    }

}
