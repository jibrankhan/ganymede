package nlr.ganymede.simulation;

import java.util.Queue;

import nlr.components.ComponentManager;
import nlr.ganymede.GanymedeRules;
import nlr.ganymede.PlayerService;
import nlr.ganymede.command.Command;
import nlr.ganymede.command.CommandPerformer;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.fogOfWar.FogOfWar;
import nlr.ganymede.simulation.physics.BoidManager;
import nlr.ganymede.simulation.projectiles.ProjectileManager;
import nlr.ganymede.simulation.radar.RadarManager;
import nlr.ganymede.simulation.targeting.TargetManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public strictfp final class GanymedeSimulation {

	private PlayerService playerService;
	private DataService dataService;
	private View view;
	private GanymedeRules ganymedeRules;
	
	private ComponentManager componentManager;
	
	private FactionManager factionManager;
	private ResourceManager resourceManager;
	private UnlocksManager unlocksManager;
	private GanymedeMap map;
	private FogOfWar fogOfWar;
	private RadarManager radarManager;
	private PowerManager powerManager;
	private TargetManager targetManager;
	private ProjectileManager projectileManager;
	private BoidManager boidManager;
	private GanymedeLogic ganymedeLogic;
	
	private UnitFactory unitFactory;
	private CommandPerformer commandPerformer;

	public strictfp FactionManager getFactionManager() {
		return factionManager;
	}

	public strictfp ResourceManager getResourceManager() {
		return resourceManager;
	}

	public strictfp ComponentManager getComponentManager() {
		return componentManager;
	}

	public strictfp UnlocksManager getUnlocksManager() {
		return unlocksManager;
	}

	public strictfp GanymedeMap getGanymedeMap() {
		return map;
	}

	public strictfp FogOfWar getFogOfWar() {
		return fogOfWar;
	}
	
	public strictfp PowerManager getPowerManager(){
		return powerManager;
	}

	public strictfp BoidManager getPhysicsManager() {
		return boidManager;
	}

	public strictfp UnitFactory getUnitFactory() {
		return unitFactory;
	}

	public strictfp GanymedeLogic getGanymedeLogic() {
		return ganymedeLogic;
	}

	public GanymedeSimulation(
			PlayerService playerService,
			DataService dataService, 
			GanymedeRules ganymedeRules, 
			View view) throws SlickException {
		
		super();
		
		this.playerService = playerService;
		this.dataService = dataService;
		this.view = view;
		this.ganymedeRules = ganymedeRules;
		
		this.componentManager = new ComponentManager();

		this.factionManager = new FactionManager(this.componentManager.takeId(), this.dataService);
		this.resourceManager = new ResourceManager(this.componentManager.takeId());
		this.unlocksManager = new UnlocksManager(this.componentManager.takeId(), this.dataService);
		this.map = new GanymedeMap(this.componentManager.takeId(), this.dataService, this.view, new TiledMap(this.ganymedeRules.getMapReference()));
		this.fogOfWar = new FogOfWar(this.componentManager.takeId(), this.componentManager, this.view, this.map, this.playerService, this.dataService);
		this.radarManager = new RadarManager(this.componentManager.takeId(), this.playerService, this.componentManager);
		this.powerManager = new PowerManager(this.componentManager.takeId(), this.componentManager, this.map);
		this.targetManager = new TargetManager(this.componentManager.takeId(), this.map);
		this.projectileManager = new ProjectileManager(this.componentManager.takeId(), this.dataService, this.view, this.componentManager);
		this.boidManager = new BoidManager(this.componentManager.takeId(), this.componentManager, this.map);
		this.unitFactory = new UnitFactory(this.dataService, this.playerService, this.componentManager, this.factionManager, this.resourceManager, this.map, this.view, this.fogOfWar, this.powerManager, this.targetManager, this.projectileManager, this.unlocksManager);
		this.ganymedeLogic = new GanymedeLogic(this.componentManager.takeId(), this.factionManager, this.dataService, this.resourceManager, this.unitFactory, this.map, this.fogOfWar);
		
		this.commandPerformer = new CommandPerformer(this.dataService, this.componentManager, this.resourceManager, this.map, this.unitFactory);
	}
	
	public strictfp void init() throws SlickException {
		
		this.componentManager.init(null);
		
		this.componentManager.addComponent(this.factionManager);	
		this.componentManager.addComponent(this.resourceManager);
		this.componentManager.addComponent(this.unlocksManager);
		this.componentManager.addComponent(this.map);
		this.componentManager.addComponent(this.fogOfWar);
		this.componentManager.addComponent(this.radarManager);
		this.componentManager.addComponent(this.powerManager);
		this.componentManager.addComponent(this.targetManager);
		this.componentManager.addComponent(this.projectileManager);
		this.componentManager.addComponent(this.boidManager);
		this.componentManager.addComponent(this.ganymedeLogic);
	}
	
	public strictfp void step(GameContainer gameContainer, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
	}
	
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}

	public strictfp void perform(Queue<Command> commands) throws SlickException {
		
		this.commandPerformer.performCommands(commands);
	}
}
