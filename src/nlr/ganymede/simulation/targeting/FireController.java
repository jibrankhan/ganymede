package nlr.ganymede.simulation.targeting;

import nlr.components.BasicComponent;
import nlr.components.ComponentManager;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.control.CanAttack;
import nlr.utils.Utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class FireController extends BasicComponent implements CanAttack {
	
	private static final float ROTATION_TOLERANCE = 4f; 

	private ComponentManager componentManager;
	private GanymedeMap map;
	
	private FiringPlatform firingPlatform;
	private Weapon weapon;
	
	private Targeter targeter;
	
	private Target target;
	
	public FireController(long id, ComponentManager componentManager, GanymedeMap map, FiringPlatform firingPlatform, Weapon weapon) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.map = map;
		
		this.firingPlatform = firingPlatform;
		this.weapon = weapon;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.targeter = new Targeter(this.componentManager, this.weapon);
		
		this.target = null;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.firingPlatform.isAlive()) {
					
			this.weapon.setPosition(this.firingPlatform);
			
			if (this.target == null) {
				
				if (this.targeter.getTargets().isEmpty()) {
					
					float rotation = this.firingPlatform.getRotation();
					float directionToTarget = (float) this.firingPlatform.getVelocity().getTheta();
					
					this.firingPlatform.turn(Utils.angleDifference(rotation, directionToTarget) * 
							Utils.directionShortest(rotation, directionToTarget));
				}
				else {
					
					this.target = this.targeter.getTargets().get(0);
				}
			}
			else {
				
				if (this.firingPlatform.isSteady()) {
					
					int targetTile = this.weapon.getViableTargetTile(this.target);
					
					if (targetTile != -1) {
					
						float rotation = this.firingPlatform.getRotation();
						
						float directionToTarget = (float) this.map.getTileCenter(this.target.getTiles().get(targetTile)).sub(this.firingPlatform.getPosition()).getTheta();
						
						this.firingPlatform.turn(Utils.angleDifference(rotation, directionToTarget) * 
								Utils.directionShortest(rotation, directionToTarget));
						
						if (Utils.angleDifference(rotation, directionToTarget) < ROTATION_TOLERANCE) {
							
							if (this.weapon.getWeaponState() == WeaponState.Ready) {
								
								this.weapon.charge(target, targetTile);
							}
						}
						else {
							
							if (this.weapon.getWeaponState() == WeaponState.Charging) {
								
								this.weapon.cancel();
							}
						}
					}
					else {
						
						this.target = null;
					}
				}
				else {
					
					this.target = null;
				}
			}
		}
		else {
			
			this.destroy();
		}
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
		
		this.weapon.destroy();
	}

	@Override
	public void setTarget(Target target) {
		
		this.target = target;
	}

	@Override
	public void clearTarget() {
		
		this.target = null;
	}

	@Override
	public boolean isGround() {
		
		return this.firingPlatform.isGround();
	}

	@Override
	public boolean isAir() {
		
		return this.firingPlatform.isAir();
	}

	@Override
	public Vector2f getPosition() {
		
		return this.firingPlatform.getPosition();
	}

	@Override
	public float getZ() {
		
		return this.firingPlatform.getZ();
	}
}
