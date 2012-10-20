package nlr.ganymede.hud;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nlr.ganymede.PlayerService;
import nlr.ganymede.SimulationStepService;
import nlr.ganymede.command.CommandAttack;
import nlr.ganymede.command.CommandMove;
import nlr.ganymede.command.CommandStop;
import nlr.ganymede.command.CommandTrainUnit;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.ganymede.simulation.Resources;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.control.CanAttack;
import nlr.ganymede.simulation.control.CanMove;
import nlr.ganymede.simulation.control.Controllable;
import nlr.ganymede.simulation.control.Selectable;
import nlr.ganymede.simulation.structures.TrainsUnits;
import nlr.ganymede.simulation.targeting.Target;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class OrderTracker implements MouseListener, KeyListener {

	private DataService dataService;
	private PlayerService playerService;
	private SimulationStepService simulationStepService;
	private View view;
	private Input input;
	private SelectionManager selectionManager;
	private GanymedeSimulation ganymedeSimulation;
	private Hud hud;
	
	private boolean isQueuing;
	
	public OrderTracker(
			DataService dataService, 
			PlayerService playerService, 
			SimulationStepService simulationStepService, 
			View view, 
			Input input, 
			SelectionManager selectionManager, 
			GanymedeSimulation ganymedeSimulation, 
			Hud hud) {
		
		super();
		
		this.dataService = dataService;
		this.playerService = playerService;
		this.simulationStepService = simulationStepService;
		this.view = view;
		this.input = input;
		this.selectionManager = selectionManager;
		this.ganymedeSimulation = ganymedeSimulation;
		this.hud = hud;
	}
	
	public void init() {
		
		this.input.addMouseListener(this);
		this.input.addKeyListener(this);
		
		this.isQueuing = false;
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		
		if (button == Input.MOUSE_RIGHT_BUTTON) {
			
			Vector2f worldCoordinates = this.view.viewCoordinatesToWorldCoordinates(x, y);
			
			for (Selectable i : this.selectionManager.getSelected()) {
				
				if (i.getFaction() == this.playerService.getLocalPlayer().getFaction()) {
					
					if ((i.getEntity().is(Controllable.class)) && (i.getEntity().is(CanMove.class))) {
						
						CommandMove commandMove = new CommandMove(
								i.getEntity().getId(), 
								!this.isQueuing, 
								worldCoordinates.getX(), 
								worldCoordinates.getY());
						
						try {
							
							this.simulationStepService.sendCommand(commandMove);
							
						} 
						catch (SlickException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
		if (key == Input.KEY_LSHIFT) {
			
			this.isQueuing = true;
		}
		
		if (key == Input.KEY_S) {
			
			try {
				
				this.orderStop();
			} 
			catch (SlickException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			
			List<Integer> ids = this.dataService.getEntityIdsForHotkey(key);
			
			if (ids.size() > 0) {
				
				try {
					
					orderTrainUnits(ids.get(0));
				} 
				catch (SlickException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		
		if (key == Input.KEY_LSHIFT) {
			
			this.isQueuing = false;
		}
	}
	
	private void orderStop() throws SlickException {
		
		for (Selectable i : this.selectionManager.getSelected()) {
			
			if (i.getEntity().is(Controllable.class)) {
				
				this.simulationStepService.sendCommand(new CommandStop(i.getId()));
			}
		}
	}
	
	private void orderTrainUnits(int id) throws SlickException {
		
		// Get the resource cost of the unit
		Resources resourceCost = new Resources();
		
		try {
			
			SupplyData supplyData = this.dataService.getSupplyData(id);
			
			resourceCost = new Resources(supplyData.getIceCost(), supplyData.getMineralCost(), supplyData.getMetalCost());
		}
		catch (Exception e) {
			
			resourceCost.zero();
		}
		
		// Check we can afford it
		Resources resourcesFree = this.hud.getResourcesFree();
		
		if (resourcesFree.getIce() < resourceCost.getIce()) {
			
			return;
		}
		
		if (resourcesFree.getMinerals() < resourceCost.getMinerals()) {
			
			return;
		}
		
		if (resourcesFree.getMetal() < resourceCost.getMetal()) {
			
			return;
		}
		
		// Find the best CanTrainUnits 
		List<TrainsUnits> viable = new ArrayList<TrainsUnits>();
		
		for (Selectable i : this.selectionManager.getSelected()) {
			
			if (i.getEntity().is(TrainsUnits.class)) {
				
				TrainsUnits trainsUnits = i.getEntity().get(TrainsUnits.class);
				
				if (trainsUnits.getIdsTrain().contains(id)) {
					
					viable.add(trainsUnits);
				}
			}
		}
		
		if (!viable.isEmpty()) {
			
			Collections.sort(viable, this.leastBusyCanTrainUnitsComparator);

			// Issue the command
			this.simulationStepService.sendCommand(new CommandTrainUnit(viable.get(0).getId(), id));
			
			this.hud.getResourcesReserved().add(resourceCost);
		}
	}
	
	private final Comparator<TrainsUnits> leastBusyCanTrainUnitsComparator = new Comparator<TrainsUnits>() {
		
		@Override
		public int compare(TrainsUnits a, TrainsUnits b) {
			
			int c = a.getBuildTime();
			int d = b.getBuildTime();
			
			if (c > d) {
				
				return 1;
			}
			else {
				
				if (c == d) {
					
					return 0;
				}
				else {
					
					return -1;
				}
			}
		}
	};
}
