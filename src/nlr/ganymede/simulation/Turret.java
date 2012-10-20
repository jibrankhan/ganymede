package nlr.ganymede.simulation;

import nlr.components.BasicComponentRenderable;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.PlayerService;
import nlr.ganymede.data.TurretData;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.physics.Boid;
import nlr.ganymede.simulation.rendering.RotatingSprite;
import nlr.ganymede.simulation.targeting.FiringPlatform;
import nlr.utils.Utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class Turret extends BasicComponentRenderable implements FiringPlatform {

	private TurretData turretData;

	private PlayerService playerService;
	private View view;
	
	private Affiliated affiliated;
	private Boid boid;
	private Visibility visibility;
	
	private float rotation;
	private float turning;
	
	private RotatingSprite sprite;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.getDepthUnit(
				this.getPosition().getX(), 
				this.getPosition().getY(), 
				this.getZ());
	}
	
	@Override
	public boolean isGround() {
		return this.boid.isGround();
	}

	@Override
	public boolean isAir() {
		return this.boid.isAir();
	}

	@Override
	public Vector2f getPosition() {
		return this.boid.getPosition();
	}
	
	@Override
	public float getZ() {
		return this.boid.getZ() + 1f;
	}
	
	@Override
	public boolean isMoving() {
		return this.boid.isMoving();
	}
	
	@Override
	public Vector2f getVelocity() {
		return this.boid.getVelocity();
	}

	@Override
	public float getRotation() {
		return this.rotation;
	}
	
	@Override
	public boolean isSteady() {
		return true;
	}
	
	public Turret(
			long id, 
			TurretData turretData, 
			PlayerService playerService, 
			View view, 
			Affiliated affiliated, 
			Boid boid, 
			Visibility visibility) {
		
		super(id);
		
		this.turretData = turretData;
		
		this.playerService = playerService;
		this.view = view;
		
		this.affiliated = affiliated;
		this.boid = boid;
		this.visibility = visibility;
	}	
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.rotation = 0f;
		this.turning = 0f;
		
		this.sprite = new RotatingSprite(this.view, this.turretData.getRotatingSpriteData());
		
		this.sprite.setFilter(GanymedeConstants.FACTION_COLORS[this.affiliated.getFaction()]);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.turning != 0) {
			
			float l = Math.abs(this.turning);
			
			if (l > this.turretData.getTurnSpeed()) {
				
				this.turning *= this.turretData.getTurnSpeed() / l;
			}
			
			this.rotation += this.turning;
			
			this.rotation = Utils.wrapAngle(this.rotation);
			this.turning = 0f;
			
			this.sprite.setAngle(this.rotation);
		}
		
		this.sprite.setPosition(this.getPosition().getX(), this.getPosition().getY());
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.visibility.isVisible(this.playerService.getLocalPlayer().getFaction())) {
			
			this.sprite.render();
		}
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
	}
	
	@Override
	public void turn(float turning) {
		
		this.turning += turning;
	}

	@Override
	public Vector2f getPositionPrevious() {
		
		return this.boid.getPositionPrevious();
	}

	@Override
	public float getSpeed() {
		
		return this.boid.getSpeed();
	}

	@Override
	public float getSpeedSquared() {
		
		return this.boid.getSpeedSquared();
	}

	@Override
	public boolean hasMoved() {
		
		return this.boid.hasMoved();
	}
}
