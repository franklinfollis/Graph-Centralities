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
        public String handle;
        public Float score;

        public NodeScore(Node n, Float s){
            node = n;
            score = s;

            try {
                Twitter twitter = new TwitterFactory().getInstance();
                User u = twitter.showUser((long)n.getID());
                name = u.getName();
                handle = u.getScreenName();
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

            for (Node e : n.getEdges()) {
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
        Queue<SortedPair> q = nodes
            .getMap()
            .values()
            .stream()
            .map(n -> new SortedPair(bfsSum(n), n))
            .collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 5; i++) {
            SortedPair p = q.poll();
            result.add(i, new NodeScore(p.getNode(), p.getDistance()));
        }
        return result;
    }

    private static class BrandesData {
        ArrayList<Node> pred = new ArrayList<>();
        int distance;

        float sigma;
        float dependency = 0;

        private BrandesData(int distance, float sigma) {
            this.distance = distance;
            this.sigma = sigma;
        }

        private BrandesData(Node ignored) {
            this(-1,0);
        }
    }

    private static class MuteFloat {
        private float value = 0.f;

        private MuteFloat(Node ignored) { }
    }

    private static void brandesAlg(Node start, Map<Node, MuteFloat> b){
        Queue<Node> queue = new ArrayDeque<>();
        Stack<Node> s = new Stack<>();
        Map<Node, BrandesData> nodeData = new IdentityHashMap<>();

        queue.add(start);

        nodeData.put(start, new BrandesData(0, 1));

        while (!queue.isEmpty()) {
            Node n = queue.remove();
            BrandesData nData = nodeData.computeIfAbsent(n, BrandesData::new);

            s.push(n);

            for (Node v : n.getEdges()) {
                BrandesData vData = nodeData.computeIfAbsent(v, BrandesData::new);

                if (vData.distance < 0) {
                    queue.add(v);
                    vData.distance = nData.distance + 1;
                }

                if (vData.distance == nData.distance + 1) {
                    vData.sigma += nData.sigma;
                    vData.pred.add(n);
                }
            }
        }

        while(!s.isEmpty()){
            Node w = s.pop();
            BrandesData wData = nodeData.get(w);

            for(Node v : wData.pred){
                BrandesData vData = nodeData.get(v);

                vData.dependency += (vData.sigma / wData.sigma) * (1 + wData.dependency);
            }

            if(w != start) b.computeIfAbsent(w, MuteFloat::new).value += wData.dependency;
        }
    }

    public ArrayList<NodeScore> betweennessCentral(NodeMap nodes) {

        Map<Node, MuteFloat> betweens = new HashMap<>();

        nodes
                .getMap()
                .values()
                .stream()
                .forEach(n -> brandesAlg(n, betweens));

        Queue<SortedPair> q = betweens
                .keySet()
                .stream()
                .map(n -> new SortedPair(betweens.getOrDefault(n, new MuteFloat(n)).value, n))
                .collect(Collectors.toCollection(PriorityQueue::new));

        ArrayList<NodeScore> result = new ArrayList<>(5);

        for(int i = 0; i < 5; i++){
            SortedPair n = q.poll();
            result.add(new NodeScore(n.node, n.distance));
        }

        return result;
    }

    public ArrayList<NodeScore> katzCentral(NodeMap nodes) {
        return null;
    }
}

