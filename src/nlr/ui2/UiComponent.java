package nlr.ui2;

public strictfp interface UiComponent extends UiEntity {
	
	Dimension getX();
	
	Dimension getY();
	
	Dimension getWidth();
	
	Dimension getHeight();
	
	float getAbsoluteX();
	
	void setAbsoluteX(float x);
	
	float getAbsoluteY();
	
	void setAbsoluteY(float y);
	
	float getAbsoluteWidth();
	
	void setAbsoluteWidth(float w);
	
	float getAbsoluteHeight();
	
	void setAbsoluteHeight(float h);
}

