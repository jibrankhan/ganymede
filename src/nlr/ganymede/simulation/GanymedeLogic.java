package nlr.ganymede.simulation;

import java.util.Collection;
import java.util.HashSet;

import nlr.components.BasicComponent;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.fogOfWar.FogOfWar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class GanymedeLogic extends BasicComponent {

	private FactionManager factionManager;
	private DataService dataService;
	private ResourceManager resourceManager;
	private UnitFactory unitFactory;
	private GanymedeMap ganymedeMap;
	private FogOfWar fogOfWar;
	
	private boolean isGameOver;
	
	private HashSet<Integer> winners;
	
	public strictfp boolean isGameOver() {
		
		return isGameOver;
	}
	
	public strictfp Collection<Integer> getWinners() {
		
		return this.winners;
	}
	
	public GanymedeLogic(
			long id, 
			FactionManager factionManager, 
			DataService dataService,
			ResourceManager resourceManager,
			UnitFactory unitFactory, 
			GanymedeMap ganymedeMap, 
			FogOfWar fogOfWar) {
		
		super(id);
		
		this.factionManager = factionManager;
		this.dataService = dataService;
		this.resourceManager = resourceManager;
		this.unitFactory = unitFactory;
		this.ganymedeMap = ganymedeMap;
		this.fogOfWar = fogOfWar;
		
		this.isGameOver = false;
		
		this.winners = new HashSet<Integer>(GanymedeConstants.MAX_FACTION_COUNT);
	}

	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		// For each faction in use... 
		for (int i : this.factionManager.getFactions()) { 
			
			// Unlock race
			this.unitFactory.createEntity(this.factionManager.getRaceId(i), i, 0, 0, true);
			
			// Reveal map
			this.fogOfWar.revealMap(i);
			
			// Deposit starting resources
			this.resourceManager.depositResources(i, 200, 800, 50);	
			
			// Create starting structure
			this.unitFactory.createEntity(
					this.dataService.getRaceData(this.factionManager.getRaceId(i)).getIdHq(), 
					i, 
					this.ganymedeMap.getStartingPosition(i).x, 
					this.ganymedeMap.getStartingPosition(i).y, 
					true);
		}
		
		this.isGameOver = false;
		
		this.winners.clear();
	}
	
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (!this.isGameOver) {
			
			for (int i : this.factionManager.getFactions()) {
				
				if (this.factionManager.getValue(i) <= 0) {
					
					this.isGameOver = true;
					
					break;
				}
			}
			
			if (this.isGameOver) {
				
				for (int i : this.factionManager.getFactions()) {
					
					if (this.factionManager.getValue(i) > 0) {
						
						this.winners.add(i);
					}
				}
			}
		}
	}
}
