package nlr.ui;

public strictfp class UniScale {

	private float absoluteX;
	private float absoluteY;
	
	private float relativeX;
	private float relativeY;
	
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
	
	public float getRelativeX() {
		
		return relativeX;
	}
	
	public void setRelativeX(float relativeX) {
		
		this.relativeX = relativeX;
	}
	
	public float getRelativeY() {
		
		return relativeY;
	}
	
	public void setRelativeY(float relativeY) {
		
		this.relativeY = relativeY;
	}
	
	public UniScale(float absoluteX, float absoluteY, float relativeX, float relativeY) {
		
		super();
		
		this.absoluteX = absoluteX;
		this.absoluteY = absoluteY;
		
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}
	
	public UniScale(float absoluteX, float absoluteY) {
		
		super();
		
		this.absoluteX = absoluteX;
		this.absoluteY = absoluteY;
		
		this.relativeX = 0f;
		this.relativeY = 0f;
	}
	
	public UniScale() {
		
		super();
		
		this.absoluteX = 0f;
		this.absoluteY = 0f;
		
		this.relativeX = 0f;
		this.relativeY = 0f;
	}
	
	public float getActualX(UIContainer parent) {
		
		return this.absoluteX + this.relativeX * parent.getInnerWidth();
	}
	
	public float getActualY(UIContainer parent) {
		
		return this.absoluteY + this.relativeY * parent.getInnerHeight();
	}
	
	public float getX() {
		
		return this.absoluteX;
	}
	
	public float getY() {
		
		return this.absoluteY;
	}
	
	public UniScale copy() {
		
		return new UniScale(
				this.absoluteX, 
				this.absoluteY, 
				this.relativeX, 
				this.relativeY);
	}
}

