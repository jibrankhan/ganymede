package nlr.ganymede.simulation.targeting;

import nlr.components.Destroyable;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.Bounded;
import nlr.ganymede.simulation.Tiled;
import nlr.ganymede.simulation.control.Selectable;
import nlr.ganymede.simulation.fogOfWar.Visibility;
import nlr.ganymede.simulation.health.HitPoints;
import nlr.ganymede.simulation.physics.Collidable;

public strictfp interface Target extends Affiliated, Visibility, Collidable, Bounded, HitPoints, Destroyable, Selectable, Tiled {
	
	void hit(int pierce, int heat, int impact, boolean isEmp);
}
