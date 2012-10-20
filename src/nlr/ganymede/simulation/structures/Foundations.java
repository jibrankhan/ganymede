package nlr.ganymede.simulation.structures;

import java.awt.Point;

import nlr.ganymede.simulation.Bounded;
import nlr.ganymede.simulation.Tiled;

public strictfp interface Foundations extends Tiled, Bounded {

	Point getTopLeft();

	int getWidth();

	int getLength();
}
