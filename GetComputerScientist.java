import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

/**
 * Created by Francis on 6/06/2016.
 */
public class GetComputerScientist {


    public class ComputerScientist {
        public String name;
        public String twitterHandle;

        public ComputerScientist(String n, String t) {
            name = n;
            twitterHandle = t;
        }
    }

    public ArrayList<ComputerScientist> getComputerScientists() {
        ArrayList<ComputerScientist> scientists = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("ComputerScientists.txt"));

            while (sc.hasNext()) {
                ComputerScientist cs = new ComputerScientist(sc.nextLine(), sc.nextLine());
                scientists.add(cs);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scientists;
    }
}
