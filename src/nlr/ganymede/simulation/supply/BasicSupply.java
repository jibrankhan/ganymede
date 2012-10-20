package nlr.ganymede.simulation.supply;

import nlr.components.BasicComponent;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.FactionManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class BasicSupply extends BasicComponent implements Supply {

	private FactionManager factionManager;
	
	private SupplyData supplyData;
	
	private Affiliated affiliated;

	public BasicSupply(long id, FactionManager factionManager, SupplyData supplyData, Affiliated affiliated) {
		
		super(id);
		
		this.factionManager = factionManager;
		
		this.supplyData = supplyData;
		
		this.affiliated = affiliated;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.factionManager.adjustValue(this.affiliated.getFaction(), this.supplyData.getValue());
		this.factionManager.adjustSupply(this.affiliated.getFaction(), this.supplyData.getSupply());
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
		
		this.factionManager.adjustValue(this.affiliated.getFaction(), -this.supplyData.getValue());
		this.factionManager.adjustSupply(this.affiliated.getFaction(), -this.supplyData.getSupply());
	}
}
