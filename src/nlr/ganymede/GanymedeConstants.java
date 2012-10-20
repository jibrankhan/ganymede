package nlr.ganymede;

import org.newdawn.slick.Color;

public strictfp final class GanymedeConstants {

	public static final int MAX_FACTION_COUNT = 2;
	
	public static final int SCREEN_EDGE_HOT_WIDTH = 64;
	public static final int SCREEN_EDGE_HOT_HEIGHT = 64;
	
	public static final int VIEW_SPEED = 6;
	
	public static final int MINI_MAP_WIDTH = 256;
	public static final int MINI_MAP_HEIGHT = 256;
	
	public static final int STEPS_PER_TURN = 100;
	
	public static final float DEPTH_MAP = 0f;
	public static final float DEPTH_UNIT = -1f;
	public static final float DEPTH_FOG_OF_WAR = -2f;
	public static final float DEPTH_RADAR = -3f;
	
	/**
	 * Calculates the appropriate draw depth of an entity in the world. 
	 * The x coordinate is not currently used in the calculations, but may be in the future. 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public strictfp static final float getDepthUnit(float x, float y, float z) {
		
		return DEPTH_UNIT - y - z;
	}
	
	public static final Color[] FACTION_COLORS = { 
		new Color(180, 200, 255), new Color(220, 100, 80), Color.yellow, Color.pink, Color.orange, 
		Color.green, Color.lightGray, Color.cyan }; 
	
	public static final float PROJECTILE_SPEED_BULLET = 8f;
	public static final float PROJECTILE_SPEED_ONE_TWENTY = 2f;
}