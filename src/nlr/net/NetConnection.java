package nlr.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.ws.ProtocolException;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

/**
 * A Connection class provides a simple wrapper around a TCP socket. 
 * 
 * Messages are continually read in a separate thread and queued for
 * processing. 
 * 
 * Depends on Google Guava AbstractExecutionThreadService. 
 * 
 * @author nicklarooy
 *
 */
public final class NetConnection extends AbstractExecutionThreadService {

	private Socket socket;
	
	private boolean keepRunning;
	
	private Queue<NetMessage> netMessages;
	
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	public Queue<NetMessage> getNetMessages() {
		return netMessages;
	}

	public NetConnection(Socket socket) {
		
		super();
		
		this.socket = socket;
		
		this.keepRunning = false;
		this.netMessages = new ConcurrentLinkedQueue<NetMessage>();
	}
	
	@Override
	protected void startUp() throws Exception {
		
		super.startUp();
		
		this.keepRunning = true;
		
		this.netMessages.clear();
		
		this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
		this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
	}
	
	@Override
	protected void run() {
		
		while (this.keepRunning) {
			
			try {
				Object object = this.objectInputStream.readObject();
				
				if (object instanceof NetMessage) {
					this.netMessages.add((NetMessage)object);
				}
				else {
					throw new ProtocolException("Object received is not a NetMessage! ");
				}
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				
				this.triggerShutdown();
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
		
		this.netMessages.clear();
		
		this.objectOutputStream.close();
		this.objectInputStream.close();
		
		this.socket.close();
	}
	
	public void send(NetMessage netMessage) throws IOException {
		if (this.isRunning()) {
			this.objectOutputStream.writeObject(netMessage);
		}
	}
}
