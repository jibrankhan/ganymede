package nlr.ganymede.simulation.control;

import org.newdawn.slick.geom.Shape;

import nlr.components.Entity;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.Labelled;
import nlr.ganymede.simulation.fogOfWar.Visibility;

public strictfp interface Selectable extends Affiliated, Visibility, Labelled {
	
	Entity getEntity();
	
	Shape getSelectionArea();
}
