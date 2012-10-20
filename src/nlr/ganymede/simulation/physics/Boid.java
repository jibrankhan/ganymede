package nlr.ganymede.simulation.physics;

import nlr.ganymede.simulation.Bounded;
import nlr.ganymede.simulation.Tiled;
import nlr.ganymede.simulation.control.CanMove;
import nlr.ganymede.simulation.targeting.FiringPlatform;

public strictfp interface Boid extends CanMove, FiringPlatform, Bounded, Tiled {

}
