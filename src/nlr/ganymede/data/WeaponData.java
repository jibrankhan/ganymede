package nlr.ganymede.data;

import nlr.ganymede.simulation.projectiles.ProjectileType;

public final class WeaponData {

	private int id;
	
	private TextData name;
	
	private int damagePierce;
	private int damageHeat;
	private int damageImpact;
	
	private boolean isEmp;
	
	private int rangeMin;
	private int rangeMax;
	
	private int chargeTime;
	private int recoveryTime;
	
	private ProjectileType projectileType;
	
	private boolean targetsGround;
	private boolean targetsAir;

	public int getId() {
		return id;
	}

	public TextData getName() {
		return name;
	}

	public int getDamagePierce() {
		return damagePierce;
	}

	public int getDamageHeat() {
		return damageHeat;
	}

	public int getDamageImpact() {
		return damageImpact;
	}
	
	public boolean isEmp() {
		return isEmp;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public int getRangeMin() {
		return rangeMin;
	}

	public int getRangeMax() {
		return rangeMax;
	}

	public int getRecoveryTime() {
		return recoveryTime;
	}

	public ProjectileType getProjectileType() {
		return projectileType;
	}
	
	public boolean targetsGround() {
		return targetsGround;
	}

	public boolean targetsAir() {
		return targetsAir;
	}

	public WeaponData(int id, TextData name, int damagePierce, int damageHeat,
			int damageImpact, boolean isEmp, int rangeMin, int rangeMax, 
			int chargeTime, int recoveryTime, 
			ProjectileType type, boolean targetsGround, boolean targetsAir) {
		
		super();
		
		this.id = id;
		
		this.name = name;
		
		this.damagePierce = damagePierce;
		this.damageHeat = damageHeat;
		this.damageImpact = damageImpact;
		
		this.isEmp = isEmp;
		
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		
		this.chargeTime = chargeTime;
		this.recoveryTime = recoveryTime;
		
		this.projectileType = type;
		
		this.targetsGround = targetsGround;
		this.targetsAir = targetsAir;
	}
}
