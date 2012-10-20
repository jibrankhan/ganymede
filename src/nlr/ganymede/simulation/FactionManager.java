package nlr.ganymede.simulation;


import java.util.Collection;
import java.util.HashMap;

import nlr.components.BasicComponent;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.RaceData;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public class FactionManager extends BasicComponent {
	
	private DataService dataService;
	
	private HashMap<Integer, Integer> factionRaceIds;
	
	private int[] factionValues;
	private int[] factionSupply;
	
	public Collection<Integer> getFactions() {
		return this.factionRaceIds.keySet();
	}
	
	public FactionManager(long id, DataService dataService) {
		
		super(id);
		
		this.dataService = dataService;
		
		this.factionRaceIds = new HashMap<Integer, Integer>();
		
		this.factionValues = new int[GanymedeConstants.MAX_FACTION_COUNT];
		this.factionSupply = new int[GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			this.factionRaceIds.put(i, 1); // TODO: Actual race selection
		}
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			this.factionValues[i] = 0;
			this.factionSupply[i] = 0;
		}
	}
	
	public int getRaceId(int faction) {
		
		return this.factionRaceIds.get(faction);
	}
	
	public RaceData getRaceData(int faction) {
		
		return this.dataService.getRaceData(this.getRaceId(faction));
	}
	
	public int getValue(int faction) {
		
		return this.factionValues[faction];
	}
	
	public void adjustValue(int faction, int amount) {
		
		this.factionValues[faction] += amount;
	}
	
	public int getSupply(int faction) {
		
		return this.factionSupply[faction];
	}
	
	public void adjustSupply(int faction, int amount) {
		
		this.factionSupply[faction] += amount;
	}
}
