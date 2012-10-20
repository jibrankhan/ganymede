package nlr.ui2;

import nlr.ui2.layout.Layout;

public class AbstractUiContainerComponent extends AbstractUiContainer implements UiComponent {
	
	private Dimension x;
	private Dimension y;
	
	private Dimension width;
	private Dimension height;
	
	private float paddingX;
	private float paddingY;
	
	private float absoluteX;
	private float absoluteY;
	
	private float absoluteWidth;
	private float absoluteHeight;
	
	@Override
	public Dimension getX() {
		
		return this.x;
	}

	@Override
	public Dimension getY() {
		
		return this.y;
	}

	@Override
	public Dimension getWidth() {
		
		return this.width;
	}

	@Override
	public Dimension getHeight() {
		
		return this.height;
	}
	
	public AbstractUiContainerComponent(
			Layout layout, 
			Dimension x, 
			Dimension y,
			Dimension width, 
			Dimension height, 
			float paddingX, 
			float paddingY) {
		
		super(layout);
		
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.paddingX = paddingX;
		this.paddingY = paddingY;
	}

	public AbstractUiContainerComponent(
			Layout layout, 
			Dimension x, 
			Dimension y,
			Dimension width, 
			Dimension height) {
		
		this(layout, x, y, width, height, 0f, 0f);
	}

	public float getAbsoluteX() {
		
		return absoluteX;
	}

	public void setAbsoluteX(float absoluteX) {
		
		this.absoluteX = absoluteX;
	}

	public float getAbsoluteY() {
		
		return absoluteY;
	}

	public void setAbsoluteY(float absoluteY) {
		
		this.absoluteY = absoluteY;
	}

	public float getAbsoluteWidth() {
		
		return absoluteWidth;
	}

	public void setAbsoluteWidth(float absoluteWidth) {
		
		this.absoluteWidth = absoluteWidth;
	}

	public float getAbsoluteHeight() {
		
		return absoluteHeight;
	}

	public void setAbsoluteHeight(float absoluteHeight) {
		
		this.absoluteHeight = absoluteHeight;
	}

	@Override
	public float getInnerX() {
		
		return this.getAbsoluteX() + this.paddingX;
	}

	@Override
	public float getInnerY() {
		
		return this.getAbsoluteY() + this.paddingY;
	}

	@Override
	public float getInnerWidth() {
		
		return this.getAbsoluteWidth() - this.paddingX * 2f;
	}

	@Override
	public float getInnerHeight() {
		
		return this.getAbsoluteHeight() - this.paddingY * 2f;
	}
}
