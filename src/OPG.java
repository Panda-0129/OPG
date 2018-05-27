import java.io.*;
import java.util.Collections;

public class OPG {

    private static String VT[] = new String[64];
    private static String VN[] = new String[64];

    public static void main (String args[]) throws IOException {
        readFromFile();
        addVTandVN();
        FirstVT firstVT = new FirstVT();
        LastVT lastVT = new LastVT();
        Table table = new Table();
        OPGAnalyze opgAnalyze = new OPGAnalyze();
        for (String vn : VN) {
            System.out.println(vn);
            System.out.print("FIRSTVT: ");
            firstVT.getFirstVt(vn).outputSet();
            System.out.print("LASTVT: ");
            lastVT.getLastVt(vn).outputSet();
        }
        table.generateTable();
        table.outputTable();
        opgAnalyze.analyze();
    }

    private static void readFromFile() throws IOException {
        int i = 0;
        String line;
        String pathname = "test1.txt";
        File fp = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fp));
        BufferedReader br = new BufferedReader(reader);
        while ((line = br.readLine()) != null) {
            if(i == 0) {
                VN = line.split(",");
            } else if (i == 1) {
                VT = line.split(",");
            } else {
                Config.productions.add(new Production(line.replace(" ", "")));
            }
            i++;
        }
    }

    private static void addVTandVN() {
        Collections.addAll(Config.VN, VN);
        Collections.addAll(Config.VT, VT);
    }
}
