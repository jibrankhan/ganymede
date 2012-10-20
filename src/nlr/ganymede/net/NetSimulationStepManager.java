package nlr.ganymede.net;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.Player;
import nlr.ganymede.SimulationStepService;
import nlr.ganymede.command.Command;
import nlr.ganymede.hud.Hud;
import nlr.ganymede.net.netMessages.NetMessageCommand;
import nlr.ganymede.net.netMessages.NetMessageTurnComplete;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.net.NetConnection;
import nlr.net.NetMessage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class NetSimulationStepManager implements SimulationStepService {
	
	private NetConnection netConnection;
	private NetPlayerManager netPlayerManager;
	
	private Map<Player, Queue<Command>> commandQueueSplit;
	private Queue<Command> commandQueue;
	
	private GanymedeSimulation ganymedeSimulation;
	private Hud hud;
	
	private int step;
	private boolean remoteReady;
	
	public NetSimulationStepManager(NetConnection netConnection, NetPlayerManager netPlayerManager) {
		
		super();
		
		this.netConnection = netConnection;
		this.netPlayerManager = netPlayerManager;
		
		this.commandQueueSplit = new ConcurrentHashMap<Player, Queue<Command>>();
		this.commandQueue = new ConcurrentLinkedQueue<Command>();
	}
	
	@Override
	public strictfp void init(GanymedeSimulation ganymedeSimulation, Hud hud) throws SlickException {
		
		this.ganymedeSimulation = ganymedeSimulation;
		this.hud = hud;
		
		this.step = 0;
		this.remoteReady = false;
		
		this.commandQueueSplit.clear();
		
		for (Player player : this.netPlayerManager.getPlayers()) {
			
			this.commandQueueSplit.put(player, new ConcurrentLinkedQueue<Command>());
		}
		
		this.commandQueue.clear();
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		if (this.step != GanymedeConstants.STEPS_PER_TURN) {
			
			this.ganymedeSimulation.step(gameContainer, delta);
			
			this.step++;
			
			if (this.step == GanymedeConstants.STEPS_PER_TURN) {
				
				try {
					
					this.netConnection.send(new NetMessageTurnComplete());
				} 
				catch (IOException e) {
					
					throw new SlickException(e.getMessage());
				}
				
				if (this.remoteReady) {
					
					this.nextTurn();
				}
			}
		}
		
		while (!this.netConnection.getNetMessages().isEmpty()) {
			
			NetMessage netMessage = this.netConnection.getNetMessages().remove();
			
			if (netMessage instanceof NetMessageTurnComplete) {
				
				if (this.step == GanymedeConstants.STEPS_PER_TURN) {
					
					this.nextTurn();
				}
				else {
					
					this.remoteReady = true;
				}
			}
			else if (netMessage instanceof NetMessageCommand) {
				
				Command command = ((NetMessageCommand) netMessage).getCommand();
				
				this.commandQueueSplit.get(this.netPlayerManager.getRemotePlayer()).add(command);
			}
		}
	}
	
	@Override
	public strictfp void sendCommand(Command command) throws SlickException {
		
		try {
			
			this.netConnection.send(new NetMessageCommand(command));
			
			this.commandQueueSplit.get(this.netPlayerManager.getLocalPlayer()).add(command);
		} 
		catch (IOException e) {
			
			throw new SlickException(e.getMessage());
		}
	}
	
	private strictfp void nextTurn() throws SlickException {
		
		this.groupCommands();
		
		this.hud.nextTurn();
		
		this.ganymedeSimulation.perform(this.commandQueue);
		
		this.step = 0;
		this.remoteReady = false;
	}
	
	private strictfp void groupCommands() {
		
		this.commandQueue.clear();
		
		for (Player i : this.commandQueueSplit.keySet()) {
			
			while (!this.commandQueueSplit.get(i).isEmpty()) {
				
				commandQueue.add(this.commandQueueSplit.get(i).remove());
			}
		}
	}
}
