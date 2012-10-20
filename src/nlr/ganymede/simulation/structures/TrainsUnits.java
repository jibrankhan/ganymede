package nlr.ganymede.simulation.structures;

import java.util.List;

import nlr.components.Component;

import org.newdawn.slick.SlickException;

public strictfp interface TrainsUnits extends Component {

	boolean isTraining();
	
	float getBuildPercentage();
	
	int getBuildTime();
	
	List<Integer> getIdsTrain();
	
	void queue(int id) throws SlickException;
}
