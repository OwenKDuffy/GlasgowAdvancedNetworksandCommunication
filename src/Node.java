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
		routingTable.put(a, new int[] {0, a});
		//takes 0 time to send to itself via itself
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
			int msgType = p.getMsgType();
			switch(msgType)
			{
			case 0:
				System.out.println("Node: " + this.address + " received message: " + p.getMsg());
				return;
			case 1:
				//				standardMessage(p, from);
				break;
			case 2:
				sendRoutingTableUpdate(p.getSrc());
				break;
			case 3:
				updateRoutingTables(p, from);
				break;
			default:
				System.out.println("Node: " + this.address + " received message: " + p.getMsg());
				return;
			}
		}
		else {
			forwardMessage(p, from);
		}
	}


	private void sendRoutingTableUpdate(int from) {
		String message = createRoutingTableMessage();
		createMessage(from, Packet.ROUTING_TABLE_UPDATE_RES, message);

	}

	private void updateRoutingTables(Packet p, Node from) {
		String msg = p.getMsg();
		String[] ma = msg.split("\n");
		for(String s: ma) {
			String[] sa = s.split(",");
			int destNode = Integer.parseInt(sa[0]);
			//node in routing table
			int newDistToDest = Integer.parseInt(sa[1]);
			//get current dist to dest
			int[] neighbour = routingTable.get(from.address);
			newDistToDest += neighbour[0];
			//plus dist from this node to connected node		
			int[] rt = routingTable.get(destNode);
			if(rt == null) {
				//dist to the node from connected node
				rt = new int[] {Integer.MAX_VALUE, from.address};
				routingTable.put(destNode, rt);

			}
			//get current routing table entry
			int distToDest = rt[0];
			//get current dist to destination
			if (newDistToDest < distToDest) {
				routingTable.get(destNode)[0] = newDistToDest;
				routingTable.get(destNode)[1] = from.address;
			}

		}

	}

	private void forwardMessage(Packet p, Node from) {


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
			else {
				Node nxt = connections.get(n[1]);

				sendTo(nxt, p);

			}
		}
		else {
			createMessage(p.getSrc(), Packet.MESSAGE_EXPIRED_ERROR, "Message expired at Node: " + this.address + ".");
		}



	}

	private void broadcast(Packet p, Node from ) {
		for(Node n : connections.values()) {
			if(!n.equals(from)) {
				sendTo(n, p);
			}
		}

	}

	public String createRoutingTableMessage()
	{
		String s = "";
		for(int n: routingTable.keySet())
		{
			s+=n + ",";
			s+= routingTable.get(n)[0];
			s+="\n";
		}
		return s;
	}
	public void createMessage(int destination, String message) {
		Packet p = new Packet(destination, this.address, message);
		this.receive(p, this);

	}
	public void createMessage(int destination, int msgType, String message) {
		Packet p = new Packet(destination, this.address, msgType, message);
		//		p.setMsgType(msgType);
		this.receive(p, this);
	}
	public void createRoutingTableRequest()
	{
		for (Node n: connections.values()) {
			Packet p = new Packet(n.address, this.address, Packet.ROUTING_TABLE_UPDATE_REQ, null);
			this.sendTo(n, p);
		}
	}

	public String printRoutingTable() {
		String s = "";
		for(int n: routingTable.keySet())
		{
			s+="Dest: " + n + ", ";
			s+="Dist to: " + routingTable.get(n)[0];
			s+=", Via: " + routingTable.get(n)[1] + "\n";
		}
		return s;
	}
}
