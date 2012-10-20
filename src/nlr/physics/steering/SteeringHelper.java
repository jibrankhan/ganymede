package nlr.physics.steering;

import nlr.utils.Utils;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class SteeringHelper {
	
	private SteeringHelper() {
		
		super();
	}
	
	public strictfp static Vector2f steeringSeek(Vector2f position, Vector2f velocity, Vector2f target) {
		
		return target.copy().sub(position).sub(velocity);
	}
	
	public strictfp static Vector2f steeringArrival(Vector2f position, Vector2f velocity, Vector2f target, float stoppingRadius) {
		
		Vector2f displacement = target.copy().sub(position);
		
		float displacementLengthSquared = displacement.lengthSquared();
		
		if (displacementLengthSquared > stoppingRadius * stoppingRadius) {
			
			return displacement.sub(velocity);
		}
		else {
			
			return displacement.normalise().scale(displacementLengthSquared / (stoppingRadius * stoppingRadius)).sub(velocity);
		}
	}
	
	public strictfp static float turningFace(float rotation, float target) {
		
		float angleDifference = Utils.angleDifference(rotation, target);
		float directionShortest = Utils.directionShortest(rotation, target);
		
		return angleDifference * directionShortest;
	}
}
