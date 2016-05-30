import java.util.*;
import java.util.stream.Collectors;

import twitter4j.*;

/**
 * Created by Francis on 30/05/2016.
 */
public class GraphCentrality {

    public class NodeScore{
        public Node node;
        public String name;
        public Float score;

        public NodeScore(Node n, Float s){
            node = n;
            score = s;

            try {
                Twitter twitter = new TwitterFactory().getInstance();
                name = twitter.showUser((long)n.getID()).getName();
            } catch (TwitterException te){
                te.printStackTrace();
            }
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

        for(int i = 0; i < 5; i++){
            Node n = Q.poll();
            result.add(i, new NodeScore(n, n.totalEdges()/scoreDivisor));
        }

        return result;
    }

    private static float bfsSum(Node start) {
        Queue<Node> queue = new ArrayDeque<>();
        Map<Integer,Integer> distances = new HashMap<>();

        queue.add(start);
        distances.put(start.getID(), 0);

        int sum = 0;

        while (!queue.isEmpty()) {
            Node n = queue.remove();

            int dist = distances.get(n.getID());

            for (Node e : n.getIncoming()) {
                if (!distances.containsKey(e.getID())) {
                    queue.add(e);
                    distances.put(e.getID(), dist+1);

                    sum += 1.f / (dist + 1);
                }
            }
        }

        return sum;
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

    public ArrayList<NodeScore> closenessCentral(NodeMap nodes) {
        Queue<SortedPair> q = nodes.getMap().values().stream().map(n -> new SortedPair(bfsSum(n), n)).collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 5; i++) {
            SortedPair p = q.poll();
            result.add(i, new NodeScore(p.getNode(), p.getDistance()));
        }
        return result;
    }

    public ArrayList<NodeScore> betweennessCentral(NodeMap start) {

        return null;
    }

    public ArrayList<NodeScore> katzCentral(NodeMap nodes) {
        return null;
    }
}

