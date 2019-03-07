import java.util.ArrayList;
import java.util.Scanner;

public class Network {
	//update this Constant to print available commands as added.
	private static String ERROR_MSG = "Unknown Command\n"
			+ "Try:\n"
			+ "\"send [int destination], [int source], [String message]\"\n"
			+ "\"node [option], [parameters]\"\n"
			+ "\"link [int node1], [int node2], [int cost]\"\n"
			+ "\"rtable [int Node]\"\n"
			+ "\"crtable\"\n"
			+ "\"traceroute [int to], [int from]\"\n";
	private static ArrayList<Node> ntwrk = new ArrayList<Node>();
	public static void main(String[] args) { 

		//		.csv has format:
		//		line 1 is number of nodes in network
		//		all subsequent lines are links specified in form
		//		node a, node b, link cost
		
		createNetwork(args[0]);
		System.out.println(args[0]);
//		ntwrk.get(4).createMessage(2, "Test Message");
//		for(Node n : ntwrk)
//		{
//			n.createRoutingTableRequest();
//		}
		boolean finished = false;
		Scanner userInput = new Scanner(System.in);
		while(!finished)
		{
			System.out.println("Enter command:");

			String arg = userInput.next();
			arg.toLowerCase();
			String p;
			switch(arg)
			{
			case "finish":
				finished = true;
				break;
			case "send":
				p = userInput.nextLine();
				sendMessage(p);
				break;
			case "node":
				p = userInput.nextLine();
				nodeCommands(p);
				break;
			case "link":
				p = userInput.nextLine();
				linkCommand(p);
				break;
			case "rtable":
				p = userInput.nextLine();
				routingTableCommand(p);
				break;
			case "crtable":
				for(Node n : ntwrk)
				{
					n.createRoutingTableRequest();
				}
				break;
			case "traceroute":
				p = userInput.nextLine();
				traceroute(p);
				break;
			default:
				System.out.print(ERROR_MSG);
				userInput.nextLine();
			}

		}
		userInput.close();
	}
	private static void traceroute(String p) {
		String np = p.replaceAll(" ", "");
		String[] newArgs = np.split(",");
		int to = Integer.parseInt(newArgs[0]);
		int from = Integer.parseInt(newArgs[1]);
		ntwrk.get(from).createMessage(to, Packet.TRACE_ROUTE, null);
	}
	private static void routingTableCommand(String p) {
		String np = p.replaceAll(" ", "");
		Node n = ntwrk.get(Integer.parseInt(np));
		System.out.println(n.printRoutingTable());


	}
	private static void linkCommand(String p) {
		String np = p.replaceAll(" ", "");
		String[] newArgs = np.split(",");
		Node n1 = ntwrk.get(Integer.parseInt(newArgs[0]));
		Node n2 = ntwrk.get(Integer.parseInt(newArgs[1]));
		new Link(n1, n2, Integer.parseInt(newArgs[2]));

	}
	private static void nodeCommands(String p) {
		String np = p.replaceAll(" ", "");
		String[] newArgs = np.split(",");
		switch(newArgs[0]) {
		case "add":
			ntwrk.add(new Node(Integer.parseInt(newArgs[1])));
			break;
		default:
			System.out.println("Invalid operation on Nodes");
		}

	}
	private static void sendMessage(String p) {
		String np = p.replaceAll(" ", "");
		String[] newArgs = np.split(",");
		int to = Integer.parseInt(newArgs[0]);
		int from = Integer.parseInt(newArgs[1]);
		String msg = newArgs[2];	
		ntwrk.get(from).createMessage(to, msg);
	}
	private static void createNetwork(String file) {
		In nStream = new In(file);
		String allN=nStream.readAll();
		String[] array=allN.split("\n");
		int numNodes = Integer.parseInt(array[0]);
		for(int i = 0; i < numNodes; i++)
		{
			ntwrk.add(new Node(i));
		}
		for ( int i = 1; i < array.length; i++){
			String s = array[i];
			String[] newNode = s.split(",\\s+");
			new Link(ntwrk.get(Integer.parseInt(newNode[0])), ntwrk.get(Integer.parseInt(newNode[1])), Integer.parseInt(newNode[2]));

		}		
	}

}
