package nlr.net;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

public final class NetConnector implements Callable<Socket> {

	private SocketAddress hostSocketAddress;
	private int timeout;
	
	public NetConnector(SocketAddress hostSocketAddress, int timeout) {
		
		super();
		
		this.hostSocketAddress = hostSocketAddress;
		this.timeout = timeout;
	}

	@Override
	public Socket call() throws Exception {
		
		Socket socket = new Socket();
		
		socket.connect(this.hostSocketAddress, this.timeout);
		
		return socket;
	}
}
