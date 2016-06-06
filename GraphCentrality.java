import java.util.*;
import java.util.stream.Collectors;

import twitter4j.*;

/**
 * Created by Francis on 30/05/2016.
 */
public class GraphCentrality {

    public GraphAlgorithm ga = new GraphAlgorithm();

    public class NodeScore{
        public Node node;
        public String name;
        public String handle;
        public Float score;

        public NodeScore(Node n, Float s){
            node = n;
            score = s;
            name = "Joe Johnson";
            handle = "@lolcatz";
            /*try {
                Twitter twitter = new TwitterFactory().getInstance();
                User u = twitter.showUser((long)n.getID());
                name = u.getName();
                handle = u.getScreenName();
            } catch (TwitterException te){
                te.printStackTrace();
            }*/
        }
    }

    private static class SortedPair implements Comparable<SortedPair> {
        private final float distance;
        private final Node node;

        public SortedPair(float distance, Node node) {
            this.distance = distance;
            this.node = node;
        }

        @Override
        public int compareTo(SortedPair other) {
            return -Float.compare(distance, other.distance);
        }

        public Node getNode() {
            return node;
        }

        public float getDistance() {
            return distance;
        }
    }

    public ArrayList<NodeScore> degreeCentral(NodeMap nodes) {
        Queue<Node> Q = new PriorityQueue<>(nodes.size(), new Comparator<Node>() {

            @Override
            public int compare(Node n1, Node n2){
                return -(n1.totalEdges() - n2.totalEdges());
            }
        });

        float scoreDivisor = nodes.size();

        Q.addAll(nodes.getMap().values());

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 4; i++){
            Node n = Q.poll();
            result.add(i, new NodeScore(n, (float)n.totalEdges()));
        }

        return result;
    }

    public ArrayList<NodeScore> closenessCentral(NodeMap nodes) {
        Queue<SortedPair> q = nodes
                .getMap()
                .values()
                .stream()
                .map(n -> new SortedPair(ga.bfsSum(n), n))
                .collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 4; i++) {
            SortedPair p = q.poll();
            result.add(i, new NodeScore(p.getNode(), p.getDistance()));
        }
        return result;
    }

    public ArrayList<NodeScore> betweennessCentral(NodeMap nodes) {

        Map<Node, GraphAlgorithm.MuteFloat> betweens = new HashMap<>();

        nodes
                .getMap()
                .values()
                .stream()
                .forEach(n -> ga.brandesAlg(n, betweens));

        Queue<SortedPair> q = betweens
                .keySet()
                .stream()
                .map(n -> new SortedPair(betweens.getOrDefault(n, new GraphAlgorithm.MuteFloat(n)).value, n))
                .collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 4; i++){
            SortedPair n = q.poll();
            result.add(new NodeScore(n.node, n.distance));
        }

        return result;
    }

    public ArrayList<NodeScore> katzCentral(NodeMap nodes) {
        Queue<SortedPair> q = nodes
                .getMap()
                .values()
                .stream()
                .map(n -> new SortedPair(ga.bfsKatz(n), n))
                .collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 4; i++) {
            SortedPair p = q.poll();
            result.add(i, new NodeScore(p.getNode(), p.getDistance()));
        }
        return result;
    }
}

