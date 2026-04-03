package be.technifutur;

import be.technifutur.Model.*;
import be.technifutur.Service.StageService;
import be.technifutur.Ui.ConsoleUi;

public class Main {
    public static void main() {
        AppInfo appInfo = new AppInfo();
        appInfo.getCurrentVersion();
        System.out.println(Tableau.displayInbox(Dsg.re,AppInfo.staticVersion));

        StageData stageAikido =  new StageData();
        StageService service = new StageService();
        ConsoleUi ui = new ConsoleUi(service, stageAikido);
        ui.start();

        System.out.println(Tableau.displayInbox(Dsg.re,AppInfo.staticVersion));
    }
}
