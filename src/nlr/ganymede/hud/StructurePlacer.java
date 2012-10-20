package nlr.ganymede.hud;

import java.awt.Point;
import java.util.ArrayList;

import nlr.ganymede.PlayerService;
import nlr.ganymede.SimulationStepService;
import nlr.ganymede.command.CommandPlaceStructure;
import nlr.ganymede.data.DataService;
import nlr.ganymede.data.FoundationsData;
import nlr.ganymede.data.StructureRendererData;
import nlr.ganymede.data.SupplyData;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.ganymede.simulation.PowerManager;
import nlr.ganymede.simulation.Resources;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.rendering.Sprite;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp class StructurePlacer implements MouseListener, KeyListener {
	
	private DataService dataService;
	private PlayerService playerService;
	private SimulationStepService simulationStepService;
	private View view;
	private GanymedeSimulation ganymedeSimulation;
	private Hud hud;
	
	private Input input;
	
	private Point mouseTileCoordinates;
	
	private boolean isPlacingStructure;
	private boolean isPlacementValid;
	
	private Point placementCoordinates;
	
	private int placingStructureId;
	
	private Sprite sprite;
	
	public StructurePlacer(
			DataService dataService, 
			PlayerService playerService, 
			SimulationStepService simulationStepService, 
			View view, 
			GanymedeSimulation ganymedeSimulation, 
			Hud hud, 
			Input input) {
		
		this.dataService = dataService;
		this.playerService = playerService;
		this.simulationStepService = simulationStepService;
		this.view = view;
		this.ganymedeSimulation = ganymedeSimulation;
		this.hud = hud;
		
		this.input = input;
		
		this.mouseTileCoordinates = new Point(0, 0);
		
		this.isPlacingStructure = false;
		this.isPlacementValid = true;
		
		this.placementCoordinates = new Point(0, 0);
		
		this.input.addKeyListener(this);
		this.input.addMouseListener(this);
		
		this.sprite = new Sprite(this.view);
	}
	
	public void render(Graphics graphics) {
		
		if ((this.isPlacingStructure) || (this.input.isKeyDown(Input.KEY_P))) {
		
			graphics.setColor(new Color(0, 100, 255, 50));
			
			GanymedeMap map = this.ganymedeSimulation.getGanymedeMap();
			PowerManager powerManager = this.ganymedeSimulation.getPowerManager();
			
			for (int x = 0; x < map.getWidthInTiles(); x++) {
				for (int y = 0; y < map.getHeightInTiles(); y++) {
					
					if (powerManager.isPowered(x, y, this.playerService.getLocalPlayer().getFaction())) {
						
						Vector2f v = map.getTilePosition(x, y);
						
						Point p = this.view.worldCoordinatesToViewCoordinates(v.getX(), v.getY());
						
						graphics.fillRect(p.x, p.y, map.getTileWidth(), map.getTileHeight());
					}
				}
			}
		}
		
		if (this.isPlacingStructure) {
			
			this.sprite.render();
		}
	}

	@Override
	public boolean isAcceptingInput() {
		
		return true;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			
			if (this.isPlacingStructure) {
				
				this.isPlacingStructure = this.input.isKeyDown(Input.KEY_LSHIFT);
				
				if (this.isPlacementValid) {
					
					try {
						
						this.simulationStepService.sendCommand(
								new CommandPlaceStructure(
										this.playerService.getLocalPlayer().getFaction(), 
										this.placementCoordinates.x, 
										this.placementCoordinates.y, 
										this.placingStructureId));
						
						try {
							
							SupplyData supplyData = this.dataService.getSupplyData(this.placingStructureId);
							
							this.hud.getResourcesReserved().add(supplyData.getIceCost(), supplyData.getMineralCost(), supplyData.getMetalCost());
						}
						catch (SlickException e) {
							
						}
						
						try {
							
							FoundationsData foundationsData = this.dataService.getFoundationsData(this.placingStructureId);
							
							for (int xx = 0; xx < foundationsData.getWidth(); xx++) {
								
								for (int yy = 0; yy < foundationsData.getLength(); yy++) {
									
									this.hud.getReservedTiles().add(new Point(this.placementCoordinates.x + xx, this.placementCoordinates.y + yy));
								}
							}
						}
						catch (SlickException e) {
							
						}
					} 
					catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else if (button == Input.MOUSE_RIGHT_BUTTON) {
			
			this.isPlacingStructure = false;
		}
	}

	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		
		this.mouseTileCoordinates = this.ganymedeSimulation.getGanymedeMap().getNearestTile(
				this.view.viewCoordinatesToWorldCoordinates(
						new Point(newX, newY)
						));
		
		if (this.isPlacingStructure) {
			
			updatePlacement();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		
		if (key == Input.KEY_ESCAPE) {
			
			this.isPlacingStructure = false;
		}
		else {
			
			ArrayList<Integer> r = this.dataService.getEntityIdsForHotkey(key);
			
			int faction = this.playerService.getLocalPlayer().getFaction();
			
			for (int i : r) {
				
				try {
					
					boolean meetRequirements = true;
					
					if (!this.ganymedeSimulation.getUnlocksManager().hasRequirements(i, faction)) {
						
						meetRequirements = false;
					}
					
					try {
						
						SupplyData supplyData = this.dataService.getSupplyData(i);
						
						Resources resourcesFree = this.hud.getResourcesFree();
						
						if (resourcesFree.getIce() < supplyData.getIceCost()) {
							
							meetRequirements = false;
						}
						
						if (resourcesFree.getMinerals() < supplyData.getMineralCost()) {
							
							meetRequirements = false;
						}
						
						if (resourcesFree.getMetal() < supplyData.getMetalCost()) {
							
							meetRequirements = false;
						}
					}
					catch (SlickException e) {
						
					}
					
					if (meetRequirements) {
						
						StructureRendererData structureRendererData = 
								this.dataService.getStructureRendererData(i);
						
						this.isPlacingStructure = true;
						
						this.placingStructureId = i;
						
						this.sprite.setSpriteData(structureRendererData.getSpriteData());
						
						this.updatePlacement();
						
						break;
					}
				}
				catch (SlickException e) {
					// e.printStackTrace(); // TODO: Should this be here?
				}
			}
		}
	}
	
	private void updatePlacement() {
		
		try {
			
			FoundationsData foundationsData = this.dataService.getFoundationsData(this.placingStructureId);
			
			this.placementCoordinates = new Point(
					this.mouseTileCoordinates.x - foundationsData.getWidth() / 2, 
					this.mouseTileCoordinates.y - foundationsData.getLength() / 2);
			
			this.sprite.setPosition(this.ganymedeSimulation.getGanymedeMap().getTilePosition(this.placementCoordinates));
			
			this.isPlacementValid = this.ganymedeSimulation.getUnitFactory().canCreateStructure(
					this.placingStructureId, 
					this.playerService.getLocalPlayer().getFaction(), 
					this.placementCoordinates.y, 
					this.placementCoordinates.x);
			
			for (int x = 0; x < foundationsData.getWidth(); x++) {
				
				for (int y = 0; y < foundationsData.getLength(); y++) {
					
					if (this.hud.getReservedTiles().contains(new Point(this.placementCoordinates.x + x, this.placementCoordinates.y + y))) {
						
						this.isPlacementValid = false;
						
						break;
					}
				}
				
				if (!this.isPlacementValid) {
					
					break;
				}
			}
			
			if (this.isPlacementValid) {
				
				this.sprite.setFilter(new Color(255, 255, 255, 100));
			}
			else {
				
				this.sprite.setFilter(new Color(255, 0, 0, 100));
			}
		}
		catch (Exception e) {
			
			this.isPlacingStructure = false;
		}
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public void setInput(Input arg0) {
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
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
}
