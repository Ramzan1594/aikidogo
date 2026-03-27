package be.technifutur;

public class AppInfo {
    public static final int YEAR = 2026;
    public static final int MONTH = 3;
    public static final int DAY = 27;
    public static final int BUILD = 001;
    public static final String VERSION =
            String.format("%04d.%02d.%02d.%03d", YEAR, MONTH, DAY, BUILD);


    public static String appVersion()
    {
        return  "\n********  APP VERSION  ********\n"
                +"        "+VERSION+"            "
                +"\n*******************************\n";
    }
}
