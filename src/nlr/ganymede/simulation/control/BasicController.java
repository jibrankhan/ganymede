package nlr.ganymede.simulation.control;

import java.util.ArrayList;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.simulation.control.behaviors.Behavior;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class BasicController extends BasicComponent implements Controllable {
	
	private List<Behavior> behaviors;
	
	private Behavior previousBehavior;
	
	public boolean isIdle() {
		
		return this.behaviors.isEmpty();
	}
	
	@Override
	public List<Behavior> getBehaviors() {
		
		return new ArrayList<Behavior>(this.behaviors);
	}
	
	public BasicController(long id) {
		
		super(id);
		
		this.behaviors = new ArrayList<Behavior>();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.behaviors.clear();
		
		this.previousBehavior = null;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.tryPerform();
	}
	
	public void queue(Behavior behavior) {
		
		this.behaviors.add(behavior);
	}
	
	public void stop() {
		
		for (Behavior i : this.behaviors) {
			
			i.requestCancel();
		}
	}

	@Override
	public void skip() {
		
		boolean done = this.behaviors.isEmpty();
		
		while (!done) {
			
			if (this.behaviors.get(0).isComplete()) {
				
				this.behaviors.remove(0);
				
				done = this.behaviors.isEmpty();
			}
			else {
				
				this.behaviors.get(0).requestCancel();
				
				done = true;
			}
		}
	}

	@Override
	public void undo() {
		
		boolean done = this.behaviors.isEmpty();
		
		while (!done) {
			
			if (this.behaviors.get(this.behaviors.size() - 1).isComplete()) {
				
				this.behaviors.remove(this.behaviors.size() - 1);
				
				done = this.behaviors.isEmpty();
			}
			else {
				
				this.behaviors.get(this.behaviors.size() - 1).requestCancel();
				
				done = true;
			}
		}
	}
	
	private void tryPerform() {
		
		if (!this.behaviors.isEmpty()) {
			
			boolean i = true;
			
			while (i) {
				
				if (this.behaviors.isEmpty()) {
					
					i = false;
				}
				else {
					
					if (this.behaviors.get(0).isComplete()) {
						
						this.behaviors.remove(0);
					}
					else {
						
						i = false;
					}
				}
			}
			
			if (!this.behaviors.isEmpty()) {
				
				if (this.behaviors.get(0) == this.previousBehavior) {
					
					this.behaviors.get(0).perform();
				}
				else {
					
					this.behaviors.get(0).activate();
					
					this.previousBehavior = this.behaviors.get(0);
					
					this.tryPerform();
				}
			}
		}
	}
}
