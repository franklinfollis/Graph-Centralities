import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import twitter4j.User;
import twitter4j.api.UsersResources;

/**
 * Created by Francis on 30/05/2016.
 */
public class main {

    public static void main(String[] args) {
        GraphCentrality gc = new GraphCentrality();
        UsersResources us;
        NodeMap nodes = new NodeMap();

        try (Scanner sc = new Scanner(new File("graph2.txt"))) {
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

            System.out.println("\t\t\t\tDEGREE CENTRALITY");

            for(GraphCentrality.NodeScore ns : degree){
                System.out.println(String.format("Node: " + ns.node.getID() + "\tScore: " + "%.7f" + "\tName: " + ns.name, ns.score));
            }

            System.out.println("\n\t\t\t\tCLOSENESS CENTRALITY");

            for(GraphCentrality.NodeScore ns : close){
                System.out.println(String.format("Node: " + ns.node.getID() + "\tDistance: " + "%.7f" + "\tName: " + ns.name, ns.score));
            }

            System.out.println("\n\t\t\t\tBETWEENNESS CENTRALITY");

            for(GraphCentrality.NodeScore ns : between) {
                System.out.println(String.format("Node: " + ns.node.getID() + "\tDistance: " + "%.7f" + "\tName: " + ns.name, ns.score));
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
