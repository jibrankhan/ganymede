package nlr.physics.collisions;

import java.util.TreeSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlr.physics.BasicPointPhysics;

public strictfp class BasicCollider extends BasicPointPhysics implements Collider {

	private float radius;
	
	private TreeSet<Collider> collidersHitThisFrame;
	
	@Override
	public float getRadius() {
		
		return this.radius;
	}
	
	public void setRadius(float radius) {
		
		if (radius > 0f) {
			
			this.radius = radius;
		}
		else {
			
			this.radius = Float.MIN_NORMAL;
		}
	}

	public BasicCollider(long id, Vector2f initialPosition, float mass, float dragCoefficient, float radius) {
		
		super(id, initialPosition, mass, dragCoefficient);
		
		this.radius = radius;
		
		this.collidersHitThisFrame = new TreeSet<Collider>();
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.collidersHitThisFrame.clear();
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.collidersHitThisFrame.clear();
	}

	@Override
	public boolean canCollide(Collider other) {
		
		return (!this.collidersHitThisFrame.contains(other));
	}

	@Override
	public void onCollision(Collider other, Vector2f depth) {
		
		this.collidersHitThisFrame.add(other);
	}

	@Override
	public int compareTo(Collider o) {
		
		if (this.getPosition().getX() < o.getPosition().getX()) {
			
			return 1;
		}
		else {
			
			if (this.getPosition().getX() == o.getPosition().getX()) {
				
				return 0;
			}
			else {
				
				return -1;
			}
		}
	}
}
