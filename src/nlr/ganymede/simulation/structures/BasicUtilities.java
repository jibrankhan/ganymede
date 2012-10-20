package nlr.ganymede.simulation.structures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.data.UtilitiesData;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.health.HitPoints;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class BasicUtilities extends BasicComponent implements Utilities {
	
	private GanymedeMap map;
	
	private UtilitiesData utilitiesData;
	
	private Affiliated affiliated;
	private Foundations foundations;
	private HitPoints hitPoints;
	
	private boolean isBuilt;
	
	private int timeTillBuilt;
	private int hpBuildStep;
	
	private boolean isReceivingPower;
	
	private List<Point> tilesReach;
	
	@Override
	public int getFaction() {
		
		return affiliated.getFaction();
	}
	
	@Override
	public List<Point> getTiles() {
		
		return this.foundations.getTiles();
	}

	@Override
	public boolean isGround() {
		
		return this.foundations.isGround();
	}

	@Override
	public boolean isAir() {
		
		return this.foundations.isAir();
	}

	public boolean isBuilt() {
		
		return isBuilt;
	}
	
	public int getTimeTillBuilt() {
		
		if (this.isBuilt) {
			
			return 0;
		}
		else {
			
			return this.timeTillBuilt;
		}
	}
	
	@Override
	public strictfp float getBuildPercentage() {
		
		if (this.isBuilt) {
			
			return 1f;
		}
		else {
			
			return 1f - (this.timeTillBuilt / (float) this.utilitiesData.getBuildTime());
		}
	}
	
	public boolean isReceivingPower() {
		
		return isReceivingPower;
	}
	
	@Override
	public void setReceivingPower(boolean isReceivingPower) {
		
		this.isReceivingPower = isReceivingPower;
	}
	
	@Override
	public boolean isPowered() {
		
		return (((this.isBuilt) && (this.utilitiesData.isPowerGenerating())) || (this.isReceivingPower));
	}
	
	@Override
	public boolean isPowerRequired() {
		
		return this.utilitiesData.isPowerRequired();
	}
	
	@Override
	public boolean isFunctional() {
		
		if ((this.isAlive()) && (this.isBuilt)) {
			
			return this.isPowered() || (!this.utilitiesData.isPowerRequired());
		}
		else {
			
			return false;
		}
	}
	
	@Override
	public List<Point> getTilesReach() {
		return this.tilesReach;
	}

	public BasicUtilities(
			long id,
			GanymedeMap map, 
			UtilitiesData utilitiesData, 
			Affiliated affiliated, 
			Foundations foundations, 
			HitPoints hitPoints, 
			boolean isBuilt) {
		
		super(id);
		
		this.map = map;
		
		this.utilitiesData = utilitiesData;
		
		this.affiliated = affiliated;
		this.foundations = foundations;
		this.hitPoints = hitPoints;
		
		this.isBuilt = isBuilt;
		
		this.tilesReach = new ArrayList<Point>();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		if (this.isBuilt) {
			
			this.hitPoints.setHitPoints(this.hitPoints.getHitPointsMax());
		}
		else {
			
			this.timeTillBuilt = this.utilitiesData.getBuildTime();
			
			this.hpBuildStep = (this.hitPoints.getHitPointsMax() - this.hitPoints.getHitPoints() + 1) / this.utilitiesData.getBuildTime();
		}
		
		this.isReceivingPower = false;
		
		this.tilesReach.clear();
		
		for (Point p : this.foundations.getTiles()) {
			
			int minX = Math.max(0, p.x - this.utilitiesData.getPowerTransferRadius());
			int minY = Math.max(0, p.y - this.utilitiesData.getPowerTransferRadius());
	
			int maxX = Math.min(p.x + this.utilitiesData.getPowerTransferRadius() + 1, this.map.getWidthInTiles());
			int maxY = Math.min(p.y + this.utilitiesData.getPowerTransferRadius() + 1, this.map.getHeightInTiles());
			
			for (int x = minX; x < maxX; x++) {
				
				for (int y = minY; y < maxY; y++) {
					
					if (GanymedeMap.distanceSq(p.x, p.y, x, y) <= 
							this.utilitiesData.getPowerTransferRadius() * 
							this.utilitiesData.getPowerTransferRadius()) {
						
						Point q = new Point(x, y);
						
						if (!this.tilesReach.contains(q)) {
							
							this.tilesReach.add(q);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (!this.isBuilt) {
			
			if ((this.isReceivingPower) || (!this.utilitiesData.isPowerRequired())) {
				
				this.timeTillBuilt--;
				
				this.hitPoints.adjust(this.hpBuildStep);
				
				if (this.timeTillBuilt == 0) {
					
					this.isBuilt = true;
				}
			}
		}
	}
}
