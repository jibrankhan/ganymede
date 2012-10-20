package nlr.ganymede.simulation.radar;

import nlr.components.BasicComponent;
import nlr.ganymede.data.RadarProviderData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.Functional;

public strictfp final class RadarProvider extends BasicComponent implements Affiliated, Functional {

	private Affiliated affiliated;
	private Functional functional;
	
	@Override
	public int getFaction() {
		
		return this.affiliated.getFaction();
	}
	
	@Override
	public boolean isFunctional() {
		
		return this.functional.isFunctional();
	}
	
	public RadarProvider(long id, RadarProviderData radarProviderData, Affiliated affiliated, Functional functional) {
		
		super(id);
		
		this.affiliated = affiliated;
		this.functional = functional;
	}
}
