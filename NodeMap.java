import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francis on 30/05/2016.
 */
public class NodeMap {

    private final Map<Integer, Node> nodes = new HashMap<>();

    public Node get(int id) {
        return nodes.computeIfAbsent(id, Node::new);
    }

    private void addEdge(Node from, Node to) {
        from.addOutgoing(to);
        to.addIncoming(from);
    }

    public void addEdge(int from, int to) {
        addEdge(get(from), get(to));
    }

    public int size(){
        return nodes.size();
    }

    public Map<Integer, Node> getMap(){
        return nodes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        nodes.forEach((id, node) -> sb
                .append(node)
                .append("\n")
        );

        return sb.toString();
    }
}
