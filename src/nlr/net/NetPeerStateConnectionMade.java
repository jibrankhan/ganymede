package nlr.net;

public final class NetPeerStateConnectionMade extends NetPeerState {

	private NetConnection newNetConnection;

	public NetConnection getNewNetConnection() {
		return newNetConnection;
	}

	public NetPeerStateConnectionMade(NetConnection newNetConnection) {
		
		super();
		
		this.newNetConnection = newNetConnection;
	}
}
