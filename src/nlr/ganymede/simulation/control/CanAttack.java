package nlr.ganymede.simulation.control;

import nlr.ganymede.simulation.GanymedeLocated;
import nlr.ganymede.simulation.targeting.Target;

public strictfp interface CanAttack extends GanymedeLocated {
	
	void setTarget(Target target);
	
	void clearTarget();
}
