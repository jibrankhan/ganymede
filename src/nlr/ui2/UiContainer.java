package nlr.ui2;

import java.util.List;

public strictfp interface UiContainer extends UiEntity {
	
	float getInnerX();
	
	float getInnerY();
	
	float getInnerWidth();
	
	float getInnerHeight();
	
	List<UiComponent> getComponents();
	
	void add(UiComponent component);
	
	void remove(UiComponent component);
	
	void removeAll();
}
