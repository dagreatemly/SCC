import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Graph {
	final int V;
	private int E;
	List<Integer>[] adj;
	
	public Graph(int V) {
		if (V<0) throw new IllegalArgumentException("V positive");
		this.V = V;
		this.E = 0;
		adj = (List<Integer>[]) new List[V + 1];
		for (int v = 0; v<=V; v++) {
			adj[v] = new ArrayList<Integer>();
		}
	}

	public Graph(String filename) {
		this.V = V(filename);
		adj = (List<Integer>[]) new List[V + 1];
		for (int v=0; v<=V; v++) {
			adj[v] = new ArrayList<Integer>();
		}
		E = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = reader.readLine();
			while(line != null) {
				int v = 0, w = 0;
				try (Scanner scanner = new Scanner(line)) {
					if(scanner.hasNextInt()) v = scanner.nextInt();
					if(scanner.hasNextInt()) w = scanner.nextInt();
					addEdge(v, w);
				}
				line = reader.readLine();
			}
		} catch(FileNotFoundException e) {
			e.getMessage();
		} catch(IOException e) {
			e.getMessage();
		}
	}
				
	public int E(String filename) {
		int edges = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while((line=reader.readLine())!=null) {
				edges++;
			}
		} catch(FileNotFoundException e) {
			e.getMessage();
		} catch(IOException e) {
			e.getMessage();
		}
		return edges;
	}
	
	public int V() {
		return V;
	}
	
	public static int V(String filename) {
		int v = 0;
		try (Scanner s = new Scanner(new File(filename))) { 
			while(s.hasNextInt()){
				int next = s.nextInt();
				if(v < next) v = next;
			}
		} catch(FileNotFoundException e) {
			e.getMessage();
		}
		return v;
	}

	void validateVertex(int v) {
		if (v < 1 || v > V) 
			throw new IndexOutOfBoundsException();
	}
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		adj[v].add(w);
		E++;
	}
	
	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		return adj[v];
	}
	
	public int outdegree(int v) {
		validateVertex(v);
		return adj[v].size();
	}
	
	public Graph reverse() {
		Graph R = new Graph(V);
		for(int v=1; v<=V; v++) {
			for(int w : adj(v)) {
				R.addEdge(w, v);
			}
		}
		return R;
	}
	
	public List<Integer>[] adjContainer() {
		return adj;
	}
	
	public static void main(String[] args) {
		
		Graph G = new Graph("SCC.txt");
		Graph R = G.reverse();
		DFS G1 = new DFS();
		int[] F = G1.DFSLoop1(R);

		DFS G2 = new DFS();
		int[] leaders = G2.DFSLoop2(G, F);
		
		ArrayList<Integer> L = new ArrayList<>();
		for(int i = 0; i < leaders.length; i++) {
			L.add(leaders[i]);
		}
		
		Hashtable<Integer, Integer> frequencyHash = new Hashtable<Integer, Integer>();
		ArrayList<Integer> uniqueList = new ArrayList<>();
		
		for(int i = 0; i<L.size(); i++) {
			if (uniqueList.contains(L.get(i))) {
				int elementCount = frequencyHash.get(L.get(i));
				elementCount++;
				frequencyHash.put(L.get(i), elementCount);
			} else {
				uniqueList.add(L.get(i));
				frequencyHash.put(L.get(i), 1);
			}
		}
		Collection<Integer> values = frequencyHash.values();
		
		ArrayList<Integer> V = new ArrayList<>();
		V.addAll(values);
		Collections.sort(V);
		System.out.println("Done");//breakpoint here,debug view, see last five entries of V
//		
	}
}