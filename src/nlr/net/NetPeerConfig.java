package nlr.net;

public final class NetPeerConfig {
	
	private int localPort;
	private int maxConnections;
	private int serverSocketTimeout;

	private boolean acceptIncomingConnections;

	public int getLocalPort() {
		return localPort;
	}

	public int getMaxConnections() {
		return maxConnections;
	}
	
	public int getServerSocketTimeout() {
		return serverSocketTimeout;
	}

	public boolean isAcceptIncomingConnections() {
		return acceptIncomingConnections;
	}

	public NetPeerConfig(
			int localPort, 
			int maxConnections,
			int serverSocketTimeout, 
			boolean acceptIncomingConnections) {
		
		super();
		
		this.localPort = localPort;
		this.maxConnections = maxConnections;
		this.serverSocketTimeout = serverSocketTimeout;
		
		this.acceptIncomingConnections = acceptIncomingConnections;
	}
}
