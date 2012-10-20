package nlr.ganymede.data;

public strictfp final class TurretData {

	private int id;
	
	private RotatingSpriteData rotatingSpriteData;
	private WeaponData weaponData;
	
	private float turnSpeed;

	public strictfp int getId() {
		return id;
	}

	public strictfp RotatingSpriteData getRotatingSpriteData() {
		return rotatingSpriteData;
	}

	public strictfp WeaponData getWeaponData() {
		return weaponData;
	}

	public strictfp float getTurnSpeed() {
		return turnSpeed;
	}

	public TurretData(
			int id, 
			RotatingSpriteData rotatingSpriteData,
			WeaponData weaponData, 
			float turnSpeed) {
		
		super();
		
		this.id = id;
		this.rotatingSpriteData = rotatingSpriteData;
		this.weaponData = weaponData;
		this.turnSpeed = turnSpeed;
	}
}
