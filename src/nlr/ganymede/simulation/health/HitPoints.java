package nlr.ganymede.simulation.health;

import nlr.components.Component;

public strictfp interface HitPoints extends Component {

	int getHitPoints();
	
	void setHitPoints(int hitPoints);
	
	int getHitPointsMax();
	
	float getHitPointsPercentage();
	
	void adjust(int amount);
}
