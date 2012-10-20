package nlr.ui;

public strictfp interface UIContainer {
	
	boolean isEnabled();
	
	float getInnerX();
	
	float getInnerY();
	
	float getInnerWidth();
	
	float getInnerHeight();
	
	void add(UIComponent component);
	
	void remove(UIComponent component);
	
	void removeAll();
}
