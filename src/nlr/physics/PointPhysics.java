package nlr.physics;

import org.newdawn.slick.geom.Vector2f;

public strictfp interface PointPhysics extends Mobile {

	float getMass();
	
	void applyImpulse(Vector2f impulse);
}
