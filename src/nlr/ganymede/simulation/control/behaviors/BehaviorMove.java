package nlr.ganymede.simulation.control.behaviors;

import java.awt.Point;

import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.GanymedePath;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.control.CanMove;
import nlr.physics.steering.SteeringHelper;
import nlr.utils.Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class BehaviorMove implements Behavior {
	
	private static final float ACCEPT_RADIUS = 32f;

	private GanymedeMap map;
	private CanMove canMove;
	
	private float targetX;
	private float targetY;
	
	private boolean isComplete;
	
	private GanymedePath path;

	public BehaviorMove(GanymedeMap map, CanMove canMove, float targetX, float targetY) {
		
		super();
		
		this.map = map;
		this.canMove = canMove;
		
		this.targetX = targetX;
		this.targetY = targetY;
		
		this.isComplete = false;
	}

	@Override
	public strictfp boolean isComplete() {
		
		return this.isComplete;
	}
	
	@Override
	public strictfp void activate() {
		
		this.path = this.map.findPath(this.canMove, this.targetX, this.targetY);
		
		if (this.path == null) {
			
			this.isComplete = true;
		}
	}

	@Override
	public strictfp void perform() {
		
		if (this.canMove.getPosition().distanceSquared(this.path.getEnd()) <= ACCEPT_RADIUS * ACCEPT_RADIUS) {
			
			this.isComplete = true;
		}
		else {
			
			float distanceAlongPath = this.path.getDistanceAlongPath(this.canMove.getPosition());
			
			Vector2f currentTarget = this.path.getPointAlongPath(distanceAlongPath + 32f);
			
			Vector2f steering = SteeringHelper.steeringSeek(
						this.canMove.getPosition(), 
						this.canMove.getVelocity(), 
						currentTarget);
			
			float steeringAngle = (float)steering.getTheta();
			
			float turning = Utils.angleDifference(steeringAngle, this.canMove.getRotation()) * 
					Utils.directionShortest(this.canMove.getRotation(), steeringAngle);
			
			this.canMove.addSteering(steering, 1f);
			this.canMove.turn(turning);
		}
	}

	@Override
	public strictfp void requestCancel() {
		
		this.isComplete = true;
	}

	@Override
	public strictfp void draw(Graphics graphics, View view) {
		
		this.draw(graphics, view, this.canMove.getPosition().getX(), this.canMove.getPosition().getY());
	}

	@Override
	public strictfp void draw(Graphics graphics, View view, float estimatedStartX, float estimatedStartY) {
		
		Point a = view.worldCoordinatesToViewCoordinates(estimatedStartX, estimatedStartY);
		Point b = view.worldCoordinatesToViewCoordinates(this.targetX, this.targetY);
		
		graphics.setColor(Color.green);
		
		graphics.drawLine(a.x, a.y, b.x, b.y);
		graphics.drawOval(b.x - 4f, b.y - 3f, 8f, 6f);
	}

	@Override
	public strictfp float estimatedCompletionX() {
		
		return this.targetX;
	}

	@Override
	public strictfp float estimatedCompletionY() {
		
		return this.targetY;
	}
}
