package nlr.ui2.layout;

import nlr.ui2.UiComponent;
import nlr.ui2.UiContainer;

public strictfp class FlowLayout implements Layout {

	private float spacingX;
	private float spacingY;
	
	public FlowLayout(float spacingX, float spacingY) {
		
		super();
		
		this.spacingX = spacingX;
		this.spacingY = spacingY;
	}
	
	public FlowLayout() {
		
		this(0f, 0f);
	}

	@Override
	public void doLayout(UiContainer container) {
		
		float innerX = container.getInnerX();
		float innerY = container.getInnerY();
		
		float innerWidth = container.getInnerWidth();
		float innerHeight = container.getInnerHeight();
		
		float x = 0f;
		float y = 0f;
		
		float maxY = 0f;
		
		for (UiComponent i : container.getComponents()) {
			
			i.setAbsoluteWidth(i.getWidth().get(innerWidth));
			i.setAbsoluteHeight(i.getHeight().get(innerHeight));
			
			if (i.getAbsoluteHeight() > maxY) {
				
				maxY = i.getAbsoluteHeight();
			}
			
			if (x + i.getAbsoluteWidth() > innerWidth) {
				
				x = 0f;
				
				y += maxY + this.spacingY;
				
				maxY = 0f;
			}
			
			i.setAbsoluteX(innerX + x);
			i.setAbsoluteY(innerY + y);
			
			x += i.getAbsoluteWidth() + this.spacingX;
		}
	}
}
