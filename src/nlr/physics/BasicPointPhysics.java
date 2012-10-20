package nlr.physics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlr.components.BasicComponent;

public strictfp class BasicPointPhysics extends BasicComponent implements PointPhysics {

	private float mass;
	private float dragCoefficient;
	
	private Vector2f initialPosition;
	
	private Vector2f position;
	private Vector2f positionPrevious;
	
	private Vector2f velocity;
	
	private Vector2f acceleration;
	
	private Vector2f drag;
	
	@Override
	public float getMass() {
		
		return this.mass;
	}

	@Override
	public Vector2f getPosition() {
		
		return this.position;
	}
	
	@Override
	public Vector2f getPositionPrevious() {
		
		return this.positionPrevious;
	}

	@Override
	public Vector2f getVelocity() {
		
		return this.velocity;
	}

	@Override
	public float getSpeed() {
		
		return this.velocity.length();
	}

	@Override
	public float getSpeedSquared() {
		
		return this.velocity.lengthSquared();
	}

	@Override
	public boolean isMoving() {
		
		if (this.velocity.getX() != 0f) {
			
			return true;
		}
		else {
			
			return this.velocity.getY() != 0f;
		}
	}

	@Override
	public boolean hasMoved() {
		
		if (this.position.getX() != this.positionPrevious.getX()) {
			
			return true;
		}
		else {
			
			return this.position.getY() != this.positionPrevious.getY();
		}
	}

	public BasicPointPhysics(long id, Vector2f initialPosition, float mass, float dragCoefficient) {
		
		super(id);
		
		this.initialPosition = initialPosition;
		
		this.mass = mass;
		
		this.dragCoefficient = dragCoefficient;
		
		this.initialPosition = initialPosition;
		
		this.position = new Vector2f();
		this.positionPrevious = new Vector2f();
		
		this.velocity = new Vector2f();
		
		this.acceleration = new Vector2f();
		
		this.drag = new Vector2f();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.position.set(this.initialPosition);
		this.positionPrevious.set(this.position);
		
		this.velocity.set(0f, 0f);
		
		this.acceleration.set(0f, 0f);
		
		this.drag.set(0f, 0f);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if ((this.acceleration.getX() != 0f) || (this.acceleration.getY() != 0f)) {
			
			this.velocity.add(this.acceleration);
			
			this.acceleration.set(0f, 0f);
		}
		
		if ((this.velocity.getX() != 0f) || (this.velocity.getY() != 0f)) {
			
			this.position.add(this.velocity);
			
			if (this.dragCoefficient > 0f) {
				
				this.drag.set(this.velocity);
				this.drag.normalise();
				this.drag.scale(-this.dragCoefficient * this.velocity.lengthSquared());
				
				this.applyImpulse(this.drag);
			}
		}
		
		this.positionPrevious.set(this.position);
	}

	@Override
	public void applyImpulse(final Vector2f impulse) {
		
		this.acceleration.add(impulse.copy().scale(1f / this.mass));
	}
}
