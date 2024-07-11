//package Prova;
import java.util.*;

public class ZWGraph<V> {
    HashMap<Node<V>, List<Edge<V>>> graph;

    /* Costruttore */	
    public ZWGraph() {
        graph = new HashMap<Node<V>, List<Edge<V>>>();
    }

    /* Restituisce una collezione contenente i nodi del grafo */	
    public Collection<Node<V>> getNodes() {
        return graph.keySet();
    }
    
    /* Restituisce una collezione contenente gli archi uscenti dal nodo dato */	
    public Collection<Edge<V>> getOutEdges(Node<V> source) {
        return graph.get(source);
    }

    /* Aggiunge un nuovo nodo al grafo */
    public void insertNode(Node<V> v) {
        if (!graph.containsKey(v))
            graph.put(v, new LinkedList<Edge<V>>());
    }
    
    /* Aggiunge un nuovo arco (pesato) al grafo tra il nodo source e il nodo destination, di peso weight */
    public void insertEdge(Node<V> source, Node<V> destination, Integer weight) throws RuntimeException {
        if (!(graph.containsKey(source) && graph.containsKey(destination)))
            throw new RuntimeException("Nodo non presente nel grafo");

        graph.get(source).add(new Edge<V>(source, destination, weight));
    }

    /* Aggiunge un nuovo arco (non pesato, ovvero di peso 1) al grafo tra il nodo source e il nodo destination */
    public void insertEdge(Node<V> source, Node<V> destination) throws RuntimeException {
        if (!(graph.containsKey(source) && graph.containsKey(destination)))
            throw new RuntimeException("Nodo non presente nel grafo");

        graph.get(source).add(new Edge<V>(source, destination, 1));
    }
    
    /* Stampa il grafo su stdout */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Node<V> v : this.getNodes()) {
            sb.append(v + " : ");
    
            for (Edge<V> u : this.getOutEdges(v))
                sb.append(u + " ");
            sb.append("\n");
        }
        return sb.toString();
    }

    /* Restituisce l'insieme di tutti gli archi del grafo */
    public Set<Edge<V>> getEdges() {
        HashSet<Edge<V>> set = new HashSet<>();
        
        for(Node<V> n : this.graph.keySet())
            set.addAll(graph.get(n));
        
        return set;
    }

    public static <V> void sssp(Graph<V> g, Node<V> source) {
    	MinHeap<Node<V>> coda = new MinHeap<Node<V>>();
    	HashMap<Node<V>,HeapEntry<Node<V>>> dist = new HashMap<Node<V>, HeapEntry<Node<V>>>();
    	final int Inf = 1000000;
    	for (Node<V> n: g.getNodes()) {
    		HeapEntry<Node<V>> hu = coda.insert(n == source ? 0 : Inf, n);    	
    		dist.put(n, hu);
    	}
    	while(!coda.isEmpty()) {
    		HeapEntry<Node<V>> hu = coda.removeMin();
    		Node<V> n = hu.getValue();
    		for (Edge<V> e: g.getOutEdges(n)) {
    			Node<V> v = e.getTarget();
    			if (dist.get(n).getKey() + e.getWeight() < dist.get(v).getKey()) {
    				coda.replaceKey(dist.get(v), dist.get(n).getKey() + e.getWeight());
                    v.par = n; // ??
    			}
    		}
    		
    	}

        System.out.println("MINIMUM COSTS:");
    	for(Node<V> u : g.getNodes()) {
            System.out.println(u + " " + dist.get(u).getKey());
    	}
        System.out.println("PATHS:");
		for (Node<V> n : g.getNodes()) {
			System.out.print(n.value);
			Node<V> p = n.par;
			while (p != null || p == source) {
				System.out.print(" <-- " + p.value);
				p = p.par;
			}
			System.out.println();
		}
		System.out.println();
    }
    
    public static <V> int bellmanford(Graph<V> g, Node<V> s) {
    	for (Node<V> n: g.getNodes()) {
    		n.dist = n==s ? 0 : 100000;
    	}
    	
    	for (Node<V> n: g.getNodes()) {
    		for (Edge<V> e: g.getEdges()) {
    			Node<V> t = e.getTarget();
    			Node<V> so = e.getSource();
    			if (t.dist > e.getWeight() + so.dist) {
    				t.dist = e.getWeight() + so.dist;
    			}
    		}
    	}
    	
    	for (Edge<V> e: g.getEdges()) {
    		if (e.getTarget().dist > e.getWeight() + e.getSource().dist) {return 0;}
    	}

    	System.out.println("MINIMUM COSTS:");
    	for (Node<V> n: g.getNodes()) {
    		System.out.println(n+" "+n.dist);
    	}
        /*
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
        */
    	return 1;
    }
    
    //Krsuskal
    public static <V> void mst(Graph<V> g) {
    	Partition<V> P;
        MinHeap<Edge<V>> Q;
        int i=0;
    	for (Node<V> e: g.getNodes()) {
    		e.map = i++;
    	}
        P = new Partition<V>(g.getNodes());
        Q = new MinHeap<Edge<V>>();
        for(Edge<V> e : g.getEdges()) {
            Q.insert(e.getWeight(), e);
        }
        while (!Q.isEmpty()) {
        	Edge<V> e = Q.removeMin().getValue();
            Node<V> u = e.getSource(), v = e.getTarget();
            List<Node<V>> s = P.find(u.map), t = P.find(v.map);
            if(s != t) {
                System.out.println(u.getValue() + " " + v.getValue());
                P.union(u.map, v.map);
            }
        }	  
    }
    
    public static <V> void prim(Graph<V> g, Node<V> start) {
    	 MinHeap<Node<V>> minHeap = new MinHeap<>();
         HashMap<Node<V>, Integer> distance = new HashMap<>();
         HashMap<Node<V>, Node<V>> parent = new HashMap<>();
         HashSet<Node<V>> inMST = new HashSet<>();
         // metto tutte le distanze a +Inf
         for (Node<V> node : g.getNodes()) 
             distance.put(node, Integer.MAX_VALUE);
         // parto dalla sorgente che quindi non ha parenti, ne tanto meno una distanza
         minHeap.insert(0, start);
         distance.put(start, 0);
         parent.put(start, null);
         
         while (!minHeap.isEmpty()) {
             Node<V> u = minHeap.removeMin().getValue();
             inMST.add(u);
             for (Edge<V> edge : g.getOutEdges(u)) {
                 Node<V> v = edge.getTarget();
                 int weight = edge.getWeight();
                 if (!inMST.contains(v) && weight < distance.get(v)) {
                     distance.put(v, weight);
                     parent.put(v, u);
                     minHeap.insert(weight, v);
                 }
             }
         }
         for (Node<V> node : g.getNodes())
             if (parent.get(node) != null)
                 System.out.println(parent.get(node).getValue() + " - " + node.getValue());
    }
    
    public static <V> void floydWarshall(Graph<V> g) {
    	List<Node<V>> nodes = new ArrayList<>(g.getNodes());
        int n = nodes.size();
        //Mappo i Nodi ai loro indici
        Map<Node<V>, Integer> nodeIndex = new HashMap<>(); 
        for (int i = 0; i < n; i++) {
            nodeIndex.put(nodes.get(i), i);
        }
    	// Matrice delle distanze
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
            dist[i][i] = 0;
        }
        // Distanze basate sui vicini
        for (Node<V> u : g.getNodes()) {
            for (Edge<V> e : g.getOutEdges(u)) {
                int uIndex = nodeIndex.get(u);
                int vIndex = nodeIndex.get(e.getTarget());
                dist[uIndex][vIndex] = e.getWeight();
            }
        }
    	// 
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        // Stampa finale
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == Integer.MAX_VALUE / 2) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
}
     