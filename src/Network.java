import java.util.ArrayList;

public class Network {

	private static ArrayList<Node> ntwrk;
	public static void main(String[] args) {
		ntwrk = new ArrayList<Node>();
		// TODO add implementation to translate from CSV to network
		// interim

		In nStream = new In("network.csv");
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


		
//		new Link(ntwrk.get(0), ntwrk.get(1), 5);
//		new Link(ntwrk.get(1), ntwrk.get(2), 3);
//		new Link(ntwrk.get(0), ntwrk.get(4), 1);
		ntwrk.get(4).createMessage(2, "Test Message");
		//		boolean finished = false;
		//		while(!finished)
		//		{
		//			
		//		}
	}

}
