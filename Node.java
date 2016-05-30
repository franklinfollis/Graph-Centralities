import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francis on 30/05/2016.
 */
public class Node {
    private final int id;
    private final ArrayList<Node> outgoing = new ArrayList<>();
    private final ArrayList<Node> incoming = new ArrayList<>();

    public Node(int id) {
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public void addOutgoing(Node n) {
        outgoing.add(n);
    }

    public void addIncoming(Node n) {
        incoming.add(n);
    }

    public List<Node> getOutgoing() {
        return outgoing;
    }

    public List<Node> getIncoming() {
        return incoming;
    }

    public int totalEdges() {
        return outgoing.size() + incoming.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append(" -> (");

        String prefix = "";

        for (Node n : outgoing) {
            sb
                    .append(prefix)
                    .append(n.id);

            prefix = ", ";
        }

        sb.append(")");

        return sb.toString();
    }
}