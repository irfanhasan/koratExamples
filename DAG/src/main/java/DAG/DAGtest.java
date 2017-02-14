package DAG;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import DAG.DAG.Node;

public class DAGtest {
	@Test
	public void simpleDAG() {
		//Simple DAG
		//1 -> 2 -> 3
		Node n1 = new Node();
		Node n2 = new Node();
		Node n3 = new Node();
		DAG g1 = new DAG(n1);
		g1.addChild(n1, n2, true);
		g1.addChild(n2, n3, true);
		assertEquals(true, g1.repOK());
		
		//just root
		DAG g2 = new DAG(new Node());
		assertEquals(true, g2.repOK());
	}
	
	@Test
	public void additionalDAG() {
		//DAGs that have multiple paths going forward that hit the same children
		//4 -> 5 -> 6
		//4 -> 6
		Node n4 = new Node();
		Node n5 = new Node();
		Node n6 = new Node();
		DAG g1 = new DAG(n4);
		g1.addChild(n4, n5, true);
		g1.addChild(n4, n6, true);
		g1.addChild(n5, n6, false);
		assertEquals(true, g1.repOK());
		
		//1 -> 2 -> 3 -> 4 ... 10
		//1 -> 3
		//2 -> 3
		//1 -> 10
		Node n1 = new Node();
		Node n2 = new Node();
		Node n3 = new Node();
		DAG g2 = new DAG(n1);
		g2.addChild(n1, n2, true);
		g2.addChild(n1, n3, true);
		g2.addChild(n2, n3, false);
		Node parent = n3;
		for (int i = 0; i < 7; i++) {
			Node child = new Node();
			g2.addChild(parent, child, true);
			parent = child;
		}
		g2.addChild(n1, parent, false);
		assertEquals(true, g2.repOK());
		
	}
	
	@Test
	public void simpleCycle() {
		//1 -> 2 
		//2 -> 1
		
		Node n1 = new Node();
		Node n2 = new Node();
		
		DAG g1 = new DAG(n1);
		g1.addChild(n1, n2, true);
		g1.addChild(n2, n1, false);
		
		assertEquals(false, g1.repOK());
		
		
		//1 -> 1
		
		Node n3 = new Node();
		DAG g2 = new DAG(n3);
		g2.addChild(n3, n3, false);
		assertEquals(false, g2.repOK());
	}
	
	@Test
	public void complicatedCycle() {
		//Multiple paths leading to cycle
		//1 -> 2 -> 3 -> 4 ... 10
		//3 -> 1
		//3 -> 2
		//10 -> 1
		Node n1 = new Node();
		Node n2 = new Node();
		Node n3 = new Node();
		DAG g2 = new DAG(n1);
		g2.addChild(n1, n2, true);
		g2.addChild(n3, n1, true);
		g2.addChild(n3, n2, false);
		Node parent = n3;
		for (int i = 0; i < 7; i++) {
			Node child = new Node();
			g2.addChild(parent, child, true);
			parent = child;
		}
		g2.addChild(parent, n1, false);
		assertEquals(false, g2.repOK());
	}
}
