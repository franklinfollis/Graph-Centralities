import java.util.HashMap;
import java.util.Map;

public class NodeMap {
	
		private final Map<Integer, Node> nodes = new HashMap<>();
		
		public Node get(int id) {
			return nodes.computeIfAbsent(id, Node::new);
		}
		
		public void addEdge(int from, int to) {
			get(from).add(get(to));
		}
		
		public int size(){
			return nodes.size();
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			nodes.forEach((id, node) -> sb
				.append(node)
				.append("\n")
			);
			
			return sb.toString();
		}
	}