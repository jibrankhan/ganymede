package nlr.physics;

import org.newdawn.slick.geom.Vector2f;

public strictfp interface Mobile extends Positioned2D {
	
	Vector2f getPositionPrevious();
	
	Vector2f getVelocity();

	float getSpeed();
	float getSpeedSquared();
	
	boolean isMoving();
	boolean hasMoved();
}
