package nlr.ganymede.simulation.projectiles;

import java.util.List;

import nlr.components.BasicComponent;
import nlr.components.ComponentManager;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.targeting.Target;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class ProjectileManager extends BasicComponent {
	
	private DataService dataService;
	private View view;
	private ComponentManager componentManager;
	
	private Line line;
	
	public ProjectileManager(long id, DataService dataService, View view, ComponentManager componentManager) {
		
		super(id);
		
		this.dataService = dataService;
		this.view = view;
		this.componentManager = componentManager;
		
		this.line = new Line(0f, 0f);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		List<Projectile> projectiles = this.componentManager.getComponents(Projectile.class);
		List<Target> targets = this.componentManager.getComponents(Target.class);
		
		for (Projectile i : projectiles) {
			
			for (Target target : targets) {
				
				this.line.set(i.getPosition(), i.getPositionPrevious());
				
				if ((i.getFaction() != target.getFaction()) && ((i.isGround()) && (target.isGround())) || ((i.isAir()) && (target.isAir()))) {
					
					if (target.getBoundingShape().contains(this.line)) {
						
						target.hit(i.getDamagePierce(), i.getDamageHeat(), i.getDamageImpact(), i.isEmp());
						
						i.destroy();
					}
				}
			}
		}
	}
	
	public void spawnBullet(
			Vector2f position, 
			int faction, 
			int damagePierce,
			int damageHeat, 
			int damageImpact, 
			boolean isEmp, 
			boolean isGround,
			boolean isAir, 
			Vector2f direction, 
			float z, 
			float range) {
		
		Bullet bullet = new Bullet(
				this.componentManager.takeId(), 
				position, 
				faction, 
				damagePierce, 
				damageHeat, 
				damageImpact, 
				isEmp, 
				isGround, 
				isAir, 
				this.dataService, 
				this.view, 
				direction, 
				z, 
				range);
		
		this.componentManager.addComponent(bullet);
	}
}
