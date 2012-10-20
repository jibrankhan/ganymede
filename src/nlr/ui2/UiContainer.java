package nlr.ui2;

import java.util.Set;

public strictfp interface UiContainer extends UiEntity {
	
	float getInnerX();
	
	float getInnerY();
	
	float getInnerWidth();
	
	float getInnerHeight();
	
	Set<UiComponent> getComponents();
	
	void add(UiComponent component);
	
	void remove(UiComponent component);
	
	void removeAll();
}
