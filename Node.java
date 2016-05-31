import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Francis on 30/05/2016.
 */
public class Node {
    private final int id;
    private final Set<Node> edges = new HashSet<>();

    public Node(int id) {
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public void addEdge(Node n) {
        edges.add(n);
    }

    public Set<Node> getEdges() {
        return edges;
    }

    public int totalEdges() {
        return edges.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append(" -> (");

        String prefix = "";

        for (Node n : edges) {
            sb
                    .append(prefix)
                    .append(n.id);

            prefix = ", ";
        }

        sb.append(")");

        return sb.toString();
    }
}