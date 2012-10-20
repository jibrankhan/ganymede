package nlr.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp interface UIComponent {
	
	boolean isEnabled();
	
	void setEnabled(boolean isEnabled); 
	
	void setContainer(UIContainer container);
	
	float getX();
	
	float getY();
	
	float getWidth();
	
	float getHeight();
	
	int getZ();
	
	void init(GameContainer gameContainer);
	
	void update(GameContainer gameContainer, int delta);
	
	void render(GameContainer gameContainer, Graphics graphics);
}
