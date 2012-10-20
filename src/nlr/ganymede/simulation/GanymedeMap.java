package nlr.ganymede.simulation;

import java.awt.Point;
import java.util.ArrayList;

import nlr.components.BasicComponentRenderable;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.control.CanMove;
import nlr.ganymede.simulation.physics.BasicBoid;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public strictfp final class GanymedeMap extends BasicComponentRenderable implements TileBasedMap {
	
	private DataService dataService;
	
	private View view;
	private TiledMap tiledMap;
	
	private Vector2f position; 
	
	private boolean[] isMeta;
	
	private boolean[][] isSolid;
	
	private int[][] iceStart;
	private int[][] mineralsStart;
	private int[][] metalStart;
	
	private int[][] ice;
	private int[][] minerals;
	private int[][] metal;
	
	private boolean[][] isBuiltOn;
	
	private PathFinder pathFinder;
	
	private ArrayList<Point> startingPositions;
	
	private Animation animationIce;
	private Animation animationMinerals;
	private Animation animationMetal;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.DEPTH_MAP;
	}
	
	public strictfp TiledMap getTiledMap() {
		
		return this.tiledMap;
	}
	
	public strictfp int getWidth() {
		
		return this.getWidthInTiles() * this.getTileWidth();
	}
	
	public strictfp int getHeight() {
		
		return this.getWidthInTiles() * this.getTileWidth();
	}
	
	@Override
	public strictfp int getWidthInTiles() {
		
		return this.tiledMap.getWidth();
	}
	
	@Override
	public strictfp int getHeightInTiles() {
		
		return this.tiledMap.getHeight();
	}
	
	public strictfp int getTileWidth() {
		
		return this.tiledMap.getTileWidth();
	}
	
	public strictfp int getTileHeight() {
		
		return this.tiledMap.getTileHeight();
	}
	
	public strictfp Vector2f getTileHalf() {
		
		return new Vector2f(this.getTileWidth() / 2f, this.getTileHeight() / 2f);
	}
	
	public strictfp Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public GanymedeMap(long id, DataService dataService, View view, TiledMap tiledMap) {
		
		super(id);
		
		this.dataService = dataService;
		
		this.view = view;
		this.tiledMap = tiledMap;
		
		this.position = new Vector2f(0, 0);
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.isMeta = new boolean[this.tiledMap.getLayerCount()];
		
		this.isSolid = new boolean[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		
		this.ice = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		this.minerals = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		this.metal = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		
		this.iceStart = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		this.mineralsStart = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		this.metalStart = new int[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		
		for (int z = 0; z < this.tiledMap.getLayerCount(); z++) {
			
			this.isMeta[z] = Boolean.parseBoolean(this.tiledMap.getLayerProperty(z, "Meta", "False"));
			
			for (int x = 0; x < this.tiledMap.getWidth(); x++) {
				
				for (int y = 0; y < this.tiledMap.getHeight(); y++) {
					
					if (Boolean.parseBoolean(this.tiledMap.getTileProperty(this.tiledMap.getTileId(x, y, z), "Solid", "False"))) {
						
						this.isSolid[x][y] = true;
					}
					
					this.ice[x][y] = Integer.parseInt(this.tiledMap.getTileProperty(this.tiledMap.getTileId(x, y, z), "Ice", "0"));
					this.minerals[x][y] = Integer.parseInt(this.tiledMap.getTileProperty(this.tiledMap.getTileId(x, y, z), "Minerals", "0"));
					this.metal[x][y] = Integer.parseInt(this.tiledMap.getTileProperty(this.tiledMap.getTileId(x, y, z), "Metal", "0"));
					
					this.iceStart[x][y] = this.ice[x][y];
					this.mineralsStart[x][y] = this.minerals[x][y];
					this.metalStart[x][y] = this.metal[x][y];
				}
			}
		}
		
		this.isBuiltOn = new boolean[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		
		this.pathFinder = new GanymedePathFinder(this, this.getWidthInTiles() * this.getHeightInTiles(), true, false);
		
		this.startingPositions = new ArrayList<Point>();
		
		for (int i = 0; i < Integer.parseInt(this.tiledMap.getMapProperty("MaxFactions", "0")); i++) {
			
			Point p = new Point(
					Integer.parseInt(this.tiledMap.getMapProperty(Integer.toString(i) + "StartX", "0")), 
					Integer.parseInt(this.tiledMap.getMapProperty(Integer.toString(i) + "StartY", "0")));
			
			this.startingPositions.add(p);
		}
		
		this.animationIce = new Animation(new SpriteSheet(this.dataService.getImage(21), 24, 24), 1);
		this.animationMinerals = new Animation(new SpriteSheet(this.dataService.getImage(11), 24, 24), 1);
		this.animationMetal = new Animation(new SpriteSheet(this.dataService.getImage(22), 24, 24), 1);
	}
	
	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		Point p = this.view.worldCoordinatesToViewCoordinates(this.position.getX(), this.position.getY());
		
		for (int i = 0; i < this.tiledMap.getLayerCount(); i++) {
			
			if (!this.isMeta[i]) {
				
				this.tiledMap.render(p.x, p.y, i);
			}
		}
		
		for (int x = 0; x < this.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.getHeightInTiles(); y++) {
				
				if (this.iceStart[x][y] > 0) {
					
					int i = (this.ice[x][y] * (this.animationIce.getFrameCount() - 1)) / this.iceStart[x][y];
					
					Vector2f tileCenter = this.getTileCenter(x, y);
					
					this.animationIce.getImage(i).drawCentered(p.x + tileCenter.getX(), p.y + tileCenter.getY());
				}
				else if (this.mineralsStart[x][y] > 0) { // TODO: Handle resource overlap better
					
					int i = (this.minerals[x][y] * (this.animationMinerals.getFrameCount() - 1)) / this.mineralsStart[x][y];
					
					Vector2f tileCenter = this.getTileCenter(x, y);
					
					this.animationMinerals.getImage(i).drawCentered(p.x + tileCenter.getX(), p.y + tileCenter.getY());
				}
				else if (this.metalStart[x][y] > 0) {
					
					int i = (this.metal[x][y] * (this.animationMetal.getFrameCount() - 1)) / this.metalStart[x][y];
					
					Vector2f tileCenter = this.getTileCenter(x, y);
					
					this.animationMetal.getImage(i).drawCentered(p.x + tileCenter.getX(), p.y + tileCenter.getY());
				}
			}
		}
	}
	
	public strictfp boolean isSolid(int x, int y) {
		
		if ((x < 0) || (y < 0) || (x >= this.getWidthInTiles()) || (y >= this.getHeightInTiles())) {
			
			return true;
		}
		else {
			
			return this.isSolid[x][y];
		}
	}
	
	public strictfp boolean isBuiltOn(int x, int y) {
		
		if ((x < 0) || (y < 0) || (x >= this.getWidthInTiles()) || (y >= this.getHeightInTiles())) {
			
			return false;
		}
		else {
			
			return this.isBuiltOn[x][y];
		}
	}
	
	public strictfp void setBuiltOn(Point p, boolean isBuiltOn) {
		
		this.isBuiltOn[p.x][p.y] = isBuiltOn;
	}
	
	public strictfp int getIce(int x, int y) {
		
		return this.ice[x][y];
	}
	
	public strictfp void mineIce(int x, int y) {
		
		this.ice[x][y]--;
	}
	
	public strictfp int getMinerals(int x, int y) {
		
		return this.minerals[x][y];
	}
	
	public strictfp void mineMinerals(int x, int y) {
		
		this.minerals[x][y]--;
	}
	
	public strictfp int getMetal(int x, int y) {
		
		return this.metal[x][y];
	}
	
	public strictfp void mineMetal(int x, int y) {
		
		this.metal[x][y]--;
	}
	
	public strictfp Point getStartingPosition(int faction) {
		
		return new Point(this.startingPositions.get(faction));
	}
	
	public strictfp Point getNearestTile(float x, float y) {
		
		// Get the coordinates
		Point tileCoordinates = new Point(
				(int) (x / this.getTileWidth()), 
				(int) (y / this.getTileHeight()));
		
		// Clamp
		if (tileCoordinates.x < 0) {
			
			tileCoordinates.x = 0;
		}
		
		if (tileCoordinates.y < 0) {
			
			tileCoordinates.y = 0;
		}
		
		if (tileCoordinates.x >= this.getWidthInTiles()) {
			
			tileCoordinates.x = this.getWidthInTiles();
		}
		
		if (tileCoordinates.y >= this.getHeightInTiles()) {
			
			tileCoordinates.y = this.getHeightInTiles();
		}
		
		// Return the result
		return tileCoordinates;
	}
	
	public strictfp Point getNearestTile(Vector2f worldPosition) {
		
		return this.getNearestTile(worldPosition.getX(), worldPosition.getY());
	}
	
	public strictfp Vector2f getTilePosition(int x, int y) {
		
		return new Vector2f(
				x * this.tiledMap.getTileWidth(), 
				y * this.tiledMap.getTileHeight());
	}
	
	public strictfp Vector2f getTilePosition(Point tileCoordinates) {
		
		return this.getTilePosition(
				tileCoordinates.x, 
				tileCoordinates.y);
	}
	
	public strictfp Vector2f getTileCenter(int x, int y) {
		
		return new Vector2f(
				x * this.tiledMap.getTileWidth() + this.getTileWidth() / 2f, 
				y * this.tiledMap.getTileHeight() + this.getTileHeight() / 2f);
	}
	
	public strictfp Vector2f getTileCenter(Point tileCoordinates) {
		
		return this.getTileCenter(
				tileCoordinates.x, 
				tileCoordinates.y);
	}
	
	public strictfp Rectangle getTileArea(int x, int y) {
		
		Vector2f topLeft = this.getTilePosition(new Point(x, y));
		
		return new Rectangle(
				topLeft.getX(), 
				topLeft.getY(), 
				this.getTileWidth(), 
				this.getTileHeight());
	}

	@Override
	public strictfp boolean blocked(PathFindingContext context, int x, int y) {
		
		if (context.getMover() instanceof BasicBoid) {
			
			BasicBoid basicBoid = (BasicBoid) context.getMover();
			
			if (!basicBoid.isGround()) {
				
				return false;
			}
		}
		
		if (this.isSolid(x, y)) {
			
			return true;
		}
		else {
			
			return this.isBuiltOn(x, y);
		}
	}

	@Override
	public strictfp float getCost(PathFindingContext arg0, int arg1, int arg2) {
		
		return 1.0f; // TODO: Add cost for being near enemy bases etc. 
	}

	@Override
	public strictfp void pathFinderVisited(int arg0, int arg1) {
		
	}
	
	public strictfp GanymedePath findPath(CanMove canMove, float targetX, float targetY) {
		
		Point s = this.getNearestTile(canMove.getPosition().getX(), canMove.getPosition().getY());
		Point t = this.getNearestTile(targetX, targetY);
		
		Path path = this.pathFinder.findPath(canMove, s.x, s.y, t.x, t.y);
		
		if (path == null) {
			
			return null;
		}
		else {
			
			ArrayList<Vector2f> waypoints = new ArrayList<Vector2f>(path.getLength() + 2);
			
			for (int i = 0; i < path.getLength(); i++) {
				
				waypoints.add(this.getTilePosition(path.getX(i), path.getY(i)).add(this.getTileHalf()));
			}
			
			return new GanymedePath(waypoints);
		}
	}
	
	public strictfp static int distanceSq(int x1, int y1, int x2, int y2) {
		
		int dx = (x2 - x1);
		int dy = (y2 - y1);
		
		return (dx * dx) + (dy * dy);
	}
}
