import java.util.*;
import java.io.*;

public class GraphCentrality {
	
	public void degreeCentral(NodeMap nodes){
		
		
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
