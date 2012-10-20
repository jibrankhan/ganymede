package nlr.ganymede.simulation.targeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nlr.components.ComponentManager;

public strictfp final class Targeter {

	private ComponentManager componentManager;
	private Weapon weapon;
	
	private List<Target> targets;
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public Targeter(ComponentManager componentManager, Weapon weapon) {
		
		super();
		
		this.componentManager = componentManager;
		this.weapon = weapon;
		
		this.targets = new ArrayList<Target>();
	}
	
	public strictfp List<Target> getTargets() {
		
		this.targets.clear();
		
		for (Target i : this.componentManager.getComponents(Target.class)) {
			
			if (this.weapon.hasViableTargetTile(i)) {
				
				this.targets.add(i);
			}
		}
		
		Collections.sort(this.targets, this.targetComparator);
		
		return new ArrayList<Target>(this.targets);
	}
	
	private final Comparator<Target> targetComparator = new Comparator<Target>() {
		
		@Override
		public int compare(Target o1, Target o2) {
			
			if (o1.getHitPoints() > o2.getHitPoints()) {
				
				return 1;
			}
			else {
				
				if (o1.getHitPoints() == o2.getHitPoints()) {
					
					return 0;
				}
				else {
					
					return -1;
				}
			}
		}
	};
}
