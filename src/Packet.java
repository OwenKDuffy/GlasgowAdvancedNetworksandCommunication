
public class Packet {
	private int dst, src;
	private String msg;
	private int timeToLive = 10;
	
	Packet(int destinationAddress, int sourceAddress, String message)
	{
		dst = destinationAddress;
		src = sourceAddress;
		msg = message;
	}

	public int getDst() {
		// TODO Auto-generated method stub
		return dst;
	}
	
	public int getSrc() {
		return src;
	}
	
	public String getMsg() {
		return msg;
	}
	public int getTTL() {
		return timeToLive;
	}
	public void decrementTTL() {
		timeToLive--;
	}
}
