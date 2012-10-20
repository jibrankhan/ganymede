package nlr.ganymede.simulation;

import nlr.components.BasicComponent;
import nlr.ganymede.data.MilitaryUnitData;
import nlr.ganymede.simulation.rendering.UnitRenderer;
import nlr.ganymede.simulation.targeting.Weapon;
import nlr.ganymede.simulation.targeting.WeaponState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class MilitaryUnit extends BasicComponent {

	private MilitaryUnitData militaryUnitData;
	
	private Weapon weapon;
	private UnitRenderer unitRenderer;
	
	private WeaponState weaponStatePrevious;
	
	public MilitaryUnit(
			long id, 
			MilitaryUnitData militaryUnitData,
			Weapon weapon, 
			UnitRenderer unitRenderer) {
		
		super(id);
		
		this.militaryUnitData = militaryUnitData;
		
		this.weapon = weapon;
		this.unitRenderer = unitRenderer;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.weaponStatePrevious = this.weapon.getWeaponState();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		WeaponState weaponState = this.weapon.getWeaponState();
		
		if (this.weaponStatePrevious != weaponState) {
			
			switch (weaponState) {
			
			case Ready:
				
				this.unitRenderer.shouldResetSprite();
				
				break;
			
			case Charging: 
				
				this.unitRenderer.setSpriteData(this.militaryUnitData.getSpriteDataCharging());
				
				break;
			
			case Recovering: 
				
				this.unitRenderer.setSpriteData(this.militaryUnitData.getSpriteDataRecovering());
				
				break;
			
			}
			
			this.weaponStatePrevious = weaponState;
		}
	}
}
