package nlr.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

public final class NetPeerServer extends AbstractExecutionThreadService {

	private int localPort;
	private int timeout;
	private boolean acceptConnections;
	
	private boolean keepRunning;
	
	private ServerSocket serverSocket;
	
	private Queue<Socket> sockets;
	
	public boolean isAcceptConnections() {
		return acceptConnections;
	}

	public void setAcceptConnections(boolean acceptConnections) {
		this.acceptConnections = acceptConnections;
	}

	public Queue<Socket> getSockets() {
		return this.sockets;
	}
	
	public NetPeerServer(int localPort, int timeout, boolean acceptConnections) {
		
		super();
		
		this.localPort = localPort;
		this.timeout = timeout;
		this.acceptConnections = acceptConnections;
		
		this.sockets = new ConcurrentLinkedQueue<Socket>();
	}
	
	@Override
	protected void startUp() throws Exception {
		
		super.startUp();
		
		this.serverSocket = new ServerSocket(this.localPort);
		
		this.serverSocket.setSoTimeout(this.timeout);
		
		this.sockets.clear();
		
		this.keepRunning = true;
	}
	
	@Override
	protected void run() {
		
		while (this.keepRunning) {
			if (this.acceptConnections) {
				try {
					this.sockets.add(this.serverSocket.accept());
				} catch (SocketTimeoutException e) {
					// Just carry on... 
				} catch (IOException e) {
					e.printStackTrace();
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
		
		this.serverSocket.close();
		
		this.sockets.clear();
	}
}
