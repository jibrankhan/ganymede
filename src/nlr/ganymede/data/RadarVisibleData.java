package nlr.ganymede.data;

import nlr.ganymede.simulation.radar.RadarBlipSize;

public final class RadarVisibleData {

	private int id;
	
	private RadarBlipSize level;

	public int getId() {
		return id;
	}

	public RadarBlipSize getLevel() {
		return level;
	}

	public RadarVisibleData(int id, RadarBlipSize level) {
		
		super();
		
		this.id = id;
		this.level = level;
	}
}
