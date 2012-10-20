package nlr.ganymede.simulation.control.behaviors;

import nlr.ganymede.simulation.View;

import org.newdawn.slick.Graphics;

public strictfp interface Behavior {
	
	boolean isComplete();
	
	void activate();
	
	void perform();
	
	void requestCancel();
	
	void draw(Graphics graphics, View view);
	
	void draw(Graphics graphics, View view, float expectedX, float expectedY);
	
	float estimatedCompletionX();
	
	float estimatedCompletionY();
}
