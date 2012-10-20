package nlr.ganymede.simulation.fogOfWar;

import java.awt.Point;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.data.LosData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.physics.Boid;

public strictfp final class UnitLos extends BasicComponent implements Los {

	private LosData losData;
	
	private Affiliated affiliated;
	private Boid boid;

	public UnitLos(
			long id, 
			LosData losData,
			Affiliated affiliated, 
			Boid boid) {
		
		super(id);
		
		this.losData = losData;
		
		this.affiliated = affiliated;
		this.boid = boid;
	}

	@Override
	public boolean isGround() {
		
		return this.boid.isGround();
	}

	@Override
	public boolean isAir() {
		
		return this.boid.isAir();
	}

	@Override
	public List<Point> getTiles() {
		
		return this.boid.getTiles();
	}

	@Override
	public int getLos() {
		
		return this.losData.getLos();
	}

	@Override
	public int getFaction() {
		
		return this.affiliated.getFaction();
	}
}
