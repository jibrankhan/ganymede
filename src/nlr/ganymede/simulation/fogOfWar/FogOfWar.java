package nlr.ganymede.simulation.fogOfWar;

import java.awt.Point;

import nlr.components.BasicComponentRenderable;
import nlr.components.ComponentManager;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.PlayerService;
import nlr.ganymede.data.DataService;
import nlr.ganymede.simulation.GanymedeMap;
import nlr.ganymede.simulation.View;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class FogOfWar extends BasicComponentRenderable {

	private ComponentManager componentManager;
	private View view;
	private GanymedeMap map;
	private PlayerService playerService;
	private DataService dataService;
	
	private boolean[][][] srd;
	private boolean[][][] fog;
	
	private Image imageSrd;
	private Image imageFog;
	
	@Override
	public strictfp float getDepth() {
		
		return GanymedeConstants.DEPTH_FOG_OF_WAR;
	}
	
	public FogOfWar(
			long id, 
			ComponentManager componentManager, 
			View view, 
			GanymedeMap map, 
			PlayerService playerService, 
			DataService dataService) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.view = view;
		this.map = map;
		this.playerService = playerService;
		this.dataService = dataService;
		
		this.srd = new boolean[this.map.getWidthInTiles()][this.map.getHeightInTiles()][GanymedeConstants.MAX_FACTION_COUNT];
		this.fog = new boolean[this.map.getWidthInTiles()][this.map.getHeightInTiles()][GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				
				for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
					
					this.srd[x][y][i] = true;
					this.fog[x][y][i] = true;
				}
			}
		}
		
		this.imageSrd = this.dataService.getImage(1);
		this.imageFog = this.dataService.getImage(2);
		
		this.imageFog.setAlpha(0.5f);
		
		// this.update();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				
				for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
					
					this.fog[x][y][i] = true;
				}
			}
		}
		
		for (Los i : this.componentManager.getComponents(Los.class)) {
			
			for (Point p : i.getTiles()) {
				
				this.updateCoordinates(
						p.x, 
						p.y, 
						i.getLos(), 
						i.getFaction());
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		Point p;
		Vector2f v;
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				
				v = this.map.getTilePosition(new Point(x, y));
				p = this.view.worldCoordinatesToViewCoordinates(v.getX(), v.getY());
				
				if (this.srd[x][y][this.playerService.getLocalPlayer().getFaction()]) {
					
					this.imageSrd.draw(p.x, p.y, this.map.getTileWidth(), this.map.getTileHeight());
				}
				else if (this.fog[x][y][this.playerService.getLocalPlayer().getFaction()]) {
					
					this.imageFog.draw(p.x, p.y, this.map.getTileWidth(), this.map.getTileHeight());
				}
			}
		}
	}
	
	public void revealMap(int faction) {
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				
				this.srd[x][y][faction] = false;
			}
		}
	}
	
	public boolean isVisible(int x, int y, int faction) {
		
		return (!this.fog[x][y][faction]);
	}
	
	private void updateCoordinates(int vx, int vy, int l, int p) {
		
		int minX = Math.max(0, vx - l);
		int minY = Math.max(0, vy - l);

		int maxX = Math.min(vx + l + 1, this.map.getWidthInTiles());
		int maxY = Math.min(vy + l + 1, this.map.getHeightInTiles());
		
		int ls = l * l;
		
		for (int x = minX; x < maxX; x++) {
			
			for (int y = minY; y < maxY; y++) {
				
				if (this.fog[x][y][p]) {
					
					if (GanymedeMap.distanceSq(x, y, vx, vy) < ls) {
						
						this.srd[x][y][p] = false;
						this.fog[x][y][p] = false;
					}
				}
			}
		}
	}
}
