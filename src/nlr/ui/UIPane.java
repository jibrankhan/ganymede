package nlr.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class UIPane extends AbstractUIContainerComponent {

	private static final Color COLOR_BACKGROUND = new Color(220, 220, 220);
	
	private static final Color COLOR_BORDER = new Color(0, 0, 0);

	public UIPane(UniScale position, UniScale area, int z, UniScale padding) {
		
		super(position, area, z, padding);
	}

	public UIPane(UniScale position, UniScale area, int z) {
		
		super(position, area, z);
	}

	public UIPane(UniScale position, UniScale area, UniScale padding) {
		
		super(position, area, padding);
	}

	public UIPane(UniScale position, UniScale area) {
		
		super(position, area);
	}
	
	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) {
		
		graphics.setColor(COLOR_BACKGROUND);
		
		graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		graphics.setColor(COLOR_BORDER);
		
		graphics.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		super.render(gameContainer, graphics);
	}
}
