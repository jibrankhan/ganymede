package nlr.ganymede.simulation.fogOfWar;

import java.awt.Point;

import nlr.components.BasicComponent;
import nlr.ganymede.GanymedeConstants;
import nlr.ganymede.simulation.Tiled;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public final class TiledVisibility extends BasicComponent implements Visibility {

	private FogOfWar fogOfWar;
	private Tiled tiled;
	
	private boolean[] visibility;
	
	public TiledVisibility(long id, FogOfWar fogOfWar, Tiled tiled) {
		
		super(id);
		
		this.fogOfWar = fogOfWar;
		this.tiled = tiled;
		
		this.visibility = new boolean[GanymedeConstants.MAX_FACTION_COUNT];
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			
			this.visibility[i] = false;
		}
		
		this.updateVisibility();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.updateVisibility();
	}
	
	private void updateVisibility() {
		
		for (int i = 0; i < GanymedeConstants.MAX_FACTION_COUNT; i++) {
			
			this.visibility[i] = false;
			
			for (Point p : this.tiled.getTiles()) {
				
				if (this.fogOfWar.isVisible(p.x, p.y, i)) {
					
					this.visibility[i] = true;
					
					break;
				}
			}
		}
	}
	
	@Override
	public boolean isVisible(int faction) {
		
		return this.visibility[faction];
	}
}
