package nlr.net;

public final class NetPeerStateMessageReceived extends NetPeerState {

	private NetConnection senderNetConnection;
	private NetMessage netMessage;
	
	public NetConnection getSenderNetConnection() {
		return senderNetConnection;
	}
	
	public NetMessage getNetMessage() {
		return netMessage;
	}

	public NetPeerStateMessageReceived(
			NetConnection senderNetConnection,
			NetMessage netMessage) {
		
		super();
		
		this.senderNetConnection = senderNetConnection;
		this.netMessage = netMessage;
	}
}
