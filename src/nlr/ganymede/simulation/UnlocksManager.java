package nlr.ganymede.simulation;

import java.util.ArrayList;

import nlr.components.BasicComponent;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.RequirementsData;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class UnlocksManager extends BasicComponent {
	
	private class IntegerList extends ArrayList<Integer> {
		
		private static final long serialVersionUID = 863590666940619406L;
	}
	
	private DataService dataService;
	
	private IntegerList[] entities;

	public UnlocksManager(long id, DataService dataService) {
		
		super(id);
		
		this.dataService = dataService;
		
		this.entities = new IntegerList[GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			this.entities[i] = new IntegerList();
		}
	}
	
	public void registerEntity(int faction, int entityId) {
		this.entities[faction].add(entityId);
	}
	
	public boolean hasEntity(int faction, int entityId) {
		return this.entities[faction].contains(entityId);
	}
	
	public boolean hasRequirements(int entityId, int faction) {
		
		try {
			
			RequirementsData requirementsData = this.dataService.getRequirementsData(entityId);
			
			for (int i : requirementsData.getRequirements()) {
				
				if (!this.hasEntity(faction, i)) {
					
					return false;
				}
			}
		}
		catch (SlickException e) {
			
			return false;
		}
		
		return true;
	}
}
