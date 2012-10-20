package nlr.ganymede.simulation.projectiles;

import org.newdawn.slick.geom.Vector2f;

import nlr.components.ComponentRenderable;
import nlr.physics.BasicPointPhysics;

public strictfp abstract class BasicProjectile extends BasicPointPhysics implements Projectile, ComponentRenderable {

	private int faction;
	
	private int damagePierce; 
	private int damageHeat; 
	private int damageImpact;
	
	private boolean isEmp;
	
	private boolean isGround;
	private boolean isAir;
	
	@Override
	public int getFaction() {
		
		return this.faction;
	}

	@Override
	public int getDamagePierce() {
		
		return damagePierce;
	}

	@Override
	public int getDamageHeat() {
		
		return damageHeat;
	}

	@Override
	public int getDamageImpact() {
		
		return damageImpact;
	}
	
	@Override
	public boolean isEmp() {
		
		return isEmp;
	}

	@Override
	public boolean isGround() {
		
		return isGround;
	}

	@Override
	public boolean isAir() {
		
		return isAir;
	}

	public BasicProjectile(
			long id, 
			Vector2f initialPosition, 
			float mass, 
			float dragCoefficient, 
			int faction, 
			int damagePierce,
			int damageHeat, 
			int damageImpact, 
			boolean isEmp, 
			boolean isGround,
			boolean isAir) {
		
		super(id, initialPosition, mass, dragCoefficient);
		
		this.faction = faction;
		
		this.damagePierce = damagePierce;
		this.damageHeat = damageHeat;
		this.damageImpact = damageImpact;
		
		this.isEmp = isEmp;
		this.isGround = isGround;
		this.isAir = isAir;
	}
}
