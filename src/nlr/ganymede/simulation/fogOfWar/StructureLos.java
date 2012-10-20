package nlr.ganymede.simulation.fogOfWar;

import java.awt.Point;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.data.LosData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.structures.Foundations;
import nlr.ganymede.simulation.structures.Utilities;

public final class StructureLos extends BasicComponent implements Los {

	private LosData losData;
	
	private Affiliated affiliated;
	private Foundations foundations;
	private Utilities utilities;
	
	@Override
	public int getFaction() {
		return this.affiliated.getFaction();
	}
	
	@Override
	public List<Point> getTiles() {
		
		return this.foundations.getTiles();
	}

	@Override
	public boolean isGround() {
		
		return this.foundations.isGround();
	}

	@Override
	public boolean isAir() {
		
		return this.foundations.isAir();
	}
	
	public int getLos() {
		
		if (this.utilities.isFunctional()) {
			
			return this.losData.getLos();
		}
		else {
			
			return 2;
		}
	}

	public StructureLos(
			long id,
			LosData losData, 
			Affiliated affiliated, 
			Foundations foundations, 
			Utilities utilities) {
		
		super(id);
		
		this.losData = losData;
		
		this.affiliated = affiliated;
		this.foundations = foundations;
		this.utilities = utilities;
	}
}
