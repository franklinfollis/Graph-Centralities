import java.util.*;
import java.io.*;

public class GraphCentrality {
	
	public static class Node {
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
	
	public static class NodeMap {
		private final Map<Integer, Node> nodes = new HashMap<>();
		
		public Node get(int id) {
			return nodes.computeIfAbsent(id, Node::new);
		}
		
		public void addEdge(int from, int to) {
			get(from).add(get(to));
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

	public static void main(String[] args) {
		NodeMap nodes = new NodeMap();
		
		try (Scanner sc = new Scanner(new File("graph1.txt"))) {
			while(sc.hasNextInt()) {
				int from = sc.nextInt();
				
				if (!sc.hasNextInt()) throw new IllegalArgumentException("Invalid file format");
				
				int to = sc.nextInt();
				
				nodes.addEdge(from, to);
			}
			
			System.out.println(nodes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
