package nlr.ganymede.simulation.structures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nlr.components.BasicComponent;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.data.TrainsUnitsData;
import nlr.ganymede.simulation.Affiliated;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.ResourceManager;
import nlr.ganymede.simulation.UnitFactory;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class StructureTrainsUnits extends BasicComponent implements TrainsUnits {

	private static final int MAX_QUEUE_SIZE = 64;
	
	private TrainsUnitsData trainsUnitsData;
	
	private DataService dataService;
	
	private ResourceManager resourceManager;
	private GanymedeMap map;
	
	private Affiliated affiliated;
	private Foundations foundations;
	
	private UnitFactory unitFactory;
	
	private Queue<Integer> buildQueue;
	
	private int timeTillBuild;
	
	@Override
	public boolean isTraining() {
		
		return !this.buildQueue.isEmpty();
	}
	
	@Override
	public List<Integer> getIdsTrain() {
		
		return new ArrayList<Integer>(this.trainsUnitsData.getIdsTrain());
	}
	
	@Override
	public strictfp float getBuildPercentage() {
		
		if (this.timeTillBuild == 0) {
			
			return 1f;
		}
		else {
			
			return 1f - (this.timeTillBuild / 200f); // TODO: Actual build time
		}
	}
	
	@Override
	public strictfp int getBuildTime() {
		
		return this.buildQueue.size() * 200; // TODO: Actual build time
	}
	
	public StructureTrainsUnits(
			long id, 
			TrainsUnitsData trainsUnitsData, 
			DataService dataService, 
			ResourceManager resourceManager, 
			GanymedeMap map, 
			Affiliated info, 
			Foundations basicFoundations, 
			UnitFactory unitFactory) {
		
		super(id);
		
		this.trainsUnitsData = trainsUnitsData;
		
		this.dataService = dataService;
		
		this.resourceManager = resourceManager;
		this.map = map;
		
		this.affiliated = info;
		this.foundations = basicFoundations;
		
		this.unitFactory = unitFactory;
		
		this.buildQueue = new LinkedList<Integer>();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.buildQueue.clear();
		
		this.timeTillBuild = 0;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (!this.buildQueue.isEmpty()) {
			
			if (this.timeTillBuild > 0) {
				
				this.timeTillBuild--;
			}
			else {
				
				int id = this.buildQueue.remove();
				
				this.unitFactory.createEntity(id, this.affiliated.getFaction(), this.foundations.getTiles().get(0).x - 1, this.foundations.getTiles().get(0).y - 1 , true); // TODO: Better spawn location
				
				this.timeTillBuild = 200; // TODO: Actual time
			}
		}
	}
	
	@Override
	public void queue(int id) throws SlickException {
		
		if (this.buildQueue.size() < MAX_QUEUE_SIZE) {
			
			if (!this.trainsUnitsData.getIdsTrain().contains(id)) {
				
				throw new SlickException("Cannot train units of id " + Integer.toString(id) + ". ");
			}
			
			SupplyData supplyData = this.dataService.getSupplyData(id);
			
			if (this.buildQueue.isEmpty()) {
				
				this.timeTillBuild = 200; // TODO: Actual time
			}
			
			this.buildQueue.add(id);
			
			this.resourceManager.spendResources(this.affiliated.getFaction(), supplyData.getIceCost(), supplyData.getMineralCost(), supplyData.getMetalCost());
		}
	}
}
