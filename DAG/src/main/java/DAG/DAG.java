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
		
		HashSet<Node> visited = new HashSet<Node>();
		LinkedList<Node> toVisit = new LinkedList<Node>();
		
	
		toVisit.add(root);
		
		while(!toVisit.isEmpty()) {
			Node current = toVisit.remove();
			visited.add(current); //adding current node to list of visited nodes
			for (int i = 0; i < current.children.length; i++) {
				Node c = current.children[i];
				//check if we have already visited this node
				if(visited.contains(c)) return false;
				toVisit.add(c);
			}
		}
		return visited.size() == size;
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
