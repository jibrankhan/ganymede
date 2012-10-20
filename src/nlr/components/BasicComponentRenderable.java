package nlr.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Provides a simple ComponentRenderable implementation. 
 * @author nicklarooy
 *
 */
public strictfp class BasicComponentRenderable extends BasicComponent implements ComponentRenderable {
	
	public BasicComponentRenderable(long id) {
		
		super(id);
	}

	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
	}

	@Override
	public strictfp float getDepth() {
		
		return 0;
	}
}
