package nlr.ganymede.simulation.structures;

import nlr.components.BasicComponent;
import nlr.ganymede.data.MinerData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class BasicMiner extends BasicComponent {
	
	private MinerData minerData;
	
	private ResourceManager resourceManager;
	private GanymedeMap map;
	
	private Affiliated affiliated;
	private Foundations foundations;
	private Utilities utilities;
	
	private int timeTillCollection;
	
	private boolean depleted;
	
	public strictfp boolean isMining() {
		
		return ((this.utilities.isFunctional()) && (!this.depleted));
	}
	
	public BasicMiner(
			long id, 
			MinerData minerData, 
			ResourceManager resourceManager, 
			GanymedeMap map, 
			Affiliated affiliated, 
			Foundations foundations, 
			Utilities utilities) {
		
		super(id);
		
		this.minerData = minerData;
		
		this.resourceManager = resourceManager;
		this.map = map;
		
		this.affiliated = affiliated;
		this.foundations = foundations;
		this.utilities = utilities;
		
		this.timeTillCollection = 0;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.timeTillCollection = this.minerData.getGatherInterval();
		
		this.depleted = false;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.isMining()) {
			
			if (this.timeTillCollection > 0) {
				
				this.timeTillCollection--;
			}
			else {
				
				this.collectResources();
				
				this.timeTillCollection = this.minerData.getGatherInterval();
			}
		}
	}
	
	private void collectResources() {
		
		int minX = this.foundations.getTopLeft().x;
		int minY = this.foundations.getTopLeft().y;
		
		int maxX = minX + this.foundations.getWidth();
		int maxY = minY + this.foundations.getLength();
		
		int ice = 0;
		
		boolean iceFound = true;
		
		while ((iceFound) && (ice < this.minerData.getIceRate())) {
			
			iceFound = false;
			
			for (int x = minX; x < maxX; x++) {
				
				for (int y = minY; y < maxY; y++) {
					
					if (ice < this.minerData.getIceRate()) {
						
						if (this.map.getIce(x, y) > 0) {
							
							this.map.mineIce(x, y);
							
							ice++;
							
							iceFound = true;
						}
					}
				}
			}
		}
		
		int minerals = 0;
		
		boolean mineralsFound = true;
		
		while ((mineralsFound) && (ice < this.minerData.getIceRate())) {
			
			mineralsFound = false;
			
			for (int x = minX; x < maxX; x++) {
				
				for (int y = minY; y < maxY; y++) {
					
					if (minerals < this.minerData.getMineralRate()) {
						
						if (this.map.getMinerals(x, y) > 0) {
							
							this.map.mineMinerals(x, y);
							
							minerals++;
							
							mineralsFound = true;
						}
					}
				}
			}
		}
		
		int metal = 0;
		
		boolean metalFound = true;
		
		while ((metalFound) && (ice < this.minerData.getIceRate())) {
			
			metalFound = false;
			
			for (int x = minX; x < maxX; x++) {
				
				for (int y = minY; y < maxY; y++) {
					
					if (metal < this.minerData.getMetalRate()) {
						
						if (this.map.getMetal(x, y) > 0) {
							
							this.map.mineMetal(x, y);
							
							metal++;
							
							metalFound = true;
						}
					}
				}
			}
		}
		if ((ice > 0) || (minerals > 0) || (metal > 0)) {
			
			this.resourceManager.depositResources(this.affiliated.getFaction(), ice, minerals, metal);
		}
		else {
			
			depleted = true;
		}
	}
}
