package nlr.ganymede.simulation.control.behaviors;

import java.awt.Point;

import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.control.CanAttack;
import nlr.ganymede.simulation.targeting.Target;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public strictfp final class BehaviorAttack implements Behavior {
	
	private CanAttack canAttack;
	private Target target;
	
	private boolean isComplete;

	public BehaviorAttack(CanAttack canAttack, Target target) {
		
		super();
		
		this.canAttack = canAttack;
		this.target = target;
		
		this.isComplete = false;
	}

	@Override
	public strictfp boolean isComplete() {
		
		return this.isComplete;
	}
	
	@Override
	public strictfp void activate() {
		
		if (this.target == null) {
			
			this.isComplete = true;
		}
		else {
			
			this.canAttack.setTarget(this.target);
		}
	}

	@Override
	public strictfp void perform() {
		
		if (this.target.isAlive()) {
			
		}
		else {
			
			this.isComplete = true;
		}
	}

	@Override
	public strictfp void requestCancel() {
		
		this.isComplete = true;
		
		this.canAttack.clearTarget();
	}

	@Override
	public strictfp void draw(Graphics graphics, View view) {
		
		this.draw(graphics, view, this.canAttack.getPosition().getX(), this.canAttack.getPosition().getY());
	}
	
	@Override
	public strictfp void draw(Graphics graphics, View view, float estimatedStartX, float estimatedStartY) {
		
		Point a = view.worldCoordinatesToViewCoordinates(
				estimatedStartX, 
				estimatedStartY);
		
		Point b = view.worldCoordinatesToViewCoordinates(
				this.target.getBoundingShape().getCenterX(), 
				this.target.getBoundingShape().getCenterY());
		
		graphics.setColor(Color.red);
		
		graphics.drawLine(a.x, a.y, b.x, b.y);
		graphics.drawOval(b.x - 4f, b.y - 3f, 8f, 6f);
	}

	@Override
	public strictfp float estimatedCompletionX() {
		
		return this.canAttack.getPosition().getX();
	}

	@Override
	public strictfp float estimatedCompletionY() {
		
		return this.canAttack.getPosition().getY();
	}
}
