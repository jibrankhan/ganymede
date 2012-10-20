package nlr.ganymede.simulation;

import java.awt.Point;
import java.util.List;

public strictfp interface PowerTransfer extends Affiliated, Powered, Tiled, Functional {
	
	List<Point> getTilesReach();
}
