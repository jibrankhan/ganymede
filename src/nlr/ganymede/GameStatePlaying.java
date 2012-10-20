package nlr.ganymede;

// import ganymede.hud.MiniMap;

import nlr.ganymede.data.DataService;
import nlr.ganymede.hud.Hud;
import nlr.ganymede.hud.OrderTracker;
import nlr.ganymede.hud.SelectionManager;
import nlr.ganymede.hud.StructurePlacer;
import nlr.ganymede.hud.ViewController;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.ganymede.simulation.View;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public strictfp final class GameStatePlaying extends BasicGameState {
	
	private PlayerService playerService;
	private SimulationStepService simulationStepService;
	private DataService dataService;
	private GanymedeRules ganymedeRules;
	
	private View view;
	private GanymedeSimulation ganymedeSimulation;
	private ViewController viewController;
	private Hud hud;
	private SelectionManager selectionManager;
	// private MiniMap miniMap;
	private OrderTracker orderTracker;
	private StructurePlacer structurePlacer;
	
	public GameStatePlaying(
			PlayerService playerService, 
			SimulationStepService simulationStepService, 
			DataService dataService, 
			GanymedeRules ganymedeRules) {
		
		this.playerService = playerService;
		this.simulationStepService = simulationStepService;
		this.dataService = dataService;
		this.ganymedeRules = ganymedeRules;
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		this.view = new View(gameContainer.getWidth(), gameContainer.getHeight());
		this.ganymedeSimulation = new GanymedeSimulation(this.playerService, this.dataService, this.ganymedeRules, this.view);
		this.viewController = new ViewController(this.view, this.ganymedeSimulation.getGanymedeMap(), gameContainer.getInput());
		this.selectionManager = new SelectionManager(this.playerService, this.ganymedeSimulation, this.view, gameContainer.getInput());
		this.hud = new Hud(this.playerService, this.view, this.ganymedeSimulation, this.selectionManager);
		// this.miniMap = new MiniMap(this.playerService, this.ganymedeSimulation, this.view, this.viewController, this.selectionManager, gameContainer.getInput(), GanymedeConstants.SCREEN_EDGE_HOT_WIDTH, 320);
		this.orderTracker = new OrderTracker(this.dataService, this.playerService, this.simulationStepService, this.view, gameContainer.getInput(), this.selectionManager, this.ganymedeSimulation, this.hud);
		this.structurePlacer = new StructurePlacer(this.dataService, this.playerService, this.simulationStepService, view, this.ganymedeSimulation, this.hud, gameContainer.getInput());
	}
	
	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		this.ganymedeSimulation.init();
		this.hud.init(gameContainer);
		// this.miniMap.init();
		this.orderTracker.init();
		this.simulationStepService.init(this.ganymedeSimulation, this.hud);
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		this.viewController.update();
		this.simulationStepService.update(gameContainer, delta);
		this.hud.update(gameContainer, delta);
		this.selectionManager.update();
		// this.miniMap.update();
	}
	
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		
		this.ganymedeSimulation.render(null, graphics);
		
		this.hud.render(null, graphics);
		// this.miniMap.render(graphics);
		this.structurePlacer.render(graphics);
		this.selectionManager.render(graphics);
	}

	@Override
	public int getID() {
		return GameStateIds.GAME_STATE_PLAYING_ID;
	}
}
