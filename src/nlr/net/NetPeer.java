package nlr.net;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

public final class NetPeer extends AbstractExecutionThreadService {
	
	private NetPeerConfig netPeerConfig;
	
	private Queue<NetPeerState> netPeerStates;
	
	private boolean keepRunning;
	
	private NetPeerServer netPeerServer;
	
	private HashSet<NetConnection> netConnections;
	
	public NetPeerConfig getNetPeerConfig() {
		return netPeerConfig;
	}
	
	public Queue<NetPeerState> getNetPeerStates() {
		return netPeerStates;
	}

	public boolean isSpaceAvailable() {
		return (this.netConnections.size() < this.netPeerConfig.getMaxConnections());
	}

	public NetPeer(NetPeerConfig netPeerConfig) {
		
		super();
		
		this.netPeerConfig = netPeerConfig;
		
		this.netPeerStates = new ConcurrentLinkedQueue<NetPeerState>();
		
		this.netConnections = new HashSet<NetConnection>(this.netPeerConfig.getMaxConnections());
	}
	
	@Override
	protected void startUp() throws Exception {
		
		super.startUp();
		
		this.netPeerStates.clear();
		
		this.keepRunning = true;
		
		this.netPeerServer = new NetPeerServer(
				this.netPeerConfig.getLocalPort(), 
				this.netPeerConfig.getServerSocketTimeout(), 
				this.netPeerConfig.isAcceptIncomingConnections());
		
		this.netPeerServer.startAndWait();
		
		this.netPeerStates.add(new NetPeerStateStarted());
	}

	@Override
	protected void run() {
		while (this.keepRunning) {
			
			// Handle new connections
			while (!this.netPeerServer.getSockets().isEmpty()) {
				NetConnection netConnection = new NetConnection(this.netPeerServer.getSockets().remove());
				
				netConnection.startAndWait();
				
				this.netConnections.add(netConnection);
				this.netPeerStates.add(new NetPeerStateConnectionMade(netConnection));
			}
			
			// Handle new messages
			for (NetConnection i : this.netConnections) {
				while (!i.getNetMessages().isEmpty()) {
					this.netPeerStates.add(new NetPeerStateMessageReceived(
							i, 
							i.getNetMessages().remove()));
				}
			}
		}
	}
	
	@Override
	protected void triggerShutdown() {
		
		super.triggerShutdown();
		
		this.keepRunning = false;
	}
	
	@Override
	protected void shutDown() throws Exception {
		
		super.shutDown();
		
		this.netPeerStates.clear();
		
		this.netPeerServer.stopAndWait();
		
		for (NetConnection i : this.netConnections) {
			i.stopAndWait();
		}
		
		this.netConnections.clear();
	}
	
	public void connect(SocketAddress hostSocketAddress, int timeout) throws IOException {
		
		Socket socket = new Socket();
		
		socket.connect(hostSocketAddress, timeout);
		
		NetConnection netConnection = new NetConnection(socket);
		
		netConnection.startAndWait();
		
		this.netConnections.add(netConnection);
		this.netPeerStates.add(new NetPeerStateConnectionMade(netConnection));
	}
	
	public void sendAll(NetMessage netMessage) throws IOException {
		for (NetConnection i : this.netConnections) {
			i.send(netMessage);
		}
	}
}
