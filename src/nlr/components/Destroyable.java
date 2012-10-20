package nlr.components;

import org.newdawn.slick.SlickException;

public interface Destroyable {

	boolean isAlive();
	
	void destroy() throws SlickException;
}
