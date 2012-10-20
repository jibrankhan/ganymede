package nlr.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp interface Component extends Destroyable {

	long getId();
	
	boolean isInitialized();
	
	boolean isAlive();
	
	void init(GameContainer gameContainer) throws SlickException;
	
	void update(GameContainer gameContainer, int delta) throws SlickException;
}
