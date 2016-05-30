import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by Francis on 30/05/2016.
 */
public class main {

    public static void main(String[] args) {
        GraphCentrality gc = new GraphCentrality();
        NodeMap nodes = new NodeMap();

        try (Scanner sc = new Scanner(new File("graph1.txt"))) {
            while(sc.hasNextInt()) {
                int from = sc.nextInt();

                if (!sc.hasNextInt()) throw new IllegalArgumentException("Invalid file format");

                int to = sc.nextInt();

                nodes.addEdge(from, to);
            }

            ArrayList<GraphCentrality.NodeScore> degree = gc.degreeCentral(nodes);
            ArrayList<GraphCentrality.NodeScore> close = gc.closenessCentral(nodes);
            ArrayList<GraphCentrality.NodeScore> between = gc.betweennessCentral(nodes);
            ArrayList<GraphCentrality.NodeScore> katz = gc.katzCentral(nodes);



        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
