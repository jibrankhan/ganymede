package nlr.ganymede.simulation.targeting;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import nlr.components.BasicComponent;
import nlr.ganymede.simulation.GanymedeMap;

public strictfp final class TargetManager extends BasicComponent {

	private GanymedeMap map;
	
	private TargetList[][] targetMap;
	
	public TargetManager(long id, GanymedeMap map) {
		
		super(id);
		
		this.map = map;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.targetMap = new TargetList[this.map.getWidthInTiles()][this.map.getHeightInTiles()];
		
		for (int x = 0; x < this.map.getWidthInTiles(); x++) {
			
			for (int y = 0; y < this.map.getHeightInTiles(); y++) {
				
				this.targetMap[x][y] = new TargetList();
			}
		}
	}
	
	public void addToTile(Target target, int x, int y) {
		
		this.targetMap[x][y].add(target);
	}
	
	public void removeFromTile(Target target, int x, int y) {
		
		this.targetMap[x][y].remove(target);
	}
	
	public List<Target> getTargetsTile(int x, int y) {
		
		return this.targetMap[x][y];
	}
	
	private class TargetList extends ArrayList<Target> {
		
		private static final long serialVersionUID = -1513398994380979588L;
	}
}
