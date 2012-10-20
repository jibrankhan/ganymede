package nlr.physics.collisions;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlr.components.BasicComponent;
import nlr.components.Component;
import nlr.components.ComponentChangedEventListener;
import nlr.components.ComponentManager;

public class CollisionManager extends BasicComponent implements ComponentChangedEventListener {

	private ComponentManager componentManager;
	
	private int areaWidthInTiles;
	private int areaLengthInTiles;
	
	private int tileWidth;
	private int tileLength;
	
	private List<Collider> colliders;
	
	private Cell[][] cells;

	public CollisionManager(long id, ComponentManager componentManager, int areaWidthInTiles, int areaLengthInTiles, int tileWidth, int tileLength) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.areaWidthInTiles = areaWidthInTiles;
		this.areaLengthInTiles = areaLengthInTiles;
		
		this.tileWidth = tileWidth;
		this.tileLength = tileLength;
		
		this.colliders = new ArrayList<Collider>();
		
		this.cells = new Cell[this.areaWidthInTiles][this.areaLengthInTiles];
		
		for (int x = 0; x < this.areaWidthInTiles; x++) {
			
			for (int y = 0; y < this.areaLengthInTiles; y++) {
				
				this.cells[x][y] = new Cell();
			}
		}
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.componentManager.addComponentAddedEventListener(this);
		this.componentManager.addComponentDestroyedEventListener(this);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		for (Collider i : this.colliders) {
			
			if (i.hasMoved()) {
				
				this.removeFromCells(i);
				this.addToCells(i);
			}
		}
		
		Collider a;
		
		Vector2f depth;
		
		for (int i = 0; i < this.colliders.size() - 1; i++) {
			
			a = this.colliders.get(i);
			
			for (Collider b : this.getNearby(a)) {
				
				if ((a.canCollide(b)) && (b.canCollide(a))) {
					
					if (CollisionChecker.collision(
							a.getPosition().getX(), 
							a.getPosition().getY(), 
							a.getRadius(), 
							b.getPosition().getX(), 
							b.getPosition().getY(), 
							b.getRadius())) {
						
						depth = CollisionChecker.collisionDepth(
								a.getPosition().getX(), 
								a.getPosition().getY(), 
								a.getRadius(), 
								b.getPosition().getX(), 
								b.getPosition().getY(), 
								b.getRadius());
						
						if ((depth.getX() != 0f) || (depth.getY() != 0f)) {
							
							a.onCollision(b, depth);
							b.onCollision(a, depth.copy().negate());
						}
					}
				}
			}
		}
	}
	
	@Override
	public void destroy() throws SlickException {
		
		super.destroy();
		
		this.componentManager.removeComponentAddedEventListener(this);
		this.componentManager.removeComponentDestroyedEventListener(this);
	}

	@Override
	public void onComponentAdded(Component component) {
		
		if (component instanceof Collider) {
			
			Collider collider = (Collider) component;
			
			this.colliders.add(collider);
			
			this.addToCells(collider);
		}
	}

	@Override
	public void onComponentDestroyed(Component component) {
		
		if (component instanceof Collider) {
			
			this.removeFromCells((Collider) component);
		}
	}
	
	private List<Point> getCellsIntersect(Collider collider) {
		
		int minX = Math.max(0, (int) (collider.getPosition().getX() - collider.getRadius()) / this.tileWidth);
		int minY = Math.max(0, (int) (collider.getPosition().getY() - collider.getRadius()) / this.tileLength);
		
		int maxX = Math.min((int) (collider.getPosition().getX() + collider.getRadius() / this.tileWidth) + 1, this.areaWidthInTiles);
		int maxY = Math.min((int) (collider.getPosition().getY() + collider.getRadius() / this.tileLength) + 1, this.areaLengthInTiles);
		
		List<Point> cellsIntersect = new ArrayList<Point>();
		
		for (int x = minX; x < maxX; x++) {
			
			for (int y = minY; y < maxY; y++) {
				
				cellsIntersect.add(new Point(x, y));
			}
		}
		
		return cellsIntersect;
	}
	
	private List<Collider> getNearby(Collider collider) {
		
		List<Point> cellsIntersect = this.getCellsIntersect(collider);
		
		List<Collider> others = new ArrayList<Collider>();
		
		for (Point p : cellsIntersect) {
			
			for (Collider i : this.cells[p.x][p.y]) {
				
				if (i != collider) {
					
					others.add(i);
				}
			}
		}
		
		return others;
	}
	
	private void addToCells(Collider collider) {
		
		List<Point> cellsIntersect = this.getCellsIntersect(collider);
		
		for (Point p : cellsIntersect) {
			
			this.cells[p.x][p.y].add(collider);
		}
	}
	
	private void removeFromCells(Collider collider) {
		
		List<Point> cellsIntersect = this.getCellsIntersect(collider);
		
		for (Point p : cellsIntersect) {
			
			this.cells[p.x][p.y].remove(collider);
		}
	}
	
	private strictfp final class Cell extends ArrayList<Collider> {
		
		private static final long serialVersionUID = 6748862287355106289L;

		public Cell() {
			
			super(32);
		}
	}
}
