package nlr.ganymede.simulation.rendering;

import nlr.components.BasicComponentRenderable;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.PlayerService;
import nlr.ganymede.data.RotatingSpriteData;
import nlr.ganymede.data.UnitRendererData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.physics.Boid;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public strictfp final class UnitRenderer extends BasicComponentRenderable implements Renderer {
	
	private PlayerService playerService;
	private View view;
	
	private UnitRendererData unitRendererData;
	
	private Affiliated affiliated;
	private Boid boid;
	private Visibility visbility;
	
	private RotatingSpriteData spriteDataIdle;
	private RotatingSpriteData spriteDataMoving;
	
	private RotatingSprite sprite;
	
	private boolean shouldResetSprite;
	
	private boolean wasMoving;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.getDepthUnit(
				this.boid.getPosition().getX(), 
				this.boid.getPosition().getY(), 
				this.boid.getZ());
	}

	@Override
	public Rectangle getRendererBoundingShape() {
		
		return this.sprite.getBoundingBox();
	}

	public UnitRenderer(
			long id,
			PlayerService playerService, 
			View view, 
			UnitRendererData unitRendererData, 
			Affiliated affiliated, 
			Boid boid, 
			Visibility visbility) {
		
		super(id);
		
		this.playerService = playerService;
		this.view = view;
		
		this.unitRendererData = unitRendererData;
		
		this.affiliated = affiliated;
		this.boid = boid;
		this.visbility = visbility;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.spriteDataIdle = this.unitRendererData.getRotatingSpriteDataIdle();
		this.spriteDataMoving = this.unitRendererData.getRotatingSpriteDataMoving();
		
		this.sprite = new RotatingSprite(this.view);
		
		this.sprite.setFilter(GanymedeConstants.FACTION_COLORS[this.affiliated.getFaction()]);
		
		this.resetSprite();
		
		this.wasMoving = false;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		boolean isMoving = this.boid.isMoving();
		
		if (isMoving != this.wasMoving) {
			
			this.shouldResetSprite = true;
			
			this.wasMoving = isMoving;
		}
		
		if (this.shouldResetSprite) {
			
			this.resetSprite();
		}
		
		this.sprite.setPosition(this.boid.getPosition().getX(), this.boid.getPosition().getY());
		this.sprite.setAngle(this.boid.getRotation());
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.visbility.isVisible(this.playerService.getLocalPlayer().getFaction())) {
			
			this.sprite.render();
		}
	}
	
	public void setSpriteData(RotatingSpriteData spriteData) {
		
		this.sprite.setSpriteData(spriteData);
		
		this.shouldResetSprite = false;
	}
	
	public void shouldResetSprite() {
		
		this.shouldResetSprite = true;
	}
	
	private void resetSprite() {
		
		if (this.boid.isMoving()) {
			
			this.sprite.setSpriteData(this.spriteDataMoving);
		}
		else {
			
			this.sprite.setSpriteData(this.spriteDataIdle);
		}
		
		this.shouldResetSprite = false;
	}
}
