package nlr.physics.steering;

import nlr.physics.PointPhysics;

import org.newdawn.slick.geom.Vector2f;

public strictfp interface Vehicle extends PointPhysics, Turnable {
	
	void addSteering(Vector2f steering, float weight);
}
