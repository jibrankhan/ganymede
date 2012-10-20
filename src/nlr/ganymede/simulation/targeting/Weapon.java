package nlr.ganymede.simulation.targeting;

import java.awt.Point;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.data.WeaponData;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.Positioned3D;
import nlr.ganymede.simulation.projectiles.ProjectileManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public final class Weapon extends BasicComponent {
	
	private WeaponData weaponData;
	
	private GanymedeMap map;
	private ProjectileManager projectileManager;
	
	private Affiliated affiliated;
	
	private Vector2f position;
	private float z;
	
	private WeaponState weaponState;
	
	private int timeTillNextState;
	
	private Target target;
	
	private int targetTileIndex;
	
	public WeaponData getWeaponData() {
		
		return this.weaponData;
	}
	
	public void setPosition(Positioned3D positioned3D) {
		
		this.position.set(positioned3D.getPosition());
		
		this.z = positioned3D.getZ();
	}

	public WeaponState getWeaponState() {
		
		return this.weaponState;
	}
	
	public Weapon(
			long id, 
			WeaponData weaponData, 
			GanymedeMap map, 
			ProjectileManager projectileManager, 
			Affiliated affiliated) {
		
		super(id);
		
		this.weaponData = weaponData;
		
		this.map = map;
		this.projectileManager = projectileManager;
		
		this.affiliated = affiliated;
		
		this.position = new Vector2f(0, 0);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.position.set(0, 0);
		
		this.weaponState = WeaponState.Ready;
		
		this.timeTillNextState = 0;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.timeTillNextState > 0) {
			
			this.timeTillNextState--;
			
			if (this.timeTillNextState == 0) {
				
				if (this.weaponState == WeaponState.Charging) {	
					
					if (this.isViableTargetTile(this.target, this.targetTileIndex)) {
						
						switch (this.weaponData.getProjectileType()) {
						
						case Bullet:
							
							this.projectileManager.spawnBullet(
									this.position, 
									this.affiliated.getFaction(), 
									this.weaponData.getDamagePierce(), 
									this.weaponData.getDamageHeat(), 
									this.weaponData.getDamageImpact(), 
									this.weaponData.isEmp(), 
									this.getWeaponData().targetsGround(), 
									this.weaponData.targetsAir(), 
									new Vector2f(this.target.getBoundingShape().getCenterX(), this.target.getBoundingShape().getCenterY()), 
									this.z, 
									this.weaponData.getRangeMax());
							
							break;

						default:
							
							throw new SlickException("Unsupported projectile type: " + this.weaponData.getProjectileType().toString() + ". ");
						}
					}
					
					this.weaponState = WeaponState.Recovering;
					
					this.timeTillNextState = this.weaponData.getRecoveryTime();
				}
				else {
					
					this.weaponState = WeaponState.Ready;
				}
			}
		}
	}
	
	public void charge(Target target, int targetTileIndex) {
		
		if (this.weaponState == WeaponState.Ready) {
			
			if (this.isViableTargetTile(target, targetTileIndex)) {
				
				this.weaponState = WeaponState.Charging;
				
				this.timeTillNextState = this.weaponData.getChargeTime();
				
				this.target = target;
				
				this.targetTileIndex = targetTileIndex;
			}
		}
	}
	
	public void cancel() {
		
		if (this.weaponState == WeaponState.Charging) {
			
			this.weaponState = WeaponState.Ready;
		}
	}
	
	public boolean hasViableTargetTile(Target target) {
		
		if (this.affiliated.getFaction() != target.getFaction()) {
			
			if (target.isVisible(this.affiliated.getFaction())) {
				
				if (target.isAlive()) {
					
					if ((this.getWeaponData().targetsGround() && target.isGround()) || (this.weaponData.targetsAir() && target.isAir())) {
						
						for (Point p : target.getTiles()) {
							
							float rangeSq = this.position.distanceSquared(this.map.getTileCenter(p));
							
							int rangeMin = this.weaponData.getRangeMin();
							int rangeMax = this.weaponData.getRangeMax();
							
							if ((rangeSq >= rangeMin * rangeMin) && (rangeSq <= rangeMax * rangeMax)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public int getViableTargetTile(Target target) {
		
		if (this.affiliated.getFaction() != target.getFaction()) {
			
			if (target.isVisible(this.affiliated.getFaction())) {
				
				if (target.isAlive()) {
					
					if ((this.getWeaponData().targetsGround() && target.isGround()) || (this.weaponData.targetsAir() && target.isAir())) {
						
						List<Point> tiles = target.getTiles();
						
						for (int i = 0; i < tiles.size(); i++) {
							
							float rangeSq = this.position.distanceSquared(this.map.getTileCenter(tiles.get(i)));
							
							int rangeMin = this.weaponData.getRangeMin();
							int rangeMax = this.weaponData.getRangeMax();
							
							if ((rangeSq >= rangeMin * rangeMin) && (rangeSq <= rangeMax * rangeMax)) {
								
								return i;
							}
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	public boolean isViableTargetTile(Target target, int targetTileIndex) {
		
		if (this.affiliated.getFaction() != target.getFaction()) {
			
			if (target.isVisible(this.affiliated.getFaction())) {
				
				if (target.isAlive()) {
					
					if ((this.getWeaponData().targetsGround() && target.isGround()) || (this.weaponData.targetsAir() && target.isAir())) {
						
						float rangeSq = this.position.distanceSquared(this.map.getTileCenter(target.getTiles().get(targetTileIndex)));
						
						int rangeMin = this.weaponData.getRangeMin();
						int rangeMax = this.weaponData.getRangeMax();
						
						if ((rangeSq >= rangeMin * rangeMin) && (rangeSq <= rangeMax * rangeMax)) {
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}
