package nlr.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp class UILabel extends AbstractUIComponent {
	
	private static final Color COLOR_TEXT = new Color(0, 0, 0);
	
	private String text;
	
	public String getText() {
		
		return text;
	}

	public void setText(String text) {
		
		this.text = text;
	}

	public UILabel(UniScale position, UniScale area, int z, String text) {
		
		super(position, area, z);
		
		this.text = text;
	}
	
	public UILabel(UniScale position, UniScale area, String text) {
		
		super(position, area);
		
		this.text = text;
	}
	
	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) {
		
		super.render(gameContainer, graphics);
		
		graphics.setColor(COLOR_TEXT);
		
		graphics.drawString(this.text, this.getX(), this.getY());
	}
}
