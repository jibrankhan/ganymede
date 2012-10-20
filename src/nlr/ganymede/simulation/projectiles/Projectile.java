package nlr.ganymede.simulation.projectiles;

import org.newdawn.slick.geom.Vector2f;

import nlr.components.Component;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.GanymedeLocated;

public strictfp interface Projectile extends Affiliated, GanymedeLocated, Component {
	
	public int getDamagePierce();

	public int getDamageHeat();

	public int getDamageImpact();

	public boolean isEmp();

	public Vector2f getPosition();
	
	public Vector2f getPositionPrevious();
}
