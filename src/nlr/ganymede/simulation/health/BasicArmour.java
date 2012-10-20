package nlr.ganymede.simulation.health;

import nlr.components.BasicComponent;
import nlr.components.ComponentManager;
import nlr.ganymede.data.ArmourData;

public final class BasicArmour extends BasicComponent implements Armour {

	private ArmourData armourData;
	
	private HitPoints hitPoints;

	public BasicArmour(long id, ComponentManager componentManager, ArmourData armourData, HitPoints hitPoints) {
		
		super(id);
		
		this.armourData = armourData;
		
		this.hitPoints = hitPoints;
	}
	
	@Override
	public void hit(int pierce, int heat, int impact) {
		
		int result = 0;
		
		if (pierce > this.armourData.getResistancePierce()) {
			
			result += pierce - this.armourData.getResistancePierce();
		}
		
		if (heat > this.armourData.getResistanceHeat()) {
			
			result += heat - this.armourData.getResistanceHeat();
		}
		
		if (impact > this.armourData.getResistanceImpact()) {
			
			result += impact - this.armourData.getResistanceImpact();
		}
		
		if (result == 0) {
			
			result = 1;
		}
		
		this.hitPoints.adjust(-result);
	}
}
