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
        if(args.length == 2 && args[1].equals("twitter")) gc.twit = true;
        NodeMap nodes = new NodeMap();

        try (Scanner sc = new Scanner(new File(args[0]))) {
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


            System.out.printf("%60s\n","DEGREE CENTRALITY");
            degree.forEach(ns -> System.out.printf("Node: %-20s Score: %-20.7f Name: %-20s Username: @%-20s\n",ns.node.getID(), ns.score , ns.name, ns.handle));

            System.out.printf("\n%60s\n","CLOSENESS CENTRALITY");
            close.forEach(ns -> System.out.printf("Node: %-20s Score: %-20.7f Name: %-20s Username: @%-20s\n",ns.node.getID(), ns.score , ns.name, ns.handle));

            System.out.printf("\n%60s\n","BETWEENNESS CENTRALITY");
            between.forEach(ns -> System.out.printf("Node: %-20s Score: %-20.7f Name: %-20s Username: @%-20s\n",ns.node.getID(), ns.score , ns.name, ns.handle));

            System.out.printf("\n%60s\n","KATZ CENTRALITY");
            katz.forEach(ns -> System.out.printf("Node: %-20s Score: %-20.7f Name: %-20s Username: @%-20s\n",ns.node.getID(), ns.score , ns.name, ns.handle));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
