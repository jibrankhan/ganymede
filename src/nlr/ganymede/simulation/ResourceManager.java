package nlr.ganymede.simulation;


import nlr.components.BasicComponent;
import nlr.ganymede.GanymedeConstants;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public strictfp final class ResourceManager extends BasicComponent {
	
	private Resources[] resources;
	
	public ResourceManager(long id) {
		
		super(id);
		
		this.resources = new Resources[GanymedeConstants.MAX_FACTION_COUNT];
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			this.resources[i] = new Resources();
		}
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			this.resources[i].zero();
		}
	}
	
	public Resources getResources(int faction) {
		return this.resources[faction].copy();
	}
	
	public boolean hasResources(int faction, int ice, int minerals, int metal) {
		
		Resources resources = this.resources[faction];
		
		if (resources.getIce() < ice) {
			
			return false;
		}
		
		if (resources.getMinerals() < minerals) {
			
			return false;
		}
		
		if (resources.getMetal() < metal) {
			
			return false;
		}
		
		return true;
	}
	
	public void spendResources(int faction, int ice, int minerals, int metal) throws SlickException {
		
		if (hasResources(faction, ice, minerals, metal)) {
			
			this.resources[faction].sub(ice, minerals, metal);
		}
		else {
			
			throw new SlickException("Faction " + Integer.toString(faction) + " does not have enough resources! ");
		}
	}
	
	public void spendResources(int faction, Resources resources) throws SlickException {
		
		this.spendResources(faction, resources.getIce(), resources.getMinerals(), resources.getMetal());
	}
	
	public void depositResources(int faction, int ice, int minerals, int metal) {
		
		this.resources[faction].add(ice, minerals, metal);
	}
	
	public void depositResources(int faction, Resources resources) {
		
		this.resources[faction].add(resources);
	}
}
