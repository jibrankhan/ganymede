package nlr.ganymede;

import nlr.ganymede.hud.Hud;
import nlr.ganymede.simulation.GanymedeSimulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp interface SimulationStepService extends CommandSender {
	
	void init(GanymedeSimulation ganymedeSimulation, Hud hud) throws SlickException;
	
	void update(GameContainer gameContainer, int delta) throws SlickException;
}
