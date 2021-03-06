import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private class IntPair{
		int x, y;
		IntPair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(IntPair ip)
		{
			if(ip.x == this.x && ip.y == this.y)
				return true;
			return false;
			
		}

		public boolean inList(ArrayList<IntPair> messages) {
			for(IntPair ip : messages)
			{
				if(ip.equals(this))
					return true;
			}
			return false;
		}
	}
	private int address;
	//	private HashMap<Node, Integer> connectionCost;
	private HashMap<Integer, Node> connections;
	private HashMap<Integer, int[]> routingTable;
	private ArrayList<IntPair> messages;
	private int msgID;
	Node(int a)
	{
		this.address = a;
		this.connections = new HashMap<Integer, Node>();
		//		this.connectionCost = new HashMap<Node, Integer>();
		this.routingTable = new HashMap<Integer, int[]>();
		routingTable.put(a, new int[] {0, a});
		//takes 0 time to send to itself via itself
		messages = new ArrayList<IntPair>();
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
		int mID = p.getID();
		IntPair mpid = new IntPair(p.getSrc(), mID);
		boolean seenMsg = mpid.inList(messages);
		if(!seenMsg) {
			messages.add(mpid);
			int msgType = p.getMsgType();
			switch(msgType)
			{
			case 0:
				if(p.getDst()== this.address) {
					System.out.println("Node: " + this.address + " received message: " + p.getMsg());
					return;
				}
				break;
			case 1:
				if(p.getDst()== this.address) {
					System.out.println("Node: " + this.address + " received message: " + p.getMsg());
				}
				break;
			case 2:
				if(p.getDst()== this.address) {
					sendRoutingTableUpdate(p.getSrc());
					return;
				}			
				break;
			case 3:
				if(p.getDst()== this.address) {
					updateRoutingTables(p, from);
					return;
				}
				break;
			case 4:
				if(p.getDst()== this.address) {
					System.out.println(p.getMsg() + "Destination");
					return;
				}
				
				String nString = p.getMsg();
				if (nString == null){
					nString = "";
				}
				nString += "Node " + this.address + " --> ";
				
				p.setMsg(nString);
				break;
			default:
				System.out.println("Node: " + this.address + " received message: " + p.getMsg());
				return;			
			}
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
		Packet p = new Packet(destination, this.address, msgType, msgID++, message);
		//		p.setMsgType(msgType);
		this.receive(p, this);
	}
	public void createRoutingTableRequest()
	{
		for (Node n: connections.values()) {
			Packet p = new Packet(n.address, this.address, Packet.ROUTING_TABLE_UPDATE_REQ, msgID++, null);
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
