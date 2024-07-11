//package Prova;

public class ZDriver {
	
	private ZDriver() {}
	
	public static void main(String[] args) {
		Graph<String> gra = new Graph<String>();
 
		Node<String> a = new Node<String>(new String("a"));
		Node<String> b = new Node<String>(new String("b"));		
		Node<String> c = new Node<String>(new String("c"));		
		Node<String> d = new Node<String>(new String("d"));
		Node<String> e = new Node<String>(new String("e"));
		Node<String> f = new Node<String>(new String("f"));

		Node<String> x = new Node<String>(new String("x"));
		Node<String> y = new Node<String>(new String("y"));
		Node<String> z = new Node<String>(new String("z"));
		Node<String> h = new Node<String>(new String("h"));


		
		gra.insertNode(a); 		
		gra.insertNode(b); 		
		gra.insertNode(c); 		
		gra.insertNode(d); 		
		gra.insertNode(e); 		
		gra.insertNode(f);
		
		//gra.insertNode(x);
		//gra.insertNode(y);
		//gra.insertNode(z);
		//gra.insertNode(h);

	
	
		gra.insertEdge(a, b, 2);
		gra.insertEdge(a, c, 1);
		gra.insertEdge(a, d, 5);
		gra.insertEdge(b, e, 2);
		gra.insertEdge(e, f, 2);
		gra.insertEdge(c, f, 88);

		//gra.insertEdge(h, a, 2);
		//gra.insertEdge(f, x, 2);
		//gra.insertEdge(x, y, 2);
		//gra.insertEdge(x, z, 2);
		//gra.insertEdge(d, z, 2);
	
		
		
		System.out.println("Grafo:");
		System.out.println(gra);
		
		//System.out.println("Is DAG? -" + (ZGraphServices.isDAG(gra) == true ? "yes" : "no"));

		//System.out.println("BFS:");
		//ZGraphServices.bfs(gra);
		//System.out.println("\n\n");

		System.out.println("DFS: ");
		ZGraphServices.dfs(gra);
		System.out.println("\n\n");

		//ZGraphServices.topologicalSort(gra);

		ZWGraph.sssp(gra, a);
		//ZWGraph.bellmanford(gra, a);

		ZWGraph.mst(gra);
		System.out.println();

		ZWGraph.prim(gra, a);
		System.out.println();

		ZWGraph.floydWarshall(gra);
		System.out.println();
	}
}
