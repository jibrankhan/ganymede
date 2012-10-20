package nlr.ganymede.command;

import java.awt.Point;
import java.util.Queue;

import nlr.components.Component;
import nlr.components.ComponentManager;
import nlr.components.Entity;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.ResourceManager;
import nlr.ganymede.simulation.UnitFactory;
import nlr.ganymede.simulation.control.CanAttack;
import nlr.ganymede.simulation.control.CanMove;
import nlr.ganymede.simulation.control.Controllable;
import nlr.ganymede.simulation.control.behaviors.BehaviorAttack;
import nlr.ganymede.simulation.control.behaviors.BehaviorMove;
import nlr.ganymede.simulation.structures.TrainsUnits;
import nlr.ganymede.simulation.targeting.Target;

import org.newdawn.slick.SlickException;

public final class CommandPerformer {
	
	private DataService dataService;
	private ComponentManager componentManager;
	private ResourceManager resourceManager;
	private GanymedeMap ganymedeMap;
	private UnitFactory unitFactory;
	
	public CommandPerformer(
			DataService dataService, 
			ComponentManager componentManager, 
			ResourceManager resourceManager, 
			GanymedeMap ganymedeMap,
			UnitFactory unitFactory) {
		
		super();
		
		this.dataService = dataService;
		this.componentManager = componentManager;
		this.resourceManager = resourceManager;
		this.ganymedeMap = ganymedeMap;
		this.unitFactory = unitFactory;
	}

	public void performCommands(Queue<Command> commands) throws SlickException {
		
		while (!commands.isEmpty()) {
			
			Command command = commands.remove();
			
			if (command instanceof CommandPlaceStructure) {
				
				this.perform((CommandPlaceStructure) command);
			}
			else if (command instanceof CommandTrainUnit) {
				
				this.perform((CommandTrainUnit) command);
			}
			else if (command instanceof CommandMove) {
				
				this.perform((CommandMove) command);
			}
		}
	}
	
	private void perform(CommandPlaceStructure command) {
		
		boolean canCreateStructure = this.unitFactory.canCreateStructure(
				command.getStructureId(), 
				command.getFaction(), 
				command.getY(), 
				command.getX());
		
		if (canCreateStructure) {
			
			this.unitFactory.createEntity(
					command.getStructureId(), 
					command.getFaction(), 
					command.getX(), 
					command.getY(), 
					false);
			
			try {
				
				SupplyData supplyData = this.dataService.getSupplyData(command.getStructureId());
				
				this.resourceManager.spendResources(
						command.getFaction(), 
						supplyData.getIceCost(), 
						supplyData.getMineralCost(), 
						supplyData.getMetalCost());
			}
			catch (SlickException e) {
				
				
			}
		}
	}
	
	private void perform(CommandTrainUnit command) {
		
		Component component = this.componentManager.getComponent(command.getEntityId());
		
		if (component instanceof TrainsUnits) {
			
			try {
				
				((TrainsUnits) component).queue(command.getTrainId());
			} 
			catch (SlickException e) {
				
			}
		}
	}
	
	private void perform(CommandMove command) {
		
		Component component = this.componentManager.getComponent(command.getEntityId());
		
		if (component instanceof Entity) {
			
			Entity entity = (Entity) component;
			
			if ((entity.is(Controllable.class)) && (entity.is(CanMove.class))) {
				
				try {
					
					Controllable controllable = entity.get(Controllable.class);
					CanMove canMove = entity.get(CanMove.class);
					
					if (command.shouldClear()) {
						
						controllable.stop();
					}
					
					controllable.queue(new BehaviorMove(this.ganymedeMap, canMove, command.getX(), command.getY()));
				} 
				catch (SlickException e) {
					
				}
			}
		}
	}
}
