package nlr.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public strictfp interface ComponentRenderable extends Component {
	
	float getDepth();
	
	void render(GameContainer gameContainer, Graphics graphics) throws SlickException;
}
