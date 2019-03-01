
public class Link {
	private Node n1, n2;
	private int cost;
	
	Link(Node n1, Node n2, int c)
	{
		this.n1 = n1;
		this.n2 = n2;
		this.cost = c;
		this.n1.addConnection(n2, cost);
		this.n2.addConnection(n1, cost);
	}
}
