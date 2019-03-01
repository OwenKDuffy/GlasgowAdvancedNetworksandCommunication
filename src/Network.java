import java.util.ArrayList;

public class Network {
	
	private static ArrayList<Node> ntwrk;
	public static void main(String[] args) {
		ntwrk = new ArrayList<Node>();
		// TODO add implementation to translate from CSV to network
		// interim
		for(int i = 0; i < 5; i++)
		{
			ntwrk.add(new Node(i));
		}
		new Link(ntwrk.get(0), ntwrk.get(1), 5);
		new Link(ntwrk.get(1), ntwrk.get(2), 3);
		new Link(ntwrk.get(0), ntwrk.get(4), 1);
		ntwrk.get(4).createMessage(2, "Test Message");
//		boolean finished = false;
//		while(!finished)
//		{
//			
//		}
	}

}
