package nlr.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp class UIButton extends AbstractUIContainerComponent {
	
	private static final Color COLOR_TEXT = new Color(0, 0, 0);
	
	private static final Color COLOR_BACKGROUND = new Color(200, 200, 200);
	
	private static final Color COLOR_HOVER = new Color(220, 220, 220);
	
	private static final Color COLOR_PRESSED = new Color(180, 180, 180);
	
	public UIButton(UniScale position, UniScale area, int z, UniScale padding) {
		
		super(position, area, z, padding);
	}

	public UIButton(UniScale position, UniScale area, int z) {
		
		super(position, area, z);
	}

	public UIButton(UniScale position, UniScale area, UniScale padding) {
		
		super(position, area, padding);
	}

	public UIButton(UniScale position, UniScale area) {
		
		super(position, area);
	}

	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) {
		
		float x = this.getX();
		float y = this.getY();
		
		float w = this.getWidth();
		float h = this.getHeight();
		
		if (this.isMousePressed()) {
			
			graphics.setColor(COLOR_PRESSED);
		}
		else if (this.isMouseOver()) {
			
			graphics.setColor(COLOR_HOVER);
		}
		else {
			
			graphics.setColor(COLOR_BACKGROUND);
		}
		
		graphics.fillRect(x, y, w, h);
		
		graphics.setColor(COLOR_TEXT);
		
		graphics.drawRect(x, y, w, h);
		
		super.render(gameContainer, graphics);
	}
}
