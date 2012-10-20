package nlr.ganymede.simulation;

import nlr.components.BasicComponent;
import nlr.ganymede.data.MinerData;
import nlr.ganymede.simulation.rendering.StructureRenderer;
import nlr.ganymede.simulation.structures.BasicMiner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;



public final class MinerRenderer extends BasicComponent {

	private MinerData minerData;
	private BasicMiner basicMiner;
	private StructureRenderer structureRenderer;
	
	private boolean wasMining;
	
	public MinerRenderer(
			long id, 
			MinerData minerData,
			BasicMiner basicMiner,
			StructureRenderer structureRenderer) {
		
		super(id);
		
		this.minerData = minerData;
		this.basicMiner = basicMiner;
		this.structureRenderer = structureRenderer;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		wasMining = this.basicMiner.isMining();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (wasMining != this.basicMiner.isMining()) {
			
			if (this.basicMiner.isMining()) {
				
				this.structureRenderer.setSpriteData(this.minerData.getSpriteDataMining());
			}
			else {
				
				this.structureRenderer.shouldResetSprite();
			}
			
			wasMining = this.basicMiner.isMining();
		}
	}
}
