package linkedlist;

import korat.finitization.IFinitization;
import korat.finitization.IIntSet;
import korat.finitization.IObjSet;
import korat.finitization.impl.FinitizationFactory;
import java.util.HashSet;

public class LinkedList {
	//singly linked list
	public static class Node {
		Node next;
		int data;
	}
	
	private Node head;
	private int size;
	
	public boolean repOK() {
		if (head == null)
			return size == 0;
		int count = 0;
		
		//Check how many elements are in the linkedlist 
		Node root = head;
		HashSet<Node> nodes = new HashSet<Node>();
		while (root != null) {
			if (nodes.contains(root)) return false;
			nodes.add(root);
			root = root.next;
			count++;
		}
		return count == size;
	}
	public static IFinitization finLinkedList(int nodesNum, int minSize, int maxSize, 
			int maxVal) {
		 IFinitization f = FinitizationFactory.create(LinkedList.class);
		 IObjSet nodes = f.createObjSet(Node.class, nodesNum, true);
		 f.set("head", nodes);
		 f.set("Node.next", nodes);
		 IIntSet values = f.createIntSet(0, maxVal);
		 f.set("Node.data", values);
		 IIntSet sizes = f.createIntSet(minSize, maxSize);
		 f.set("size", sizes);
		 return f;
	}
}
