import java.util.*;
import java.io.*;


public class GraphCentrality {
	
	public static void degreeCentral(NodeMap nodes) {
		Queue<Node> Q = new PriorityQueue<>(nodes.size(), new Comparator<Node>() {
			
			@Override
			public int compare(Node n1, Node n2){
				return -(n1.totalEdges() - n2.totalEdges());
			}
		});
		
		float scoreDivisor = nodes.size();
		
		for (Node node : nodes.getMap().values()) Q.add(node);
		
		System.out.println("\tDEGREE CENTRALITY");
		
		for(int i = 0; i < 5; i++){
			Node n = Q.poll();
			System.out.println("Node: " + n.getID() + "\t Score: " + (n.totalEdges()/scoreDivisor));
		}
	}
	
	private static float bfs(Node start) {
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
	
	public static void closenessCentral(NodeMap nodes) {
		Queue<SortedPair> q = new PriorityQueue<>();

		for (Node n : nodes.getMap().values()) {
			q.add(new SortedPair(bfs(n), n));
		}
		
		System.out.println("\tCLOSENESS CENTRALITY");
		
		for(int i = 0; i < 5; i++) {
			SortedPair p = q.poll();
			System.out.println("Node: " + p.getNode().getID() + "\t Distance: " + (p.getDistance()));
		}
	}
	
/*	public void betweennessCentral(NodeMap nodes) {
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
	*/
	public void katzCentral(NodeMap nodes) {
		
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
			
			closenessCentral(nodes);
			System.out.println();
			degreeCentral(nodes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
