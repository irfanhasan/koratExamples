package DAG;
import java.util.*;
import korat.finitization.*;
import korat.finitization.impl.*;

public class DAG {
	public static class Node {
		Node[] children;
	}
	
	private Node root;
	private int size;
	
	public boolean repOK() {
		if (root == null)
			return size == 0;
		//if node -> 1; then all of this node's children have been visited and its acyclic thus far
		//if node -> -1: then we are still on path where this node has already been seen, cycle detected
		HashMap<Node, Integer> visited = new HashMap<Node, Integer>();
		return dfs(root, visited) && visited.size() == size;
	}
	
	
	/*
	 * Check for cycles. If the current node has already been visited then there was
	 * a cycle in the graph.
	 */
	private boolean dfs(Node current, HashMap<Node, Integer> visited) {
		HashSet<Node> children = new HashSet<Node>(); //checking for duplicate children
		if(!visited.containsKey(current)) {
			visited.put(current, -1);
			for (int i = 0; i < current.children.length; i++) {
				Node child = current.children[i];
				if (!children.add(child)) return false;
				if (!dfs(child, visited)) return false;
			}
		} else {
			if(visited.get(current) == -1)
				return false;
		}
		visited.put(current, 1);
		return true;
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
