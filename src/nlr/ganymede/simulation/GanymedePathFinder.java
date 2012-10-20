package nlr.ganymede.simulation;

import org.newdawn.slick.util.pathfinding.AStarHeuristic;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public final class GanymedePathFinder extends AStarPathFinder {

	private TileBasedMap map;
	private boolean cutCorners;
	
	public GanymedePathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, boolean cutCorners) {
		
		super(map, maxSearchDistance, allowDiagMovement);
		
		this.map = map;
		this.cutCorners = cutCorners;
	}

	public GanymedePathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, AStarHeuristic heuristic, boolean cutCorners) {
		
		super(map, maxSearchDistance, allowDiagMovement, heuristic);
		
		this.map = map;
		this.cutCorners = cutCorners;
	}
	
	@Override
	protected boolean isValidLocation(Mover mover, int sx, int sy, int x, int y) {
		
		boolean valid = super.isValidLocation(mover, sx, sy, x, y);
		
        // If tile is still invalid, and we are not cutting corners,
        // and the destination node is diagonal from current node,
        // flag the tile invalid if either of the two tiles crossed
        // on the way to it are blocked.
        if ((valid) && (!cutCorners)) { 
        	if ((x - sx != 0) && (y - sy != 0)) { 
        		valid = !((map.blocked(this, x, sy)) || (map.blocked(this, sx, y)));
            }
        }
        
        return valid;
	}
}
