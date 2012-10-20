package nlr.ganymede.simulation.radar;

import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.rendering.Renderer;

public strictfp interface RadarVisible extends Affiliated, Renderer {

	RadarBlipSize getRadarBlipSize();
}
