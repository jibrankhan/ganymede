package nlr.ganymede.data;

public strictfp final class MilitaryUnitData {

	private int id;
	
	private WeaponData weaponData;
	
	private RotatingSpriteData spriteDataCharging;
	private RotatingSpriteData spriteDataRecovering;
	
	public strictfp int getId() {
		return id;
	}
	
	public strictfp WeaponData getWeaponData() {
		return weaponData;
	}
	
	public strictfp RotatingSpriteData getSpriteDataCharging() {
		return spriteDataCharging;
	}
	
	public strictfp RotatingSpriteData getSpriteDataRecovering() {
		return spriteDataRecovering;
	}

	public MilitaryUnitData(
			int id, 
			WeaponData weaponData,
			RotatingSpriteData spriteDataCharging,
			RotatingSpriteData spriteDataRecovering) {
		
		super();
		
		this.id = id;
		
		this.weaponData = weaponData;
		
		this.spriteDataCharging = spriteDataCharging;
		this.spriteDataRecovering = spriteDataRecovering;
	}
}
