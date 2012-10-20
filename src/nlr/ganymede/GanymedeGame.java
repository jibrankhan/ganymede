package nlr.ganymede;


import nlr.ganymede.data.DataManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GanymedeGame extends StateBasedGame {
	
	private PlayerService playerService;
	private SimulationStepService simulationStepService;
	private GanymedeRules ganymedeRules;
	
	private DataManager dataManager;
	
	public GanymedeGame(PlayerService playerService, SimulationStepService simulationStepService, GanymedeRules ganymedeRules) {
		
		super("Ganymede");
		
		this.playerService = playerService;
		this.simulationStepService = simulationStepService;
		this.ganymedeRules = ganymedeRules;
		
		this.dataManager = new DataManager();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		
		this.addState(new GameStateMainMenu());
		
		this.addState(
				new GameStateLoading(
						this.playerService, 
						this.simulationStepService, 
						this.dataManager));
		
		this.addState(
				new GameStatePlaying(
						this.playerService, 
						this.simulationStepService, 
						this.dataManager, 
						this.ganymedeRules));
		
		this.enterState(GameStateIds.GAME_STATE_LOADING_ID);
		//this.enterState(GameStateIds.GAME_STATE_MAIN_MENU_ID);
	}
}
