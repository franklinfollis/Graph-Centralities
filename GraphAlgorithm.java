import java.util.*;

/**
 * Created by Francis on 2/06/2016.
 */
public class GraphAlgorithm {

    public static class MuteFloat {
        public float value = 0.f;

        public MuteFloat(Node ignored) { }
    }

    public class BrandesData {
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

    public static float bfsSum(Node start) {
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

    public void brandesAlg(Node start, Map<Node, MuteFloat> b){
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

    public float bfsKatz(Node start){
        float alpha = 0.5f;

        Queue<Node> queue = new ArrayDeque<>();
        Map<Integer,Integer> distances = new HashMap<>();

        queue.add(start);
        distances.put(start.getID(), 0);

        float sum = 0;

        while (!queue.isEmpty()) {
            Node n = queue.remove();

            int dist = distances.get(n.getID());

            for (Node e : n.getEdges()) {
                if (!distances.containsKey(e.getID())) {
                    queue.add(e);
                    distances.put(e.getID(), dist+1);

                    sum += Math.pow(alpha, (dist+1));
                }
            }
        }

        return sum;

    }
}
