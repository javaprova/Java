//package Prova;
import java.util.*;

public class Node<V> implements Cloneable {
	public enum Status {UNEXPLORED, EXPLORED, EXPLORING}

	protected V value; //oppure detto "element"
	protected LinkedList<Node<V>> outEdges;
	protected LinkedList<Node<V>> inEdges;

	protected Status state; // tiene traccia dello stato di esplorazione
	protected int timestamp; // utile per associare valori interi ai vertici

	int map; // Usato da partition union e find
	int dist; // utile per memorizzare distanze in algoritmi per cammini minimi
	Node<V> par; // ??

	public Node(V e) {
		value = e;
		state = Status.UNEXPLORED;
	}

	public V getValue() { return value; } 

	@Override
	public String toString() {
		return "Node [value=" + value + ", state=" + state + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return (Node<V>) this;
	}
}
