package nlr.ganymede.hud;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import nlr.ganymede.PlayerService;
import nlr.ganymede.simulation.GanymedeSimulation;
import nlr.ganymede.simulation.View;
import nlr.ganymede.simulation.control.Selectable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Rectangle;

public strictfp final class SelectionManager implements MouseListener {

	private PlayerService playerService;
	private GanymedeSimulation ganymedeSimulation;
	private View view;
	private Input input;
	
	private boolean isDragging;
	
	private Point dragStart;
	private Point dragEnd;
	
	private List<Selectable> selected;
	private Stack<Selectable> toRemove;
	
	public List<Selectable> getSelected() {
		return this.selected;
	}
	
	public SelectionManager(PlayerService playerService, GanymedeSimulation ganymedeSimulation, View view, Input input) {
		
		super();
		
		this.playerService = playerService;
		this.ganymedeSimulation = ganymedeSimulation;
		this.view = view;
		this.input = input;
			
		this.selected = new ArrayList<Selectable>();
		
		this.toRemove = new Stack<Selectable>();
		
		this.input.addMouseListener(this);
	}
	
	public void update() {
		
		for (Selectable i : this.selected) {
			if (!i.isAlive()) {
				this.toRemove.add(i);
			}
		}
		
		while (!this.toRemove.isEmpty()) {
			this.selected.remove(this.toRemove.pop());
		}
	}
	
	public void render(Graphics graphics) {
		
		graphics.setColor(Color.white);
		
		if (this.isDragging) {
			
			int x = Math.min(this.dragStart.x, this.dragEnd.x);
			int y = Math.min(this.dragStart.y, this.dragEnd.y);
			int w = Math.max(this.dragStart.x, this.dragEnd.x) - x;
			int h = Math.max(this.dragStart.y, this.dragEnd.y) - y;
			
			graphics.drawRect(x, y, w, h);
		}
	}
	
	public void clearSelection() {
		this.selected.clear();
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
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			
			this.isDragging = false;
			
			ArrayList<Selectable> newSelection = new ArrayList<Selectable>();
			
			switch (clickCount) {
				case 1:
					
					for (Selectable i : this.ganymedeSimulation.getComponentManager().getComponents(Selectable.class)) {
						if (i.isVisible(this.playerService.getLocalPlayer().getFaction())) {
							if (i.getSelectionArea().contains(x, y)) {
								
								newSelection.add(i);
								
								break;
							}
						}
					}
					
					break;
					
				case 2:
					
					for (Selectable i : this.ganymedeSimulation.getComponentManager().getComponents(Selectable.class)) {
						if (i.getFaction() == this.playerService.getLocalPlayer().getFaction()) {
							if (i.getSelectionArea().contains(x, y)) {
								
								Rectangle viewArea = new Rectangle(
										this.view.getPosition().x, 
										this.view.getPosition().y, 
										this.view.getWidth(), 
										this.view.getHeight());
								
								for (Selectable j : this.ganymedeSimulation.getComponentManager().getComponents(Selectable.class)) {
									
									boolean isValid = (
											(i.getFaction() == j.getFaction()) && 
											(i.getEntityDataId() == j.getEntityDataId()) && 
											(viewArea.intersects(j.getSelectionArea())));
									
									if (isValid) {
										newSelection.add(j);
									}
								}
								
								break;
							}
						}
					}
					
					break;
			}
			
			if (!newSelection.isEmpty()) {
				
				this.selected.clear();
				this.selected.addAll(newSelection);
			}
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if (this.isDragging) {
			this.dragEnd = new Point(newx, newy);
		}
	}

	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			this.isDragging = true;
			
			this.dragStart = new Point(x, y);
			this.dragEnd = this.dragStart;
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		if (this.isDragging) {
			if ((this.dragStart.x != this.dragEnd.x) || (this.dragStart.y != this.dragEnd.y)) {
				
				ArrayList<Selectable> newSelection = new ArrayList<Selectable>();
				
				// Make selection
				float x = Math.min(this.dragStart.x, this.dragEnd.x);
				float y = Math.min(this.dragStart.y, this.dragEnd.y);
				float w = Math.max(this.dragStart.x, this.dragEnd.x) - x;
				float h = Math.max(this.dragStart.y, this.dragEnd.y) - y;
				
				Rectangle dragArea = new Rectangle(x, y, w, h);
				
				for (Selectable i : this.ganymedeSimulation.getComponentManager().getComponents(Selectable.class)) {
					if (i.isVisible(this.playerService.getLocalPlayer().getFaction())) {
						if (dragArea.intersects(i.getSelectionArea())) {
							newSelection.add(i);
						}
					}
				}
				
				this.isDragging = false;
				
				if (!newSelection.isEmpty()) {
					
					this.selected.clear();
					this.selected.addAll(newSelection);
				}
			}
		}
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		
	}
}
