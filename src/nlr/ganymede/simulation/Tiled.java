package nlr.ganymede.simulation;

import java.awt.Point;
import java.util.List;

import nlr.ganymede.simulation.physics.Collidable;

public strictfp interface Tiled extends Collidable {

	List<Point> getTiles();
}
