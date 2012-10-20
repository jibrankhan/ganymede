package nlr.ganymede;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.ws.ProtocolException;

import nlr.ganymede.net.NetPlayerManager;
import nlr.ganymede.net.NetSimulationStepManager;
import nlr.ganymede.net.netMessages.NetMessageClientInfo;
import nlr.ganymede.net.netMessages.NetMessageHostInfo;
import nlr.net.NetConnection;
import nlr.net.NetMessage;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public final class LauncherHost {
	
	private int screenWidth;
	private int screenHeight;
	private boolean fullScreenMode;
	private boolean vSync; 
	private int localPort; 
	private String handle;
	private String map;
	
	private LauncherHost(
			int screenWidth, 
			int screenHeight,
			boolean fullScreenMode, 
			boolean vSync, 
			int localPort,
			String handle, 
			String map) {
		
		super();
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.fullScreenMode = fullScreenMode;
		this.vSync = vSync;
		this.localPort = localPort;
		this.handle = handle;
		this.map = map;
	}

	private void launch() throws SlickException, IOException {
		
		System.out.println("Launching... ");
		
		// Create a server socket
		ServerSocket serverSocket = new ServerSocket(this.localPort);
		
		// Wait for client
		System.out.println("Waiting for client... ");
		
		Socket socket = serverSocket.accept();
		
		System.out.println("Client found. ");
		
		// Establish a connection
		NetConnection netConnection = new NetConnection(socket);
		
		System.out.println("Establishing connection... ");
		
		netConnection.startAndWait();
		
		System.out.println("Connection made. ");
		
		// Send host info
		netConnection.send(new NetMessageHostInfo(this.handle, this.map));
		
		System.out.println("Sent host info. ");
		
		// Receive client info
		System.out.println("Waiting for client info... ");
		
		String clientHandle = "???";
		
		boolean waitingForClientInfo = true;
		
		while (waitingForClientInfo) {
			while (!netConnection.getNetMessages().isEmpty()) {
				NetMessage i = netConnection.getNetMessages().remove();
				
				if (i instanceof NetMessageClientInfo) {
					clientHandle = ((NetMessageClientInfo)i).getClientHandle();
					
					waitingForClientInfo = false;
					
					System.out.println("Client info received. ");
				}
				else {
					throw new ProtocolException("Expected client info. ");
				}
			}
			
			if (waitingForClientInfo) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Launch
		System.out.println("Launching Ganymede... ");
		System.out.println();
		
		NetPlayerManager netPlayerManager = new NetPlayerManager(
				new Player(0, 0, this.handle), 
				new Player(1, 1, clientHandle), 
				true);
		
		NetSimulationStepManager netSimulationStepManager = new NetSimulationStepManager(netConnection, netPlayerManager);
		
		GanymedeRules ganymedeRules = new GanymedeRules(map);
		
		GanymedeGame ganymedeGame = new GanymedeGame(netPlayerManager, netSimulationStepManager, ganymedeRules);
		
		AppGameContainer appGameContainer = new AppGameContainer(ganymedeGame);
		
		appGameContainer.setDisplayMode(this.screenWidth, this.screenHeight, this.fullScreenMode);
		appGameContainer.setTargetFrameRate(60);
		appGameContainer.setVSync(this.vSync);
		appGameContainer.setUpdateOnlyWhenVisible(false);
		appGameContainer.setAlwaysRender(true);
		
		appGameContainer.start();
		
		// Close the connection
		System.out.println("Closing the connection... ");
		
		netConnection.stopAndWait();
		
		System.out.println("Connection closed. ");
	}
	
	public static void main(String[] args) {
		
		// About
		System.out.println("Ganymede Alpha 1.0");
		System.out.println("Host mode");
		System.out.println();
		
		// Linking
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		// Check args
		if (args.length != 7) {
			
			// Instructions
			System.out.println("0 - screen width");
			System.out.println("1 - screen height");
			System.out.println("2 - full screen mode (1 = yes, 0 = no)");
			System.out.println("3 - v sync (1 = yes, 0 = no)");
			System.out.println("4 - local port");
			System.out.println("5 - handle");
			System.out.println("6 - map");
			System.out.println();
		}
		else {	
			try {
				// Get parameters
				int width = Integer.parseInt(args[0]);
				
				if (width < 640) { 
					throw new IOException("Width must be no less than 640");
				}
				
				int height = Integer.parseInt(args[1]);
				
				if (height < 480) { 
					throw new IOException("Height must be no less than 480");
				}
				
				int i = (Integer.parseInt(args[2]));
				
				boolean fullScreenMode;
				
				if (i == 0) {
					fullScreenMode = false;
				}
				else if (i == 1) {
					fullScreenMode = true;
				}
				else {
					throw new IOException("Full screen mode must be set to 1 or 0");
				}
				
				int j = (Integer.parseInt(args[3]));
				
				boolean vSync;
				
				if (j == 0) {
					vSync = false;
				}
				else if (j == 1) {
					vSync = true;
				}
				else {
					throw new IOException("V sync must be set to 1 or 0");
				}
				
				int localPort = Integer.parseInt(args[4]);
				
				String handle = args[5];
				
				String map = args[6];
				
				// Launch
				LauncherHost launcherHost = new LauncherHost(width, height, fullScreenMode, vSync, localPort, handle, map);
				
				launcherHost.launch();
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
