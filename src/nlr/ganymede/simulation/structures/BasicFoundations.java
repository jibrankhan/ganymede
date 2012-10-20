package nlr.ganymede.simulation.structures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import nlr.components.BasicComponent;
import nlr.ganymede.data.FoundationsData;
import nlr.ganymede.simulation.GanymedeMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class BasicFoundations extends BasicComponent implements Foundations {

	private FoundationsData foundationData;
	private GanymedeMap map;
	
	private Point topLeft;
	
	private List<Point> tiles;
	
	private Rectangle area;
	
	public strictfp FoundationsData getFoundationData() {
		
		return foundationData;
	}
	
	@Override
	public strictfp Point getTopLeft() {
		
		return topLeft;
	}
	
	@Override
	public int getWidth() {
		
		return this.foundationData.getWidth();
	}
	
	@Override
	public int getLength() {
		
		return this.foundationData.getLength();
	}
	
	@Override
	public strictfp boolean isGround() {
		
		return true;
	}
	
	@Override
	public strictfp boolean isAir() {
		
		return false;
	}
	
	@Override
	public strictfp List<Point> getTiles() {
		
		return this.tiles;
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	@Override
	public Shape getBoundingShape() {
		
		return this.area;
	}

	public BasicFoundations(
			long id, 
			FoundationsData foundationData, 
			GanymedeMap map, 
			int x, 
			int y) {
		
		super(id);
		
		this.foundationData = foundationData;
		this.map = map;
		this.topLeft = new Point(x, y);
		
		this.tiles = new ArrayList<Point>();
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.tiles.clear();
		
		for (int x = 0; x < this.foundationData.getWidth(); x++) {
			
			for (int y = 0; y < this.foundationData.getLength(); y++) {
				
				Point p = new Point(this.topLeft.x + x, this.topLeft.y + y);
				
				this.map.setBuiltOn(p, true);
				
				this.tiles.add(p);
			}
		}
		
		Vector2f v = this.map.getTilePosition(this.topLeft);
		
		this.area = new Rectangle(
				v.getX(), 
				v.getY(), 
				this.foundationData.getWidth() * this.map.getTileWidth(), 
				this.foundationData.getLength() * this.map.getTileHeight());
	}
	
	@Override
	public strictfp void destroy() throws SlickException {
		
		super.destroy();
		
		for (int x = 0; x < this.foundationData.getWidth(); x++) {
			
			for (int y = 0; y < this.foundationData.getLength(); y++) {
				
				this.map.setBuiltOn(new Point(this.topLeft.x + x, this.topLeft.y + y), false);
			}
		}
	}
}
