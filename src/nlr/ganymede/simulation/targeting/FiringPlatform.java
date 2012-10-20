package nlr.ganymede.simulation.targeting;

import nlr.components.Destroyable;
import nlr.ganymede.simulation.GanymedeLocated;
import nlr.physics.Mobile;
import nlr.physics.steering.Turnable;

public strictfp interface FiringPlatform extends GanymedeLocated, Turnable, Mobile, Destroyable {
	
	boolean isSteady();
}
