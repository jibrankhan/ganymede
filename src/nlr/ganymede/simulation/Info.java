package nlr.ganymede.simulation;

import nlr.components.BasicComponent;
import nlr.ganymede.data.InfoData;
import nlr.ganymede.data.TextData;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class Info extends BasicComponent implements Affiliated, Labelled {

	private InfoData infoData;
	
	private int faction;
	
	private UnlocksManager unlocksManager;
	
	@Override
	public strictfp int getFaction() {
		
		return faction;
	}
	
	@Override
	public strictfp int getEntityDataId() {
		
		return this.infoData.getId();
	}
	
	@Override
	public strictfp TextData getName() {
		
		return this.infoData.getName();
	}

	@Override
	public strictfp TextData getDescription() {
		
		return this.infoData.getDescription();
	}

	@Override
	public strictfp TextData getAcronym() {
		
		return this.infoData.getAcronym();
	}

	public Info(
			long id, 
			InfoData infoData, 
			int faction, 
			UnlocksManager unlocksManager) {
		
		super(id);
		
		this.infoData = infoData;
		this.faction = faction;
		this.unlocksManager = unlocksManager;
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.unlocksManager.registerEntity(this.faction, this.infoData.getId());
	}
}
