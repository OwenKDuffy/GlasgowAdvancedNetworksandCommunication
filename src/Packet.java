
public class Packet {
	private int dst, src;
	private String msg;
	private int timeToLive = 10;
	private int messageType = 0; 
	//this would be best as an enum like type
	//0 = standard message
	public static final int MESSAGE_EXPIRED_ERROR = 1;

	//1 = message expiredß
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

	public void setMsgType(int msgType) {
		this.messageType = msgType;
	}
}
