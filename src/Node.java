import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private int address;
	private HashMap<Node, Integer> connectionCost;
	private ArrayList<Node> connections;
	private HashMap<Integer, Node> routingTable;
	Node(int a)
	{
		this.address = a;
		this.connections = new ArrayList<Node>();
		this.connectionCost = new HashMap<Node, Integer>();
		this.routingTable = new HashMap<Integer, Node>();
	}

	void addConnection(Node connectee, int cost)
	{
		connections.add(connectee);
		connectionCost.put(connectee, (Integer) cost);
	}

	private void sendTo(Node nextHop, Packet p)
	{
		nextHop.receive(p, this);
	}

	private void receive(Packet p, Node from) {

		if(p.getDst() == this.address){
			System.out.println("Node: " + this.address + " received message: " + p.getMsg());
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
				Node nxt = routingTable.get(p.getDst());
				try {
					sendTo(nxt, p);

				} catch (java.lang.NullPointerException e)
				{
					for(Node n : connections) {
						if(!n.equals(from))		//don't send back where it came from
							sendTo(n, p);		//broadcast if unknown route
					}
				}
//				if (nxt.notNull)
//				{
//					for(Node n : connections) {
//						if(!n.equals(from))		//don't send back where it came from
//							sendTo(n, p);		//broadcast if unknown route
//					}
//				}
//				else {
//					sendTo(nxt, p);
//				}
			}
			else {
				createMessage(p.getSrc(), Packet.MESSAGE_EXPIRED_ERROR, "Message expired at Node: " + this.address + ".");
			}

		}

	}

	public void createMessage(int destination, String message) {
		Packet p = new Packet(destination, this.address, message);
		this.receive(p, this);

	}
	public void createMessage(int destination, int msgType, String message) {
		Packet p = new Packet(destination, this.address, message);
		p.setMsgType(msgType);
		this.receive(p, this);
	}
}
