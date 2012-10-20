package nlr.ganymede.simulation.control;

import java.util.List;

import nlr.components.Component;
import nlr.ganymede.simulation.control.behaviors.Behavior;

public strictfp interface Controllable extends Component {
	
	/**
	 * Checks if the controller has any assigned behavior. 
	 * @return True if any behavior is assigned, and false otherwise
	 */
	boolean isIdle();
	
	List<Behavior> getBehaviors();
	
	/**
	 * Adds a behavior to the end of the queue. 
	 * @param behavior
	 */
	void queue(Behavior behavior);
	
	/**
	 * Will attempt to close all current behaviors. 
	 */
	void stop();
	
	/**
	 * Will attempt to skip the currently executing behavior. 
	 */
	void skip();
	
	/**
	 * Will close the last behavior added to the queue. 
	 */
	void undo();
}
