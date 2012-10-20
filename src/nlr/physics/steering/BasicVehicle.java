package nlr.physics.steering;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlr.physics.BasicPointPhysics;
import nlr.utils.Utils;

public strictfp class BasicVehicle extends BasicPointPhysics implements Vehicle {

	private float maxForce;
	private float maxForceSquared;
	
	private float turnSpeed;
	
	private List<Steering> steeringForces;
	
	private Vector2f steeringForce;
	
	private float rotation;
	private float turning;
	
	@Override
	public strictfp float getRotation() {
		
		return this.rotation;
	}

	public BasicVehicle(long id, Vector2f initialPosition, float mass, float dragCoefficient, float maxForce, float turnSpeed) {
		
		super(id, initialPosition, mass, dragCoefficient);
		
		this.maxForce = maxForce;
		
		this.maxForceSquared = this.maxForce * this.maxForce;
		
		this.turnSpeed = turnSpeed;
		
		this.steeringForces = new ArrayList<BasicVehicle.Steering>();
		
		this.steeringForce = new Vector2f();
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.steeringForces.clear();
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (!this.steeringForces.isEmpty()) {
			
			this.steeringForce.set(0f, 0f);
			
			float totalWeight = 0f;
			
			for (Steering i : this.steeringForces) {
				
				this.steeringForce.add(i.getSteeringForce().scale(i.getWeight()));
				
				totalWeight += i.getWeight();
			}
			
			this.steeringForces.clear();
			
			this.steeringForce.scale(1f / totalWeight);
			
			this.applyImpulse(this.limit(this.steeringForce));
		}
		
		if (this.turning != 0f) {
			
			float l = Math.abs(this.turning);
			
			if (l > this.turnSpeed) {
				
				this.turning *= this.turnSpeed / l; 
			}
			
			this.rotation += this.turning;
			
			this.rotation = Utils.wrapAngle(this.rotation);
			
			this.turning = 0f;
		}
	}

	@Override
	public void addSteering(Vector2f steeringForce, float weight) {
		
		this.steeringForces.add(new Steering(steeringForce, weight));
	}
	
	protected strictfp Vector2f limit(Vector2f steeringForce) {
		
		if (steeringForce.lengthSquared() > this.maxForceSquared) {
			
			steeringForce.scale(this.maxForce / steeringForce.length());
		}
		
		return steeringForce;
	}
	
	private strictfp final class Steering {
		
		private Vector2f steeringForce;
		
		private float weight;
		
		public strictfp Vector2f getSteeringForce() {
			
			return this.steeringForce;
		}
		
		public strictfp float getWeight() {
			
			return this.weight;
		}

		public Steering(Vector2f steeringForce, float weight) {
			
			super();
			
			this.steeringForce = steeringForce;
			
			if (weight < 0f) {
				
				weight = 0f;
			}
			else if (weight > 1f) {
				
				weight = 1f;
			}
			
			this.weight = weight;
		}
	}

	@Override
	public strictfp void turn(float turning) {
		
		this.turning += turning;
	}
}
