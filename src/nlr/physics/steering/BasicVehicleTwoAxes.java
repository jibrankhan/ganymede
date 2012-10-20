package nlr.physics.steering;

import org.newdawn.slick.geom.Vector2f;

public class BasicVehicleTwoAxes extends BasicVehicle {
	
	private float maxForce;
	private float maxForceSquared;
	
	private float maxForceLateral;
	private float maxForceLateralSquared;

	public BasicVehicleTwoAxes(long id, Vector2f initialPosition, float mass, float dragCoefficient, float maxForce, float turnSpeed, float maxForceLateral) {
		
		super(id, initialPosition, mass, dragCoefficient, maxForce, turnSpeed);
		
		this.maxForce = maxForce;
		this.maxForceSquared = this.maxForce * this.maxForce;
		
		this.maxForceLateral = maxForceLateral;
		this.maxForceLateralSquared = this.maxForceLateral * this.maxForceLateral;
	}
	
	@Override
	protected strictfp Vector2f limit(Vector2f steeringForce) {
		
		Vector2f medial = new Vector2f(this.getRotation());
		Vector2f lateral = medial.getPerpendicular();
		
		Vector2f medialSteering = new Vector2f();
		Vector2f lateralSteering = new Vector2f();
		
		steeringForce.projectOntoUnit(medial, medialSteering);
		steeringForce.projectOntoUnit(lateral, lateralSteering);
		
		if (medialSteering.lengthSquared() > this.maxForceSquared) {
			
			medialSteering.normalise().scale(this.maxForce);
		}
		
		if (lateralSteering.lengthSquared() > this.maxForceLateralSquared) {
			
			lateralSteering.normalise().scale(this.maxForceLateral);
		}
		
		steeringForce.set(medialSteering.add(lateralSteering));
		
		return steeringForce;
	}
}
