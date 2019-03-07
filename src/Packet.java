
public class Packet {
	private int dst, src;
	private String msg;
	private int msgid;
	private int timeToLive = 10;
	private int messageType = 0; 
	//this would be best as an enum like type
	//0 = standard message
	public static final int MESSAGE_EXPIRED_ERROR = 1;
	//1 = message expired
	public static final int ROUTING_TABLE_UPDATE_REQ = 2;
	//2 = request routing table update
	public static final int ROUTING_TABLE_UPDATE_RES = 3;
	//3 = respond to routing table update
	public static final int TRACE_ROUTE = 4;
	//4 = trace route

	Packet(int destinationAddress, int sourceAddress, String message)
	{
		dst = destinationAddress;
		src = sourceAddress;
		msg = message;
	}

	public Packet(int destinationAddress, int sourceAddress, int msgType, int id, String messageTxt) {
		dst = destinationAddress;
		src = sourceAddress;
		messageType = msgType;
		msg = messageTxt;
		msgid = id;
	}

	public int getID() {
		return msgid;
	}
	public int getDst() {
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
	public int getMsgType() {
		return messageType;
	}

	public void setMsg(String newMessage) {
		this.msg = newMessage;
		return;
	}
}
