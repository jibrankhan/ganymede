package nlr.ganymede.simulation.health;

import nlr.components.BasicComponent;
import nlr.ganymede.data.HitPointsData;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class BasicHitPoints extends BasicComponent implements HitPoints {

	private HitPointsData hitPointsData;
	private nlr.components.Entity entity;
	
	private int hitPoints;
	
	@Override
	public int getHitPoints() {
		
		return this.hitPoints;
	}
	
	@Override
	public int getHitPointsMax() {
		
		return this.hitPointsData.getMax();
	}
	
	@Override
	public void setHitPoints(int hitPoints) {
		
		this.hitPoints = hitPoints;
		
		this.clamp();
	}
	
	@Override
	public float getHitPointsPercentage() {
		
		return (float) this.hitPoints / (float) this.hitPointsData.getMax();
	}

	public BasicHitPoints(long id, nlr.components.Entity entity, HitPointsData hitPointsData) {
		
		super(id);
		
		this.entity = entity;
		
		this.hitPointsData = hitPointsData;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.hitPoints = this.hitPointsData.getStart();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.hitPoints == 0) {
			
			this.destroy();
		}
		else {
			
			if (this.hitPointsData.getRecharge() != 0) {
				
				if (this.hitPoints < this.hitPointsData.getMax()) {
					
					this.adjust(this.hitPointsData.getRecharge());
				}
			}
		}
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
		
		this.entity.destroy();
	}
	
	@Override
	public void adjust(int amount) {
		
		this.hitPoints += amount;
		
		this.clamp();
	}
	
	private void clamp() {
		
		if (this.hitPoints < 0) {
			
			this.hitPoints = 0;
		}
		else if (this.hitPoints > this.hitPointsData.getMax()) {
			
			this.hitPoints = this.hitPointsData.getMax();
		}
	}
}
