package nlr.ganymede.data;

public strictfp final class BoidData {

	private int id; 
	
	private int hardRadius;
	private int softRadius;
	
	private boolean isGround;
	private boolean isAir;
	
	private float force;
	private float lateralForce;
	
	private float maxSpeed;
	
	private float mass;
	private float turnSpeed;
	
	public strictfp int getId() {
		return id;
	}
	
	public strictfp int getHardRadius() {
		return hardRadius;
	}
	
	public strictfp int getSoftRadius() {
		return softRadius;
	}
	
	public strictfp boolean isGround() {
		return isGround;
	}
	
	public strictfp boolean isAir() {
		return isAir;
	}
	
	public strictfp float getForce() {
		return force;
	}
	
	public strictfp float getLateralForce() {
		return lateralForce;
	}
	
	public strictfp float getMaxSpeed() {
		return maxSpeed;
	}

	public strictfp float getMass() {
		return mass;
	}

	public strictfp float getTurnSpeed() {
		return turnSpeed;
	}

	public BoidData(
			int id, 
			int hardRadius, 
			int softRadius,
			boolean isGround, 
			boolean isAir, 
			float acceleration, 
			float lateralAcceleration, 
			float maxSpeed, 
			float mass, 
			float turnSpeed) {
		
		super();
		
		this.id = id;
		this.hardRadius = hardRadius;
		this.softRadius = softRadius;
		this.isGround = isGround;
		this.isAir = isAir;
		this.force = acceleration;
		this.lateralForce = lateralAcceleration;
		this.maxSpeed = maxSpeed;
		this.mass = mass;
		this.turnSpeed = turnSpeed;
	}
}
