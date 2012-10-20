package nlr.ganymede;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nlr.ganymede.command.Command;
import nlr.ganymede.hud.Hud;
import nlr.ganymede.simulation.GanymedeSimulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class DummySimulationStepManager implements SimulationStepService {

	private Queue<Command> commands;
	
	private GanymedeSimulation ganymedeSimulation;
	private Hud hud;
	
	public DummySimulationStepManager() {
		
		super();
		
		this.commands = new ConcurrentLinkedQueue<Command>();
	}

	@Override
	public strictfp void init(GanymedeSimulation ganymedeSimulation, Hud hud) throws SlickException {
		
		this.ganymedeSimulation = ganymedeSimulation;
		this.hud = hud;
		
		this.commands.clear();
	}

	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		this.ganymedeSimulation.step(gameContainer, delta);
		
		this.hud.nextTurn();
		
		this.ganymedeSimulation.perform(this.commands);
	}
	
	@Override
	public strictfp void sendCommand(Command command) throws SlickException {
		
		this.commands.add(command);
	}
}
