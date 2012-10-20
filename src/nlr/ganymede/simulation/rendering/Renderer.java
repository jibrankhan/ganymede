package nlr.ganymede.simulation.rendering;

import org.newdawn.slick.geom.Shape;

public strictfp interface Renderer {
	
	Shape getRendererBoundingShape();
}
