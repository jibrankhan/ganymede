package nlr.ganymede.simulation.projectiles;

import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.rendering.RotatingSprite;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class Bullet extends BasicProjectile {
	
	private DataService dataService;
	private View view;
	
	private Vector2f direction;
	
	private float z;
	private float range;
	
	private float speed;
	
	private RotatingSprite sprite;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.getDepthUnit(this.getPosition().getX(), this.getPosition().getY(), this.z);
	}
	
	public Bullet(
			long id, 
			Vector2f initialPosition, 
			int faction, 
			int damagePierce,
			int damageHeat, 
			int damageImpact, 
			boolean isEmp, 
			boolean isGround,
			boolean isAir, 
			DataService dataService, 
			View view,
			Vector2f direction, 
			float z, 
			float range) {
		
		super(id, initialPosition, 1f, 0f, faction, damagePierce, damageHeat, damageImpact, isEmp, isGround, isAir);
		
		this.dataService = dataService;
		this.view = view;
		
		this.direction = direction;
		
		this.z = z;
		this.range = range;
	}



	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.getVelocity().set(this.direction.normalise().scale(GanymedeConstants.PROJECTILE_SPEED_BULLET));
		
		this.speed = this.getSpeed();
		
		this.sprite = new RotatingSprite(this.view, this.dataService.getRotatingSpriteData(5));
		
		this.sprite.setPosition(this.getPosition().getX(), this.getPosition().getY());
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.range > 0f) {
			
			this.sprite.setPosition(this.getPosition().getX(), this.getPosition().getY());
			
			this.range -= this.speed;
			
			if (this.range <= 0f) {
				
				this.destroy();
			}
		}
		else {
			
			this.destroy();
		}
	}
	
	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		this.sprite.render();
	}

	@Override
	public strictfp float getZ() {
		
		return this.z;
	}
}
