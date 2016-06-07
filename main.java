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


            long start = System.currentTimeMillis();
            ArrayList<GraphCentrality.NodeScore> degree = gc.degreeCentral(nodes);
            long finish = System.currentTimeMillis();
            System.out.println("DEGREE Time :" + (finish - start) + "ms\n");

            start = System.currentTimeMillis();
            ArrayList<GraphCentrality.NodeScore> close = gc.closenessCentral(nodes);
            finish = System.currentTimeMillis();
            System.out.println("CLOSENESS Time :" + (finish - start) + "ms\n");

            start = System.currentTimeMillis();
            ArrayList<GraphCentrality.NodeScore> between = gc.betweennessCentral(nodes);
            finish = System.currentTimeMillis();
            System.out.println("BETWEENNESS Time :" + (finish - start) + "ms\n");

            start = System.currentTimeMillis();
            ArrayList<GraphCentrality.NodeScore> katz = gc.katzCentral(nodes);
            finish = System.currentTimeMillis();
            System.out.println("KATZ Time :" + (finish - start) + "ms\n");


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
