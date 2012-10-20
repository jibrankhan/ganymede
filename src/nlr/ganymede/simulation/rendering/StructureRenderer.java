package nlr.ganymede.simulation.rendering;

import nlr.components.BasicComponentRenderable;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.PlayerService;
import nlr.ganymede.data.SpriteData;
import nlr.ganymede.data.StructureRendererData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.structures.Foundations;
import nlr.ganymede.simulation.structures.Utilities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public final class StructureRenderer extends BasicComponentRenderable implements Renderer {

	private PlayerService playerService;
	
	private StructureRendererData structureRendererData;
	
	private Affiliated affiliated;
	private Foundations foundations;
	private Utilities utilities;
	private Visibility tiledVisibility;
	
	private View view;
	private GanymedeMap map;
	
	private SpriteData spriteDataIdle;
	private SpriteData spriteDataBuilding;
	
	private Sprite sprite;
	
	private boolean shouldResetSprite;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.getDepthUnit(
				this.foundations.getBoundingShape().getMinX(), 
				this.foundations.getBoundingShape().getMaxY(), 
				0f);
	}
	
	public Rectangle getRendererBoundingShape() {
		
		return this.sprite.getBoundingBox();
	}

	public StructureRenderer(
			long id,
			PlayerService playerService, 
			StructureRendererData structureRendererData,
			Affiliated affiliated, 
			Foundations foundations, 
			Utilities utilities, 
			Visibility visibility, 
			View view, 
			GanymedeMap map) {
		
		super(id);
		
		this.playerService = playerService;
		
		this.structureRendererData = structureRendererData;
		
		this.affiliated = affiliated;
		this.foundations = foundations;
		this.utilities = utilities;
		this.tiledVisibility = visibility;
		
		this.view = view;
		this.map = map;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.spriteDataIdle = this.structureRendererData.getSpriteData();
		this.spriteDataBuilding = this.structureRendererData.getSpriteDataBuilding();
		
		this.sprite = new Sprite(this.view);
		
		this.sprite.setFilter(GanymedeConstants.FACTION_COLORS[this.affiliated.getFaction()]);
		this.sprite.setLooping(false);
		this.sprite.setPosition(this.map.getTilePosition(this.foundations.getTopLeft()));
		
		this.resetSprite();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.shouldResetSprite) {
			
			this.resetSprite();
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.tiledVisibility.isVisible(this.playerService.getLocalPlayer().getFaction())) {
			
			this.sprite.render();
		}
	}
	
	public void setSpriteData(SpriteData spriteData) {
		
		this.sprite.setSpriteData(spriteData);
		
		this.shouldResetSprite = false;
	}
	
	public void shouldResetSprite() {
		
		this.shouldResetSprite = true;
	}
	
	private void resetSprite() {
		
		if (this.utilities.isBuilt()) {
			
			this.sprite.setSpriteData(this.spriteDataIdle);
		}
		else {
			
			this.sprite.setSpriteData(this.spriteDataBuilding);
		}
		
		this.shouldResetSprite = false;
	}
}
