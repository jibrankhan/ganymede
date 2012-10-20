package nlr.ui2.layout;

import nlr.ui2.UiComponent;
import nlr.ui2.UiContainer;

public strictfp class StaticLayout implements Layout {

	@Override
	public void doLayout(UiContainer container) {
		
		float innerX = container.getInnerX();
		float innerY = container.getInnerY();
		
		float innerWidth = container.getInnerWidth();
		float innerHeight = container.getInnerHeight();
		
		for (UiComponent i : container.getComponents()) {
			
			i.setAbsoluteX(innerX + i.getX().get(innerWidth));
			i.setAbsoluteY(innerY + i.getY().get(innerHeight));
			
			i.setAbsoluteWidth(i.getWidth().get(innerWidth));
			i.setAbsoluteHeight(i.getHeight().get(innerHeight));
		}
	}
}
