//package Prova;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;


public class ZGraphServices<V> {

	private static int tmp = 0;

    public static <V> void sweep(Graph<V> g) {
    	g.resetStatus(); //mette a zero tutti i campi map e dist e mette a UNEXPLORED
    	for (Node<V> m: g.getNodes()) {
    		if (m.state == Node.Status.UNEXPLORED) {DFS(m);}
    	}
    }
    
    public static <V> void DFS(Node<V> n) {
    	n.timestamp = ++tmp;
    	n.state = Node.Status.EXPLORING;
    	for (Node<V> m: n.outEdges) {
    		System.out.print(n.value + " -> " + m.value + ": {");
    		if (m.state == Node.Status.UNEXPLORED) {
    			System.out.print("TREE}\n");
    			DFS(m);
    		}else if (m.state == Node.Status.EXPLORING) {
    			System.out.print("BACK}\n");
    		}else {
    			if (n.timestamp > m.timestamp) {
    				System.out.print("CROSS}\n");
    			}else {
    				System.out.print("FORWARD}\n");
    			}
    		}
    	}
    	n.state = Node.Status.EXPLORED;
    }

	private static <V> void dfs_visit(Graph<V> g, Node<V> node) {
		if (node.state != Node.Status.UNEXPLORED)
			return;
		node.state = Node.Status.EXPLORING;
		System.out.print(node.getValue()+ " ");
		for (Edge<V> e: g.getOutEdges(node)) {
			Node<V> v = e.getTarget();
			if (v.state == Node.Status.UNEXPLORED) {
				dfs_visit(g, v);
			} 
		}
		node.state = Node.Status.EXPLORED;
	}

	public static <V> void dfs(Graph<V> g) {
		for (Node<V> node: g.getNodes()) // alle volte non è da fare il primo ciclo
			node.state = Node.Status.UNEXPLORED;
		for (Node<V> node: g.getNodes()) {
			if (node.state == Node.Status.UNEXPLORED)
				dfs_visit(g, node);
		}
	}
	
	private static <V> void bfs_visit(Graph<V> g, Node<V> s) {
		for (Node<V> node: g.getNodes()) { // alle volte non è da fare il primo ciclo
			node.state = Node.Status.UNEXPLORED;
			node.dist = 99999;	// ??
			node.par = null;	// ??
		}
		if (s.state != Node.Status.UNEXPLORED)
			return;
		Queue<Node<V>> Q = new ArrayDeque<>();
		
		s.state = Node.Status.EXPLORING;
		s.dist = 0; 	// ??
		s.par = null;	// ??
		Q.add(s);
		while (!Q.isEmpty()) {
			Node<V> u = Q.poll();
			//System.out.print(u.getValue() + " ");
			for (Edge<V> e: g.getOutEdges(u)) {
				Node<V> v = e.getTarget();
				if (v.state == Node.Status.UNEXPLORED) {
					v.state = Node.Status.EXPLORING;
					v.dist = u.dist + 1;	// ??
					v.par = u;				// ??
					Q.add(v);
				}
			}
			u.state = Node.Status.EXPLORED;
		}

		System.out.println("DISTANCES:");
		for (Node<V> n : g.getNodes()) {
			System.out.println(n.value + ": " + n.dist);
		}
		System.out.println("PATHS:");
		for (Node<V> n : g.getNodes()) {
			System.out.print(n.value);
			Node<V> p = n.par;
			while (p != null || p == s) {
				System.out.print(" <-- " + p.value);
				p = p.par;
			}
			System.out.println();
		}
		System.out.println();
	}

	public static <V> void bfs(Graph<V> g) {
		for (Node<V> node: g.getNodes()) { // credo serva quando si vuole visitare tutto il grafo anche se non è connesso (però va tolto in bfs_visit())
			node.state = Node.Status.UNEXPLORED;
			node.dist = 99999;	// ??
			node.par = null;	// ??
		}
		for (Node<V> node: g.getNodes()) {
			if (node.state == Node.Status.UNEXPLORED) {
				System.out.println("BFS_VISIT from source " + node.value + ": ");
				bfs_visit(g, node);
			}
		}
	}
    
    public static <V> void topologicalSort(Graph<V> g) {
    	g.resetStatus();
    	LinkedList<Node<V>> l = new LinkedList<Node<V>>();
    	for (Node<V> n: g.getNodes()) {
    		if (n.state == Node.Status.UNEXPLORED) {
    			if (DFS_DAG(g, n, l) > 0) {
    				System.out.println("NO DAG!!!");
    				return;
    			}
    		}
    	}
		//System.out.println(l);
    	for (Node<V> m : l) {
    		System.out.print(m.value + " ");
    	}
    	System.out.println("");
    }
    
	public static <V> int DFS_DAG(Node<V> node, LinkedList<Node<V>> l) { // dice se ci sono cicli o meno e mette nella lista tutti i nodi in ordine topologico
    	if(node.state == Node.Status.EXPLORED) {return 0;}
    	else if (node.state == Node.Status.EXPLORING) {return 1;}
    	int d=0;
    	node.state = Node.Status.EXPLORING;
    	for (Node<V> m: node.outEdges) { //node.outEdges oppure g.getOutEdges() e poi si prende il Node target (si dovrebbe passare anche g alla funzione)
    		d += DFS_DAG(m, l);
    	}
    	node.state = Node.Status.EXPLORED;
    	l.addFirst(node);
    	return d;
    }

    public static <V> int DFS_DAG(Graph<V> g, Node<V> node, LinkedList<Node<V>> l) { // dice se ci sono cicli o meno e mette nella lista tutti i nodi in ordine topologico
    	if(node.state == Node.Status.EXPLORED) {return 0;}
    	else if (node.state == Node.Status.EXPLORING) {return 1;}
    	int d=0;
    	node.state = Node.Status.EXPLORING;
    	for (Edge<V> e : g.getOutEdges(node)) { //node.outEdges oppure g.getOutEdges() e poi si prende il Node target (si dovrebbe passare anche g alla funzione)
    		Node<V> m = e.getTarget();
			d += DFS_DAG(g, m, l);
    	}
    	node.state = Node.Status.EXPLORED;
    	l.addFirst(node);
    	return d;
    }

	//forse sbagliata, forse non serve
	public static <V> boolean isDAG(Graph<V> g) {
		LinkedList<Node<V>> list = new LinkedList<>();
		for (Node<V> node: g.getNodes()) {
			if (node.state == Node.Status.UNEXPLORED)
				if (DFS_DAG(g, node, list) > 0)
					return false;
		}
		return true;
	}
	//Marius: restituisce anche il numero di componenti


    //pag.520
    public static <V> void strongConnectedComponents(Graph<V> g) {
		int num_comp = 0; // ??
        g.resetStatus();
        LinkedList<Node<V>> ll = new LinkedList<Node<V>>();
        for (Node<V> m: g.getNodes()) {
        	if (m.state == Node.Status.UNEXPLORED) {
        		DFS_DAG(m, ll); // mette ordinamento topologico in ll
        	}
        }
        g.resetStatus();
        for (Node<V> n: ll) {
        	if (n.state == Node.Status.UNEXPLORED) {
        		LinkedList<Node<V>> l = new LinkedList<Node<V>>();
				num_comp++; // ??
        		Traspose(n, l);
				System.out.println("Componente fortemente connessa numero: " + num_comp);
        		for (Node<V> m: l) {
        			System.out.print(m.value + " ");
        		}
        		System.out.println("");
        	}
        }
    }
    
	// Inverte tutti i versi degli archi (non credo)
    public static <V> void Traspose(Node<V> n, LinkedList<Node<V>> l) {
    	if (n.state == Node.Status.EXPLORING || n.state == Node.Status.EXPLORED) {
    		return;
    	}
    	n.state = Node.Status.EXPLORING;
    	for (Node<V> m: n.inEdges) {
    		Traspose(m, l);
    	}
    	l.addLast(n);
    	n.state = Node.Status.EXPLORED;
    }
}