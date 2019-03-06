import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private int address;
	//	private HashMap<Node, Integer> connectionCost;
	private HashMap<Integer, Node> connections;
	private HashMap<Integer, int[]> routingTable;
	Node(int a)
	{
		this.address = a;
		this.connections = new HashMap<Integer, Node>();
		//		this.connectionCost = new HashMap<Node, Integer>();
		this.routingTable = new HashMap<Integer, int[]>();
	}

	void addConnection(Node connectee, int cost)
	{
		connections.put(connectee.address, connectee);
		//		connectionCost.put(connectee, (Integer) cost);
		routingTable.put(connectee.address, new int[]{cost, connectee.address});
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
			//			if (!routingTable.containsKey(p.getSrc())) {
			//				routingTable.put(p.getSrc(), new int[] {Integer.MAX_VALUE, from.address);
			//			}

			//if packet still has hops decrement TTL and send
			if(p.getTTL() > 0) {
				p.decrementTTL();
				int d = p.getDst();
				int[] n = routingTable.get(d);
				if (n == null) {
					broadcast(p, from);
					//we dont know how to get here yet
				}
				Node nxt = connections.get(n[1]);

				sendTo(nxt, p);


			}
			else {
				createMessage(p.getSrc(), Packet.MESSAGE_EXPIRED_ERROR, "Message expired at Node: " + this.address + ".");
			}

		}

	}

	private void broadcast(Packet p, Node from ) {
		for(Node n : connections.values()) {
			if(!n.equals(from)) {
				sendTo(n, p);
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
