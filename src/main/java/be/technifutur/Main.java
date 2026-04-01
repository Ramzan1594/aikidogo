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
    public static void main() {
        AppInfo appInfo = new AppInfo();
        appInfo.getCurrentVersion();
        System.out.println(Tabeau.displayInbox(Dsg.re,AppInfo.staticVersion));

        StageData stageAikido =  new StageData();
        StageService service = new StageService();
        ConsoleUi ui = new ConsoleUi(service, stageAikido);
        ui.start();

        System.out.println(Tabeau.displayInbox(Dsg.re,AppInfo.staticVersion));
    }
}
