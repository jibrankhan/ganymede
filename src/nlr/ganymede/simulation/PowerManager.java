package nlr.ganymede.simulation;

import java.awt.Point;


import nlr.components.BasicComponent;
import nlr.components.ComponentManager;
import nlr.ganymede.GanymedeConstants;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public strictfp final class PowerManager extends BasicComponent {

	private ComponentManager componentManager;
	private GanymedeMap map;
	
	private boolean[][][] power;
	
	public PowerManager(long id, ComponentManager componentManager, GanymedeMap map) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.map = map;
		
		this.power = new boolean[this.map.getWidthInTiles()][this.map.getHeightInTiles()][GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
					this.power[x][y][i] = false;
				}
			}
		}
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
					this.power[x][y][i] = false;
				}
			}
		}
		
		boolean change = true;
		
		while (change) {
			
			change = false;
			
			for (PowerTransfer i : this.componentManager.getComponents(PowerTransfer.class)) {
				
				if ((i.isFunctional()) && (i.isPowered())) {
					
					for (Point p : i.getTilesReach()) {
						
						if (!this.power[p.x][p.y][i.getFaction()]) {
							
							this.power[p.x][p.y][i.getFaction()] = true;
							
							change = true;
						}
					}
				}
			}
			
			for (PowerReceiver i : this.componentManager.getComponents(PowerReceiver.class)) {
				
				boolean b = false;
				
				for (Point p : i.getTiles()) {
					
					if (this.isPowered(p.x, p.y, i.getFaction())) {
						
						b = true;
						
						break;
					}
				}
				
				i.setReceivingPower(b);
			}
		}
	}
	
	public boolean isPowered(int x, int y, int faction) {
		return this.power[x][y][faction];
	}
}
