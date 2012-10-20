package nlr.ui2;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp interface UiEntity {

	void init(GameContainer gameContainer);
	
	void update(GameContainer gameContainer, int delta);
	
	void render(GameContainer gameContainer, Graphics graphics);
	
	void validate();
	
	void invalidate();
}
