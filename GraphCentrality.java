import java.util.*;
import java.io.*;


public class GraphCentrality {
	
	public static void degreeCentral(NodeMap nodes){
		Queue<Node> Q = new PriorityQueue<>(nodes.size(), new Comparator<Node>() {
			
			@Override
			public int compare(Node n1, Node n2){
				return n2.totalEdges() - n1.totalEdges();
			}
		});
		
		float scoreDivisor = nodes.size();
		
		for (Node node : nodes.getMap().values()) Q.add(node);
		
		System.out.println("\t DEGREE CENTRALITY");
		
		for(int i = 0; i < 4; i++){
			Node n = Q.poll();
			System.out.println("Node: " + n.getID() + "\t Score: " + (n.totalEdges()/scoreDivisor));
		}
		
	}
	
	public void closenessCentral(NodeMap nodes){
		
	}
	
	public void betweennessCentral(NodeMap nodes){
		
	}
	
	public void katzCentral(NodeMap nodes){
		
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
			
			degreeCentral(nodes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
