package nlr.physics.collisions;

import org.newdawn.slick.geom.Vector2f;

import nlr.physics.PointPhysics;

public strictfp interface Collider extends PointPhysics, Comparable<Collider> {

	float getRadius();
	
	boolean canCollide(Collider other);
	
	void onCollision(Collider other, Vector2f depth);
}
