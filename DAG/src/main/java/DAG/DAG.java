package DAG;
import java.util.*;
import korat.finitization.*;
import korat.finitization.impl.*;

public class DAG {
	public static class Node {
		Node[] children;
	}
	
	private Node root;
	//private Node[] nodes;
	private int size;
	
	
	

	
	public boolean repOK() {
		//topological sort
		if (root == null)
			return size == 0;
		Node[] nodes = getList();
		HashMap<Node, Integer> incoming = new HashMap<Node, Integer>();
		//detecting all incoming edges
		for (int i = 0; i < nodes.length; i++) {
			Node current = nodes[i];
			for (int j = 0; j < current.children.length; j++) {
				Node child = current.children[j];
				updateIncoming(child, true, incoming);
			}
		}
		
		incoming.put(root, 0);
		
		LinkedList<Node> noIncoming = new LinkedList<Node>();
		LinkedList<Node> topSort = new LinkedList<Node>();
		findNoIncoming(noIncoming, incoming);
		while(!noIncoming.isEmpty()) {
			Node current = noIncoming.remove();
			topSort.add(current);
			for (int i = 0; i < current.children.length; i++) {
				Node child = current.children[i];
				updateIncoming(child, false, incoming);
				if (incoming.get(child) == 0) noIncoming.add(child);
			}
		}
		return verifyDAG(incoming) && topSort.size() == size;
		
	}
	
	private void updateIncoming(Node n, boolean add, HashMap<Node, Integer> m) {
		if (m.containsKey(n)) {
			int update = add ? m.get(n) + 1 : m.get(n) - 1;
			m.put(n, update);
		} else {
			m.put(n, 1);
		}
	}
	private Node[] getList() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> visited = new HashSet<Node>();
		queue.add(root);
		nodes.add(root);
		visited.add(root);
		while(!queue.isEmpty()) {
			Node node = queue.remove();
			for (int i = 0; i < node.children.length; i++) {
				Node child = node.children[i];
				if (!visited.contains(child)) {
					queue.add(child);
					visited.add(child);
					nodes.add(child);
				}
			}
		}
		return nodes.toArray(new Node[nodes.size()]);
	}
	private boolean verifyDAG(HashMap<Node, Integer> m) {
		for (Node n : m.keySet()) {
			if (m.get(n) != 0) return false;
		}
		return true;
	}
	
	private void findNoIncoming(LinkedList<Node> set, HashMap<Node, Integer> m) {
		for (Node n : m.keySet()) {
			if (m.get(n) == 0) set.add(n);
		}
	}
	

	
	public static IFinitization finDAG(int nodeNum) {
        IFinitization f = FinitizationFactory.create(DAG.class);

        f.set("size", f.createIntSet(nodeNum));

        IObjSet nodes = f.createObjSet(Node.class, nodeNum, false);
        f.set("root", nodes);
		
        IIntSet arrLen = f.createIntSet(0, nodeNum - 1);
        IArraySet childrenArray = f.createArraySet(Node[].class, arrLen, nodes, nodeNum);
        
        f.set("Node.children", childrenArray);
        return f;
    }
}
