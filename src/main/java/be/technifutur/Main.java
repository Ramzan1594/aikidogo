package be.technifutur;

import be.technifutur.Model.*;
import be.technifutur.Service.StageService;
import be.technifutur.Storage.DataStorage;
import be.technifutur.Ui.ConsoleUi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static void main() throws Exception {

        StageData stageAikido =  new StageData();
        StageService service = new StageService();

        ConsoleUi ui = new ConsoleUi(service, stageAikido);
        ui.start();


//        StageData stageAikido = DataStorage.load("dataAikidogo.ser");
       /* StageData stageAikido =  new StageData();

        Plage pl = new Plage("Samedi matin 1","Samedi", "9:00","10:30");
        Plage pl2 = new Plage("Samedi matin 2","Samedi", "10:30","12:00");
        Plage pl3 = new Plage("Samedi aprem 1","Samedi", "12:30","13:30");
        Plage pl4 = new Plage("Samedi aprem 2","Samedi", "13:30","15:00");
        Plage pl5 = new Plage("Samedi matin 3","Samedi", "15:00","17:30");
        Plage pl6 = new Plage("Dimanche matin 1","Dimanche", "9:00","10:30");
        Plage pl7 = new Plage("Dimanche matin 2","Dimanche", "10:30","12:00");
        Plage pl8 = new Plage("Dimanche aprem 2","Dimanche", "12:30","13:30");

        Participant p1 = new Participant
                (ETypeParticipant.NORMAL,"clubAA","emailtest","teltest"
                        ,"Portier","Jack");
        Participant p2 = new Participant
                (ETypeParticipant.NORMAL,"clubAA","emailtest","teltest"
                        ,"Portier22","Jack2");
        pl.setAnimateur(p1);

        //STAGE on prepare le stage en lui ajoutant les plage
        stageAikido.getPlages().add(pl);
        stageAikido.getPlages().add(pl2);
        stageAikido.getPlages().add(pl3);


        //ici on vient inscrire une personne et mettre les plages et souper/logement
        Inscription ins = new Inscription(p1);
        ins.getPlages().add(pl);
        ins.setSouper(true);

        //STAGE on ajoute les participant au stage pour avoir la liste des participant
        stageAikido.getParticipants().add(p1);
        //STAGE on ajoute les inscription
        stageAikido.getInscriptions().add(ins);
        //STAGE on set les tarifs par stage
        stageAikido.setTarif(new Tarif(10, 10, 5, 60));

        //il faut faire le calcule pour chaque inscription
        StageService service = new StageService();
        double prix = service.calculerPrix(ins, stageAikido.getTarif(), 8);

        System.out.println(stageAikido.toString(ins,prix));


        DataStorage storage = new DataStorage();
        storage.save(stageAikido);


        System.out.println(AppInfo.appVersion());*/



    }
}
