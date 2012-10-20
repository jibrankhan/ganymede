package nlr.ui2;

import org.newdawn.slick.GameContainer;

import nlr.ui2.layout.Layout;

public strictfp class UiScreen extends AbstractUiContainer {

	private float width;
	private float height;
	
	private float paddingX;
	private float paddingY;
	
	@Override
	public float getInnerX() {
		
		return this.paddingX;
	}

	@Override
	public float getInnerY() {
		
		return this.paddingY;
	}
	
	@Override
	public float getInnerWidth() {
		
		return this.width - this.paddingX * 2f;
	}

	@Override
	public float getInnerHeight() {
		
		return this.height - this.paddingY * 2f;
	}

	public UiScreen(Layout layout, float paddingX, float paddingY) {
		
		super(layout);
		
		this.paddingX = paddingX;
		this.paddingY = paddingY;
	}
	
	@Override
	public strictfp void init(GameContainer gameContainer) {
		
		this.width = gameContainer.getWidth();
		this.height = gameContainer.getHeight();
		
		super.init(gameContainer);
	}
	
	public UiScreen(Layout layout) {
		
		this(layout, 0f, 0f);
	}
}
