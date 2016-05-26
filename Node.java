import java.util.ArrayList;

public class Node {
		private final int id;
		private final ArrayList<Node> edges = new ArrayList<>();
		
		public Node(int id) {
			this.id = id;
		}
		
		public void add(Node n) {
			edges.add(n);
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