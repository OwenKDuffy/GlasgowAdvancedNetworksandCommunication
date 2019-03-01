import java.util.HashMap;

public class Node {
	private int address;
	private HashMap<Node, Integer> connections;
	private HashMap<Integer, Node> routingTable;
	Node(int a)
	{
		this.address = a;
		this.connections = new HashMap<Node, Integer>();
	}

	void addConnection(Node connectee, int cost)
	{
		connections.put(connectee, (Integer) cost);
	}

	private void sendTo(Node nextHop, Packet p)
	{

		nextHop.receive(p, this);

	}

	private void receive(Packet p, Node from) {

		if(p.getDst() == this.address){
			System.out.println("" + this.address + " received message:\n" + p.getMsg());
		}
		else {
			//check if src is in routing table
			//if not add to
			if (!routingTable.containsKey(p.getSrc())) {
				routingTable.put(p.getSrc(), from);
			}
			
			//if packet still has hops decrement TTL and send
			if(p.getTTL() > 0) {
				p.decrementTTL();
				sendTo(routingTable.get(p.getDst()), p);
			}
			else {
				//send back error
			}

		}

	}
}
