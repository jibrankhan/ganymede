package nlr.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Provides a simple Component implementation. 
 * @author nicklarooy
 *
 */
public strictfp class BasicComponent implements Component {

	private long id;
	
	private boolean isInitialized;
	private boolean isAlive;
	
	@Override
	public strictfp long getId() {
		
		return id;
	}
	
	@Override
	public strictfp boolean isInitialized() {
		
		return isInitialized;
	}

	@Override
	public strictfp boolean isAlive() {
		
		return isAlive;
	}

	public BasicComponent(long id) {
		
		super();
		
		this.id = id;
	}

	@Override
	public strictfp void init(GameContainer gameContainer) throws SlickException {
		
		this.isInitialized = true;
		this.isAlive = true;
	}
	
	@Override
	public strictfp void update(GameContainer gameContainer, int delta) throws SlickException {
		
	}

	@Override
	public strictfp void destroy() throws SlickException {
		
		this.isAlive = false;
	}
}
