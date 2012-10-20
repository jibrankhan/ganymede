package nlr.ganymede.simulation.radar;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import nlr.components.BasicComponentRenderable;
import nlr.components.ComponentManager;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.PlayerService;

public strictfp final class RadarManager extends BasicComponentRenderable {

	private PlayerService playerService;
	private ComponentManager componentManager;
	
	private boolean[] hasRadar;
	
	public RadarManager(long id, PlayerService playerService, ComponentManager componentManager) {
		
		super(id);
		
		this.playerService = playerService;
		this.componentManager = componentManager;
		
		this.hasRadar = new boolean[GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.updateRadar();
	}

	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.updateRadar();
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		int faction = this.playerService.getLocalPlayer().getFaction();
		
		if (this.hasRadar[faction]) {
			
			graphics.setColor(Color.red);
			
			List<BasicRadarVisible> basicRadarVisibles = this.componentManager.getComponents(BasicRadarVisible.class);
			
			for (BasicRadarVisible j : basicRadarVisibles) {
				
				if (j.getFaction() != faction) {
					
					graphics.draw(j.getRendererBoundingShape());
				}
			}
		}
	}

	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.DEPTH_RADAR;
	}
	
	private void updateRadar() {
		
		for (int i = 0; i < this.hasRadar.length; i++) {
			
			this.hasRadar[i] = false;
		}
		
		for (RadarProvider i : this.componentManager.getComponents(RadarProvider.class)) {
			
			if (!this.hasRadar[i.getFaction()]) {
				
				if (i.isFunctional()) {
					
					this.hasRadar[i.getFaction()] = true;
				}
			}
		}
	}
}
