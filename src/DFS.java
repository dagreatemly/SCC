public class DFS {
	private Integer t;
	private Integer s;
	boolean[] marked;
	int[] leader;
	int[] F;
	
	public DFS() {
		t = 0;
		s = null;
	}
	
	public int[] DFSLoop1(Graph G) {
		int v = G.V;
		
		marked = new boolean[v + 1];
		leader = new int[v + 1];
		F = new int[v + 1];
		for( int i = v; i >= 1 ; i--) {
			if(!marked[i]) {
				s = i;
				dfs(G, i);
			}
		}
		return F;
	}
	
	public int[] DFSLoop2(Graph G, int[] F) {
		int v = G.V;
		this.F = new int[v + 1];
		marked = new boolean[v + 1];
		leader = new int[v + 1];
		int index = F.length - 1;
		
		for(int i = index; i > 0; i--) {
			if(!marked[F[i]]) {
				s = F[i];
				dfs(G, F[i]);
			}
		}
		
		return leader;
	}
	
	public void dfs(Graph G, int i) {
		G.validateVertex(i);
		marked[i] = true;
		leader[i] = s;
		
		for (int j : G.adj[i]) {
			if(!marked[j]) {
				dfs(G,j);
			}
		}
		t++;
		G.validateVertex(t);
		this.F[t] = i;
	}
}
