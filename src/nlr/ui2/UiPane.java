package nlr.ui2;

import nlr.ui2.layout.Layout;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public strictfp class UiPane extends AbstractUiContainerComponent {

	private static final Color colorBackground = new Color(220, 220, 220);
	
	private static final Color colorBorder = new Color(0, 0, 0);
	
	public UiPane(
			Layout layout, 
			Dimension x, 
			Dimension y, 
			Dimension width, 
			Dimension height,
			float paddingX, 
			float paddingY) {
		
		super(layout, x, y, width, height, paddingX, paddingY);
	}

	public UiPane(Layout layout, Dimension x, Dimension y, Dimension width, Dimension height) {
		
		super(layout, x, y, width, height);
	}
	
	@Override
	public strictfp void render(GameContainer gameContainer, Graphics graphics) {
		
		graphics.setColor(colorBackground);
		
		graphics.fillRect(
				this.getAbsoluteX(), 
				this.getAbsoluteY(), 
				this.getAbsoluteWidth(), 
				this.getAbsoluteHeight());
		
		graphics.setColor(colorBorder);
		
		graphics.drawRect(
				this.getAbsoluteX(), 
				this.getAbsoluteY(), 
				this.getAbsoluteWidth(), 
				this.getAbsoluteHeight());
		
		super.render(gameContainer, graphics);
	}
}
