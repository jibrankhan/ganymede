package nlr.ganymede.hud;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import nlr.ganymede.PlayerService;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.ganymede.simulation.Powered;
import nlr.ganymede.simulation.Resources;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.control.Controllable;
import nlr.ganymede.simulation.control.Selectable;
import nlr.ganymede.simulation.control.behaviors.Behavior;
import nlr.ganymede.simulation.health.HitPoints;
import nlr.ganymede.simulation.structures.Builds;
import nlr.ganymede.simulation.structures.TrainsUnits;
import nlr.ui.UIButton;
import nlr.ui.UILabel;
import nlr.ui.UIPane;
import nlr.ui.UIScreen;
import nlr.ui.UniScale;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author nicklarooy
 * The Hud class performs various functions. 
 * First, it renders much of the hud information you see on screen. 
 * Second, it handles state information to prevent the issuance of invalid commands. 
 * This functionality may be split at some point. 
 */
public strictfp final class Hud {
	
	private PlayerService playerService;
	private View view;
	private GanymedeSimulation ganymedeSimulation;
	private SelectionManager selectionManager;
	
	private Resources resourcesReserved;
	private List<Point> reservedTiles;
	
	private UIScreen screen;

	public Resources getResourcesReserved() {
		
		return resourcesReserved;
	}
	
	public Resources getResourcesFree() {
		
		return this.ganymedeSimulation.getResourceManager().getResources(this.playerService.getLocalPlayer().getFaction()).sub(this.resourcesReserved);
	}

	public List<Point> getReservedTiles() {
		
		return reservedTiles;
	}

	public Hud(PlayerService playerService, View view, GanymedeSimulation ganymedeSimulation, SelectionManager selectionManager) {
		
		super();
		
		this.playerService = playerService;
		this.view = view;
		this.ganymedeSimulation = ganymedeSimulation;
		this.selectionManager = selectionManager;
		
		this.resourcesReserved = new Resources();
		
		this.reservedTiles = new ArrayList<Point>();
		
		this.screen = new UIScreen(new UniScale(32f, 32f));
		
		UIPane paneResources = new UIPane(new UniScale(0f, 0f), new UniScale(256f, 32f), 0, new UniScale(8f, 8f));
		
		paneResources.add(new UILabel(new UniScale(0f, 0f, 0f, 0f), new UniScale(), "Ice: "));
		paneResources.add(new UILabel(new UniScale(0f, 0f, 0.33f, 0f), new UniScale(), "Min: "));
		paneResources.add(new UILabel(new UniScale(0f, 0f, 0.66f, 0f), new UniScale(), "Met: "));
		
		this.screen.add(paneResources);
		
		UIPane paneSupply = new UIPane(new UniScale(-256f, 0f, 1f, 0f), new UniScale(256f, 32f), new UniScale(8f, 8f));
		
		paneSupply.add(new UILabel(new UniScale(), new UniScale(), "Supply: "));
		
		this.screen.add(paneSupply);
		
		UIPane paneSelection = new UIPane(new UniScale(0f, -128f, 0f, 1f), new UniScale(-256f, 128f, 1f, 0f), new UniScale(8f, 8f));
		
		this.screen.add(paneSelection);
		
		UIPane paneMiniMap = new UIPane(new UniScale(-256f, -256f, 1f, 1f), new UniScale(256f, 256f));
		
		this.screen.add(paneMiniMap);
	}

	public strictfp void init(GameContainer gameContainer) {
		
		this.resourcesReserved.zero();
		
		this.reservedTiles.clear();
		
		this.screen.init(gameContainer);
	}
	
	public strictfp void update(GameContainer gameContainer, int delta) {
		
		this.screen.update(gameContainer, delta);
	}
	
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		for (Selectable i : this.ganymedeSimulation.getComponentManager().getComponents(Selectable.class)) {
			
			if (i.isVisible(this.playerService.getLocalPlayer().getFaction())) {
				
				int x = Math.round(i.getSelectionArea().getMinX());
				int y = Math.round(i.getSelectionArea().getMinY());
				
				int w = Math.round(Math.max(i.getSelectionArea().getWidth(), 24));
				int h = Math.round(Math.max(i.getSelectionArea().getHeight(), 32));
				
				if (i.getEntity().is(HitPoints.class)) {
					
					this.drawBar(graphics, x, y - 4, w, 4, i.getEntity().get(HitPoints.class).getHitPointsPercentage(), Color.green);
				}
				
				if (i.getEntity().is(Powered.class)) {
					
					Powered powered = i.getEntity().get(Powered.class);
					
					Color colorPower;
					
					if (powered.isPowered()) {
						
						colorPower = Color.yellow;
					}
					else if (powered.isPowerRequired()) {
						
						colorPower = Color.red;
					}
					else {
						
						colorPower = Color.blue;
					}
					
					this.drawIndicator(
							graphics, 
							colorPower, 
							x + w - 8.0f, 
							y + 4.0f, 
							6.0f);
				}
				
				if (i.getEntity().is(Builds.class)) {
					
					Builds builds = i.getEntity().get(Builds.class);
					
					if (!builds.isBuilt()) {
						
						this.drawBar(
								graphics, 
								x, 
								y - 10, 
								w, 
								4, 
								builds.getBuildPercentage(), 
								Color.magenta);
					}
				}
				
				if (i.getFaction() == this.playerService.getLocalPlayer().getFaction()) {
					
					if (i.getEntity().is(TrainsUnits.class)) {
						
						TrainsUnits trainsUnits = i.getEntity().get(TrainsUnits.class);
						
						if (trainsUnits.isTraining()) {
							
							this.drawBar(graphics, x, y - 10, w, 4, trainsUnits.getBuildPercentage(), Color.cyan);
						}
					}
				}
			}
		}
		
		for (Selectable i : this.selectionManager.getSelected()) {
			
			if (i.isVisible(this.playerService.getLocalPlayer().getFaction())) {
				
				int x = Math.round(i.getSelectionArea().getMinX());
				int y = Math.round(i.getSelectionArea().getMinY());
				
				int w = Math.round(Math.max(i.getSelectionArea().getWidth(), 24));
				int h = Math.round(Math.max(i.getSelectionArea().getHeight(), 32));
				
				int sw = w / 4;
				int sh = h / 4;
				
				graphics.setColor(Color.white);
				
				graphics.drawLine(x, y, x + sw, y); 
				graphics.drawLine(x, y, x, y + sh);
				
				graphics.drawLine(x + w, y, x + w - sw, y);
				graphics.drawLine(x + w, y, x + w, y + sh);
				
				graphics.drawLine(x, y + h, x + sw, y + h);
				graphics.drawLine(x, y + h, x, y + h - sh);
				
				graphics.drawLine(x + w, y + h, x + w - sw, y + h);
				graphics.drawLine(x + w, y + h, x + w, y + h - sh);
				
				if (i.getFaction() == this.playerService.getLocalPlayer().getFaction()) {
					
					if (i.getEntity().is(Controllable.class)) {
						
						Controllable controllable = i.getEntity().get(Controllable.class);
					
						if (controllable.getBehaviors().size() > 0) {
							
							Behavior b = controllable.getBehaviors().get(0);
							
							b.draw(graphics, this.view);
							
							float estimatedStartX = b.estimatedCompletionX();
							float estimatedStartY = b.estimatedCompletionY();
							
							for (int j = 1; j < controllable.getBehaviors().size(); j++) {
								
								b = controllable.getBehaviors().get(j);
								
								b.draw(graphics, this.view, estimatedStartX, estimatedStartY);
								
								estimatedStartX = b.estimatedCompletionX();
								estimatedStartY = b.estimatedCompletionY();
							}
						}
					}
				}
			}
		}
		
		graphics.setColor(Color.white);
		
		Resources resourcesFree = this.getResourcesFree();
		
		graphics.drawString(
				"Ice: " + Integer.toString(resourcesFree.getIce()) + 
				"		 Min: " + Integer.toString(resourcesFree.getMinerals()) + 
				"		 Met: " + Integer.toString(resourcesFree.getMetal()), 4, 64);
		
		graphics.drawString(
				"Supply: " + Integer.toString(this.ganymedeSimulation.getFactionManager().getSupply(this.playerService.getLocalPlayer().getFaction())) + 
				"		 Value: " + Integer.toString(this.ganymedeSimulation.getFactionManager().getValue(this.playerService.getLocalPlayer().getFaction())), 4, 96);
		
		if (this.ganymedeSimulation.getGanymedeLogic().isGameOver()) {
			
			String message;
			
			if (this.ganymedeSimulation.getGanymedeLogic().getWinners().contains(this.playerService.getLocalPlayer().getFaction())) {
				
				message = this.ganymedeSimulation.getFactionManager().getRaceData(this.playerService.getLocalPlayer().getFaction()).getWin().getEnglish();
			}
			else {
				
				message = this.ganymedeSimulation.getFactionManager().getRaceData(this.playerService.getLocalPlayer().getFaction()).getLose().getEnglish();
			}
			
			graphics.setColor(Color.white);
			
			graphics.drawString(message, 320, 160);
		}
		
		this.screen.render(gameContainer, graphics);
	}
	
	public void nextTurn() {
		
		this.resourcesReserved.zero();
		
		this.reservedTiles.clear();
	}
	
	private void drawBar(Graphics graphics, float x, float y, float w, float h, float p, Color col) {
		
		graphics.setColor(Color.black);
		
		graphics.fillRect(x, y, w, h);
		
		graphics.setColor(col);
		
		graphics.fillRect(x + 1, y + 1, (w - 2) * p, h - 2);
	}
	
	private void drawIndicator(Graphics graphics, Color color, float x, float y, float d) {
		
		graphics.setColor(Color.black);
		
		graphics.fillOval(x, y, d, d);
		
		graphics.setColor(color);
		
		graphics.fillOval(x + 1.0f, y + 1.0f, d - 2.0f, d - 2.0f);
	}
}
